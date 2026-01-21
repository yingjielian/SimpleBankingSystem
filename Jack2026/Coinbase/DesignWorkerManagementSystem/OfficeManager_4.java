package Jack2026.Coinbase.DesignWorkerManagementSystem;

import java.util.*;
import java.util.stream.Collectors;

class OfficeManager_4 {
    private Map<String, Worker> workers;
    private List<int[]> doublePaidIntervals; // 存储合并后的不重叠双倍时段

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
        String id, position;
        int compensation, totalTimeAcrossRoles = 0, timeInCurrentRole = 0;
        Integer lastEntryTime = null;
        List<Session> history = new ArrayList<>();
        PendingPromotion pending = null;

        Worker(String id, String position, int compensation) {
            this.id = id;
            this.position = position;
            this.compensation = compensation;
        }
    }

    public OfficeManager_4() {
        this.workers = new HashMap<>();
        this.doublePaidIntervals = new ArrayList<>();
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
            if (w.pending != null && timestamp >= w.pending.effectiveFrom) {
                w.position = w.pending.pos;
                w.compensation = w.pending.comp;
                w.timeInCurrentRole = 0;
                w.pending = null;
            }
            w.lastEntryTime = timestamp;
        } else {
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
                .limit(n).collect(Collectors.toList());
        if (list.isEmpty()) return "";
        return list.stream().map(w -> w.id + "(" + w.timeInCurrentRole + ")").collect(Collectors.joining(", "));
    }

    public String promote(String workerId, String newPos, String newCompStr, int startTs) {
        if (!workers.containsKey(workerId)) return "invalid_request";
        Worker w = workers.get(workerId);
        if (w.pending != null) return "invalid_request";
        w.pending = new PendingPromotion(newPos, Integer.parseInt(newCompStr), startTs);
        return "success";
    }

    public void setDoublePaid(int startTimestamp, int endTimestamp) {
        doublePaidIntervals.add(new int[]{startTimestamp, endTimestamp});
        // 重新合并所有区间
        if (doublePaidIntervals.size() <= 1) return;

        doublePaidIntervals.sort(Comparator.comparingInt(a -> a[0]));
        List<int[]> merged = new ArrayList<>();
        int[] current = doublePaidIntervals.get(0);

        for (int i = 1; i < doublePaidIntervals.size(); i++) {
            int[] next = doublePaidIntervals.get(i);
            if (next[0] <= current[1]) {
                current[1] = Math.max(current[1], next[1]);
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        doublePaidIntervals = merged;
    }

    public int calcSalary(String workerId, int startTs, int endTs) {
        if (!workers.containsKey(workerId)) return -1;
        Worker w = workers.get(workerId);
        long totalSalary = 0;

        for (Session s : w.history) {
            // 1. 计算 Session 与查询区间的交集
            int winStart = Math.max(s.start, startTs);
            int winEnd = Math.min(s.end, endTs);

            if (winStart < winEnd) {
                int totalOverlap = winEnd - winStart;
                int doubleOverlap = 0;

                // 2. 计算交集部分中有多少属于双倍时段
                for (int[] dp : doublePaidIntervals) {
                    int dpStart = Math.max(winStart, dp[0]);
                    int dpEnd = Math.min(winEnd, dp[1]);
                    if (dpStart < dpEnd) {
                        doubleOverlap += (dpEnd - dpStart);
                    }
                }
                // 工资 = (总时长 + 额外增加的双倍时长) * 基础薪资
                totalSalary += (long)(totalOverlap + doubleOverlap) * s.comp;
            }
        }
        return (int)totalSalary;
    }
}
