package Jack2026.Coinbase.DesignInmemoryDatabasewithBackup;

import java.util.*;

class InMemoryDB_3 {

    // 内部类，用于存储值及其过期时间
    private static class RecordValue {
        String value;
        int expiry; // 过期时间戳，Integer.MAX_VALUE 表示永不过期

        RecordValue(String value, int expiry) {
            this.value = value;
            this.expiry = expiry;
        }

        boolean isExpired(int currentTimestamp) {
            return currentTimestamp >= expiry;
        }
    }

    // Key -> (Field -> RecordValue)
    private final Map<String, Map<String, RecordValue>> database;

    public InMemoryDB_3() {
        this.database = new HashMap<>();
    }

    // --- 基础操作（向后兼容） ---

    public void setData(String key, String field, String value) {
        setDataAt(key, field, value, 0);
    }

    public String getData(String key, String field) {
        return getDataAt(key, field, Integer.MAX_VALUE); // 永久有效视角
    }

    public boolean deleteData(String key, String field) {
        return deleteDataAt(key, field, 0);
    }

    public List<String> scanData(String key) {
        return scanDataAt(key, Integer.MAX_VALUE);
    }

    public List<String> scanDataByPrefix(String key, String prefix) {
        return scanDataByPrefixAt(key, prefix, Integer.MAX_VALUE);
    }

    // --- 带 Timestamp 和 TTL 的扩展操作 ---

    public void setDataAt(String key, String field, String value, int timestamp) {
        // 默认永不过期
        database.computeIfAbsent(key, k -> new HashMap<>())
                .put(field, new RecordValue(value, Integer.MAX_VALUE));
    }

    public void setDataAtWithTtl(String key, String field, String value, int timestamp, int ttl) {
        int expiry = timestamp + ttl;
        database.computeIfAbsent(key, k -> new HashMap<>())
                .put(field, new RecordValue(value, expiry));
    }

    public String getDataAt(String key, String field, int timestamp) {
        Map<String, RecordValue> record = database.get(key);
        if (record != null) {
            RecordValue rv = record.get(field);
            if (rv != null && !rv.isExpired(timestamp)) {
                return rv.value;
            }
        }
        return "";
    }

    public boolean deleteDataAt(String key, String field, int timestamp) {
        Map<String, RecordValue> record = database.get(key);
        if (record != null) {
            RecordValue rv = record.get(field);
            if (rv != null && !rv.isExpired(timestamp)) {
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
        if (record == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        // 获取所有未过期的字段
        List<String> sortedFields = new ArrayList<>();
        for (Map.Entry<String, RecordValue> entry : record.entrySet()) {
            if (!entry.getValue().isExpired(timestamp) && entry.getKey().startsWith(prefix)) {
                sortedFields.add(entry.getKey());
            }
        }

        // 字典序排序
        Collections.sort(sortedFields);

        for (String field : sortedFields) {
            result.add(field + "(" + record.get(field).value + ")");
        }
        return result;
    }
}
