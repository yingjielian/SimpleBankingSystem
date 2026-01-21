package Jack2026.Coinbase.DesignWorkerManagementSystem;

import java.util.*;
import java.util.stream.Collectors;

class OfficeManager_3 {
    private Map<String, Worker> workers;

    private static class Session {
        int start, end, comp;
        Session(int start, int end, int comp) {
            this.start = start;
            this.end = end;
            this.comp = comp;
        }
    }

    private static class PendingPromotion {
        String pos;
        int comp;
        int effectiveFrom;
        PendingPromotion(String pos, int comp, int from) {
            this.pos = pos;
            this.comp = comp;
            this.effectiveFrom = from;
        }
    }

    private static class Worker {
        String id;
        String position;
        int compensation;
        int totalTimeAcrossRoles = 0;
        int timeInCurrentRole = 0;
        Integer lastEntryTime = null;
        List<Session> history = new ArrayList<>();
        PendingPromotion pending = null;

        Worker(String id, String position, int compensation) {
            this.id = id;
            this.position = position;
            this.compensation = compensation;
        }
    }

    public OfficeManager_3() {
        this.workers = new HashMap<>();
    }

    public boolean addWorker(String workerId, String position, int compensation) {
        if (workers.containsKey(workerId)) return false;
        workers.put(workerId, new Worker(workerId, position, compensation));
        return true;
    }

    public String registerWorker(String workerId, int timestamp) {
        if (!workers.containsKey(workerId)) return "invalid_request";
        Worker w = workers.get(workerId);

        if (w.lastEntryTime == null) {
            // 进入办公室：检查是否有待处理的晋升且已到生效时间
            if (w.pending != null && timestamp >= w.pending.effectiveFrom) {
                w.position = w.pending.pos;
                w.compensation = w.pending.comp;
                w.timeInCurrentRole = 0; // 换岗位后，当前岗位时长重置
                w.pending = null;
            }
            w.lastEntryTime = timestamp;
        } else {
            // 离开办公室
            int duration = timestamp - w.lastEntryTime;
            w.totalTimeAcrossRoles += duration;
            w.timeInCurrentRole += duration;
            w.history.add(new Session(w.lastEntryTime, timestamp, w.compensation));
            w.lastEntryTime = null;
        }
        return "registered";
    }

    public int get(String workerId) {
        if (!workers.containsKey(workerId)) return -1;
        return workers.get(workerId).totalTimeAcrossRoles;
    }

    public String topNWorkers(int n, String position) {
        List<Worker> list = workers.values().stream()
                .filter(w -> w.position.equals(position))
                .sorted((w1, w2) -> {
                    if (w1.timeInCurrentRole != w2.timeInCurrentRole)
                        return Integer.compare(w2.timeInCurrentRole, w1.timeInCurrentRole);
                    return w1.id.compareTo(w2.id);
                })
                .limit(n)
                .collect(Collectors.toList());

        if (list.isEmpty()) return "";
        return list.stream()
                .map(w -> w.id + "(" + w.timeInCurrentRole + ")")
                .collect(Collectors.joining(", "));
    }

    public String promote(String workerId, String newPos, String newCompStr, int startTs) {
        if (!workers.containsKey(workerId)) return "invalid_request";
        Worker w = workers.get(workerId);
        // 如果已有待处理的晋升，则不能再次申请
        if (w.pending != null) return "invalid_request";

        w.pending = new PendingPromotion(newPos, Integer.parseInt(newCompStr), startTs);
        return "success";
    }

    public int calcSalary(String workerId, int startTs, int endTs) {
        if (!workers.containsKey(workerId)) return -1;
        Worker w = workers.get(workerId);
        long totalSalary = 0;

        for (Session s : w.history) {
            // 计算工作区间与查询区间的交集
            int overlapStart = Math.max(s.start, startTs);
            int overlapEnd = Math.min(s.end, endTs);

            if (overlapStart < overlapEnd) {
                totalSalary += (long)(overlapEnd - overlapStart) * s.comp;
            }
        }
        return (int)totalSalary;
    }
}
