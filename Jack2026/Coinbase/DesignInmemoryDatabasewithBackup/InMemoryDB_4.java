package Jack2026.Coinbase.DesignInmemoryDatabasewithBackup;

import java.util.*;

class InMemoryDB_4 {

    private static class RecordValue {
        String value;
        int expiry; // 绝对过期时间戳，Integer.MAX_VALUE 为永不过期

        RecordValue(String value, int expiry) {
            this.value = value;
            this.expiry = expiry;
        }
    }

    // 用于备份的结构，存储值和备份时的剩余 TTL
    private static class SnapshotValue {
        String value;
        int remainingTtl; // -1 表示永不过期

        SnapshotValue(String value, int remainingTtl) {
            this.value = value;
            this.remainingTtl = remainingTtl;
        }
    }

    private Map<String, Map<String, RecordValue>> database;
    private final TreeMap<Integer, Map<String, Map<String, SnapshotValue>>> backups;

    public InMemoryDB_4() {
        this.database = new HashMap<>();
        this.backups = new TreeMap<>();
    }

    // --- 基础操作 (继承自 Follow-up 2) ---

    public void setDataAt(String key, String field, String value, int timestamp) {
        database.computeIfAbsent(key, k -> new HashMap<>())
                .put(field, new RecordValue(value, Integer.MAX_VALUE));
    }

    public void setDataAtWithTtl(String key, String field, String value, int timestamp, int ttl) {
        database.computeIfAbsent(key, k -> new HashMap<>())
                .put(field, new RecordValue(value, timestamp + ttl));
    }

    public String getDataAt(String key, String field, int timestamp) {
        Map<String, RecordValue> record = database.get(key);
        if (record != null) {
            RecordValue rv = record.get(field);
            if (rv != null && rv.expiry > timestamp) {
                return rv.value;
            }
        }
        return "";
    }

    public boolean deleteDataAt(String key, String field, int timestamp) {
        Map<String, RecordValue> record = database.get(key);
        if (record != null) {
            RecordValue rv = record.get(field);
            if (rv != null && rv.expiry > timestamp) {
                record.remove(field);
                return true;
            }
        }
        return false;
    }

    public List<String> scanDataAt(String key, int timestamp) {
        return scanDataByPrefixAt(key, "", timestamp);
    }

    public List<String> scanDataByPrefixAt(String key, String prefix, int timestamp) {
        Map<String, RecordValue> record = database.get(key);
        if (record == null) return new ArrayList<>();

        List<String> result = new ArrayList<>();
        List<String> sortedFields = new ArrayList<>();
        for (String field : record.keySet()) {
            RecordValue rv = record.get(field);
            if (rv.expiry > timestamp && field.startsWith(prefix)) {
                sortedFields.add(field);
            }
        }
        Collections.sort(sortedFields);
        for (String f : sortedFields) {
            result.add(f + "(" + record.get(f).value + ")");
        }
        return result;
    }

    // --- Follow-up 3: Backup & Restore ---

    public int backup(int timestamp) {
        Map<String, Map<String, SnapshotValue>> currentSnapshot = new HashMap<>();
        int activeRecordsCount = 0;

        for (Map.Entry<String, Map<String, RecordValue>> entry : database.entrySet()) {
            Map<String, SnapshotValue> snapshotRecord = new HashMap<>();
            for (Map.Entry<String, RecordValue> fieldEntry : entry.getValue().entrySet()) {
                RecordValue rv = fieldEntry.getValue();
                if (rv.expiry > timestamp) {
                    int remaining = (rv.expiry == Integer.MAX_VALUE) ? -1 : (rv.expiry - timestamp);
                    snapshotRecord.put(fieldEntry.getKey(), new SnapshotValue(rv.value, remaining));
                }
            }
            if (!snapshotRecord.isEmpty()) {
                currentSnapshot.put(entry.getKey(), snapshotRecord);
                activeRecordsCount++;
            }
        }
        backups.put(timestamp, currentSnapshot);
        return activeRecordsCount;
    }

    public void restore(int timestamp, int timestampToRestore) {
        // 找到最新且不大于 timestampToRestore 的备份
        Integer backupKey = backups.floorKey(timestampToRestore);
        if (backupKey == null) return;

        Map<String, Map<String, SnapshotValue>> snapshot = backups.get(backupKey);

        // 覆盖当前数据库
        database = new HashMap<>();
        for (Map.Entry<String, Map<String, SnapshotValue>> entry : snapshot.entrySet()) {
            Map<String, RecordValue> restoredRecord = new HashMap<>();
            for (Map.Entry<String, SnapshotValue> fieldEntry : entry.getValue().entrySet()) {
                SnapshotValue sv = fieldEntry.getValue();
                int newExpiry = (sv.remainingTtl == -1) ? Integer.MAX_VALUE : (timestamp + sv.remainingTtl);
                restoredRecord.put(fieldEntry.getKey(), new RecordValue(sv.value, newExpiry));
            }
            database.put(entry.getKey(), restoredRecord);
        }
    }
}
