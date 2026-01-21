package Jack2026.Coinbase.DesignInmemoryDatabasewithBackup;

import java.util.*;

class InMemoryDB_2 {
    // 外层 Map 存储 Key -> (Field -> Value)
    private final Map<String, Map<String, String>> database;

    public InMemoryDB_2() {
        this.database = new HashMap<>();
    }

    public void setData(String key, String field, String value) {
        database.computeIfAbsent(key, k -> new HashMap<>()).put(field, value);
    }

    public String getData(String key, String field) {
        Map<String, String> record = database.get(key);
        return (record != null) ? record.getOrDefault(field, "") : "";
    }

    public boolean deleteData(String key, String field) {
        Map<String, String> record = database.get(key);
        if (record != null) {
            return record.remove(field) != null;
        }
        return false;
    }

    /**
     * 返回指定 key 的所有字段，格式为 "field(value)"，按 field 字典序排序。
     */
    public List<String> scanData(String key) {
        Map<String, String> record = database.get(key);
        if (record == null || record.isEmpty()) {
            return new ArrayList<>();
        }

        // 提取并排序
        List<String> result = new ArrayList<>();
        List<String> sortedFields = new ArrayList<>(record.keySet());
        Collections.sort(sortedFields);

        for (String field : sortedFields) {
            result.add(field + "(" + record.get(field) + ")");
        }
        return result;
    }

    /**
     * 返回指定 key 中以 prefix 开头的字段，格式为 "field(value)"，按 field 字典序排序。
     */
    public List<String> scanDataByPrefix(String key, String prefix) {
        Map<String, String> record = database.get(key);
        if (record == null || record.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        List<String> sortedFields = new ArrayList<>(record.keySet());
        Collections.sort(sortedFields);

        for (String field : sortedFields) {
            if (field.startsWith(prefix)) {
                result.add(field + "(" + record.get(field) + ")");
            }
        }
        return result;
    }
}
