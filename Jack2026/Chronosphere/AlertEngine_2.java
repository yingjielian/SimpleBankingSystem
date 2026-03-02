package Jack2026.Chronosphere;

import java.util.*;
import java.util.concurrent.*;

class Config {
    String id;
    String query;
    double criticalThreshold;
    int intervalSeconds;       // 检查频率
    int repeatIntervalSeconds; // 重复通知频率

    public Config(String id, String query, double threshold, int interval, int repeatInterval) {
        this.id = id;
        this.query = query;
        this.criticalThreshold = threshold;
        this.intervalSeconds = interval;
        this.repeatIntervalSeconds = repeatInterval;
    }
}

interface Client {
    List<Config> getAlerts();
    double executeQuery(String query);
    void notify(String alertId, double value);
    void resolve(String alertId, double value);
}

public class AlertEngine_2 {
    private final Client client;
    private final ScheduledExecutorService scheduler;

    // 保持 Part 1 的 enum
    enum AlertState { PASS, CRITICAL }

    private final Map<String, AlertState> alertStates = new ConcurrentHashMap<>();
    // 用于追踪重复间隔：Key 为 alertId, Value 为上次通知的毫秒时间戳
    private final Map<String, Long> lastNotifyTimes = new ConcurrentHashMap<>();

    public AlertEngine_2(Client client) {
        this.client = client;
        this.scheduler = Executors.newScheduledThreadPool(5);
    }

    public void start() {
        System.out.println(">>> 告警引擎启动 (支持 Repeat Interval)...");
        List<Config> alerts = client.getAlerts();
        for (Config config : alerts) {
            scheduler.scheduleAtFixedRate(
                    () -> executeCycle(config),
                    0,
                    config.intervalSeconds,
                    TimeUnit.SECONDS
            );
        }
    }
    private void executeCycle(Config config) {
        try {
            double currentValue = client.executeQuery(config.query);
            AlertState currentState = (currentValue > config.criticalThreshold)
                    ? AlertState.CRITICAL : AlertState.PASS;

            AlertState previousState = alertStates.getOrDefault(config.id, AlertState.PASS);
            long currentTime = System.currentTimeMillis();

            if (currentState == AlertState.CRITICAL) {
                Long lastNotify = lastNotifyTimes.get(config.id);

                // 判断是否需要通知：
                // 1. 状态从 PASS 切换到 CRITICAL (首次触发)
                // 2. 或者是持续 CRITICAL，但距离上次通知已超过 repeatInterval
                boolean isFirstTrigger = (previousState == AlertState.PASS);
                boolean isRepeatExceeded = (lastNotify == null) ||
                        (currentTime - lastNotify >= config.repeatIntervalSeconds * 1000L);

                if (isFirstTrigger || isRepeatExceeded) {
                    client.notify(config.id, currentValue);
                    lastNotifyTimes.put(config.id, currentTime); // 记录/更新通知时间
                } else {
                    System.out.printf("[%s] 仍处于 CRITICAL 但未到重复时间，跳过通知。%n", config.id);
                }
            }
            else if (currentState == AlertState.PASS && previousState == AlertState.CRITICAL) {
                // 状态转换：从 CRITICAL 恢复到 PASS
                client.resolve(config.id, currentValue);
                // 恢复后清除通知时间记录，以便下次发生告警时能立即通知
                lastNotifyTimes.remove(config.id);
            }

            alertStates.put(config.id, currentState);

        } catch (Exception e) {
            System.err.println("Error executing alert " + config.id + ": " + e.getMessage());
        }
    }

    public void stop() {
        scheduler.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        Client fakeClient = new Client() {
            @Override
            public List<Config> getAlerts() {
                // 测试用例：每 2s 检查一次，每 4s 重复通知一次
                return List.of(new Config("CPU_HIGH", "query_cpu", 80.0, 2, 4));
            }

            @Override
            public double executeQuery(String query) {
                return 95.0; // 模拟持续触发告警
            }

            @Override
            public void notify(String id, double val) {
                System.out.println("[NOTIFY] 🚨 告警: " + id + " 值为 " + val);
            }

            @Override
            public void resolve(String id, double val) {
                System.out.println("[RESOLVE] ✅ 恢复: " + id + " 值为 " + val);
            }
        };

        AlertEngine_2 engine = new AlertEngine_2(fakeClient);
        engine.start();

        // 运行 10 秒观察效果
        Thread.sleep(10000);

        System.out.println(">>> 正在关闭引擎...");
        engine.stop();
        System.out.println(">>> 引擎已停止。Thank you very much.");
    }
}

