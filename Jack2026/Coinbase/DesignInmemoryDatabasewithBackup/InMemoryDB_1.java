package Jack2026.Coinbase.DesignInmemoryDatabasewithBackup;

import java.util.*;

class InMemoryDB_1 {
    // 使用嵌套 HashMap 存储数据：key -> (field -> value)
    private final Map<String, Map<String, String>> database;

    public InMemoryDB_1() {
        this.database = new HashMap<>();
    }

    /**
     * 插入或更新字段值。
     */
    public void setData(String key, String field, String value) {
        // 如果 key 不存在，则创建一个新的 HashMap 关联到该 key
        database.computeIfAbsent(key, k -> new HashMap<>()).put(field, value);
    }

    /**
     * 获取指定 key 下特定 field 的值。
     */
    public String getData(String key, String field) {
        Map<String, String> record = database.get(key);
        if (record == null) {
            return "";
        }
        // 如果 field 不存在，getOrDefault 返回空字符串
        return record.getOrDefault(field, "");
    }

    /**
     * 删除指定 key 下的 field。
     */
    public boolean deleteData(String key, String field) {
        Map<String, String> record = database.get(key);
        if (record == null) {
            return false;
        }

        // remove 方法返回被删除的 value，如果返回 null 说明 field 不存在
        return record.remove(field) != null;
    }
}
