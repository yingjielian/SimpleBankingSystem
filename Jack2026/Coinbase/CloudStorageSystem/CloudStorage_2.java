package Jack2026.Coinbase.CloudStorageSystem;

import java.util.*;

class CloudStorage_2 {
    // 基础存储：文件名 -> 大小
    private Map<String, Integer> storage;

    public CloudStorage_2() {
        this.storage = new HashMap<>();
    }

    public boolean addFile(String name, int size) {
        if (storage.containsKey(name)) {
            return false;
        }
        storage.put(name, size);
        return true;
    }

    public boolean copyFile(String nameFrom, String nameTo) {
        if (!storage.containsKey(nameFrom) || storage.containsKey(nameTo)) {
            return false;
        }
        storage.put(nameTo, storage.get(nameFrom));
        return true;
    }

    public int getFileSize(String name) {
        return storage.getOrDefault(name, -1);
    }

    /**
     * 根据前缀和后缀查找文件
     * 排序规则：1. 大小降序；2. 文件名升序 (Lexicographical)
     */
    public List<String> findFile(String prefix, String suffix) {
        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        // 1. 遍历所有文件，过滤符合前缀和后缀的文件
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            String name = entry.getKey();
            if (name.startsWith(prefix) && name.endsWith(suffix)) {
                matches.add(entry);
            }
        }

        // 2. 排序：大小降序，若大小相同则按文件名字典序升序
        Collections.sort(matches, (a, b) -> {
            if (!a.getValue().equals(b.getValue())) {
                // 大小降序
                return b.getValue().compareTo(a.getValue());
            }
            // 文件名升序
            return a.getKey().compareTo(b.getKey());
        });

        // 3. 格式化输出为 "name(size)"
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : matches) {
            result.add(entry.getKey() + "(" + entry.getValue() + ")");
        }

        return result;
    }
}
