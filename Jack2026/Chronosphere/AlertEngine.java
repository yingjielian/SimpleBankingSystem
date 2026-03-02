package Jack2026.Chronosphere;

import java.util.*;
import java.util.concurrent.*;

interface AlertsClient{
    List<AlertConfig> getAlerts();
    double executeQuery(String query);
    void notify(String alertId, double value);
    void resolve(String alertId, double value);
}

class AlertConfig{
    String id;
    String query;
    double criticalThreshold;
    int intervalSeconds;

    public AlertConfig(String id, String query, double threshold, int interval)
    {
        this.id = id;
        this.query = query;
        this.criticalThreshold = threshold;
        this.intervalSeconds = interval;
    }
}
public class AlertEngine {
    private final AlertsClient client;
    private final ScheduledExecutorService scheduler;
    private final Map<String, AlertState> alertStates = new ConcurrentHashMap<>();
    enum AlertState{
        PASS,
        CRITICAL
    }

    public AlertEngine(AlertsClient client, int poolSize)
    {
        this.client = client;
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
    }

    public void start(){
        List<AlertConfig> alerts = client.getAlerts();
        for(AlertConfig config : alerts)
        {
            scheduler.scheduleAtFixedRate(
                    () -> executeCycle(config),
                    0,
                    config.intervalSeconds,
                    TimeUnit.SECONDS
            );
        }
    }

    private void executeCycle(AlertConfig config)
    {
        try{
            double currentValue = client.executeQuery(config.query);
            AlertState currentState = (currentValue > config.criticalThreshold)
                    ? AlertState.CRITICAL : AlertState.PASS;
            AlertState previousState = alertStates.getOrDefault(config.id, AlertState.PASS);

            if(currentState == AlertState.CRITICAL)
            {
                client.notify(config.id, currentValue);
            }else if(currentState == AlertState.PASS && previousState == AlertState.CRITICAL)
            {
                client.resolve(config.id, currentValue);
            }

            alertStates.put(config.id, currentState);
        }catch (Exception e)
        {
            System.err.println("Error executing alert" + config.id + ": " + e.getMessage());
        }
    }

    public void stop()
    {
        scheduler.shutdown();
    }
}

class FakeAlertsClient implements AlertsClient {
    @Override
    public List<AlertConfig> getAlerts() {
        return List.of(
                new AlertConfig("cpu-high", "SELECT cpu_usage", 90.0, 5),
                new AlertConfig("mem-high", "SELECT mem_usage", 80.0, 10)
        );
    }

    @Override
    public double executeQuery(String query) {
        return Math.random() * 100; // 模拟随机指标值
    }

    @Override
    public void notify(String id, double val) {
        System.out.println("[ALERT] " + id + " is CRITICAL: " + val);
    }

    @Override
    public void resolve(String id, double val) {
        System.out.println("[RESOLVE] " + id + " is back to normal: " + val);
    }
}

 class Main {
    public static void main(String[] args) {
        // 1. 初始化模拟客户端
        AlertsClient client = new FakeAlertsClient();

        // 2. 初始化引擎
        AlertEngine engine = new AlertEngine(client, 5);

        // 3. 启动引擎
        engine.start();

        // 让主线程睡眠一段时间，以便观察输出
        try {
            Thread.sleep(20000); // 运行 20 秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. 关闭引擎
        System.out.println(">>> 正在关闭引擎...");
        engine.stop();
        System.out.println(">>> 引擎已停止。Thank you very much.");
    }
}