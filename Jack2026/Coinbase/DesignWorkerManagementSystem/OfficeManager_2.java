package Jack2026.Coinbase.DesignWorkerManagementSystem;

import java.util.*;
import java.util.stream.Collectors;

class OfficeManager_2 {
    private Map<String, Worker> workers;

    private static class Worker {
        String id;
        String position;
        int compensation;
        int totalWorkedTime;
        Integer lastEntryTime;

        Worker(String id, String position, int compensation) {
            this.id = id;
            this.position = position;
            this.compensation = compensation;
            this.totalWorkedTime = 0;
            this.lastEntryTime = null;
        }
    }

    public OfficeManager_2() {
        this.workers = new HashMap<>();
    }

    public boolean addWorker(String workerId, String position, int compensation) {
        if (workers.containsKey(workerId)) {
            return false;
        }
        // 构造函数中增加存储 ID，方便排序时获取
        workers.put(workerId, new Worker(workerId, position, compensation));
        return true;
    }

    public String registerWorker(String workerId, int timestamp) {
        if (!workers.containsKey(workerId)) {
            return "invalid_request";
        }
        Worker worker = workers.get(workerId);
        if (worker.lastEntryTime == null) {
            worker.lastEntryTime = timestamp;
        } else {
            worker.totalWorkedTime += (timestamp - worker.lastEntryTime);
            worker.lastEntryTime = null;
        }
        return "registered";
    }

    public int get(String workerId) {
        if (!workers.containsKey(workerId)) {
            return -1;
        }
        return workers.get(workerId).totalWorkedTime;
    }

    public String topNWorkers(int n, String position) {
        // 1. 筛选出符合职位的员工
        List<Worker> filteredWorkers = workers.values().stream()
                .filter(w -> w.position.equals(position))
                .collect(Collectors.toList());

        // 2. 如果没有该职位的员工，返回空字符串
        if (filteredWorkers.isEmpty()) {
            return "";
        }

        // 3. 排序逻辑：时间降序 -> ID 升序
        return filteredWorkers.stream()
                .sorted((w1, w2) -> {
                    if (w1.totalWorkedTime != w2.totalWorkedTime) {
                        // 时间大的排在前面 (Descending)
                        return Integer.compare(w2.totalWorkedTime, w1.totalWorkedTime);
                    }
                    // 时间相同，ID 字典序小的在前 (Ascending)
                    return w1.id.compareTo(w2.id);
                })
                .limit(n) // 取前 n 个
                .map(w -> w.id + "(" + w.totalWorkedTime + ")") // 格式化为 ID(Time)
                .collect(Collectors.joining(", ")); // 用逗号和空格连接
    }
}
