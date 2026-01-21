package Jack2026.Coinbase.DesignWorkerManagementSystem;

import java.util.*;

class OfficeManager_1 {
    // 使用 HashMap 存储 workerId 及其对应的员工信息
    private Map<String, Worker> workers;

    // 内部类，用于维护员工的状态
    private static class Worker {
        String position;
        int compensation;
        int totalWorkedTime;
        Integer lastEntryTime; // 使用 Integer，null 表示当前不在办公室内

        Worker(String position, int compensation) {
            this.position = position;
            this.compensation = compensation;
            this.totalWorkedTime = 0;
            this.lastEntryTime = null;
        }
    }

    public OfficeManager_1() {
        this.workers = new HashMap<>();
    }

    public boolean addWorker(String workerId, String position, int compensation) {
        if (workers.containsKey(workerId)) {
            return false;
        }
        workers.put(workerId, new Worker(position, compensation));
        return true;
    }

    public String registerWorker(String workerId, int timestamp) {
        if (!workers.containsKey(workerId)) {
            return "invalid_request";
        }

        Worker worker = workers.get(workerId);
        if (worker.lastEntryTime == null) {
            // 员工进入办公室
            worker.lastEntryTime = timestamp;
        } else {
            // 员工离开办公室，计算并累加本次工作时长
            worker.totalWorkedTime += (timestamp - worker.lastEntryTime);
            worker.lastEntryTime = null; // 重置进入时间
        }
        return "registered";
    }

    public int get(String workerId) {
        if (!workers.containsKey(workerId)) {
            return -1;
        }
        // 只返回已完成（已离开）的 session 时长总和
        return workers.get(workerId).totalWorkedTime;
    }
}
