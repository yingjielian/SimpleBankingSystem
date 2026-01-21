package Jack2026.Coinbase.CloudStorageSystem;

import java.util.*;

class CloudStorage_1 {
    // 使用 HashMap 存储文件名及其对应的大小
    private Map<String, Integer> storage;

    public CloudStorage_1() {
        // 初始化存储空间
        this.storage = new HashMap<>();
    }

    /**
     * 添加新文件
     * @param name 文件名
     * @param size 文件大小
     * @return 如果添加成功返回 true，若文件已存在返回 false
     */
    public boolean addFile(String name, int size) {
        if (storage.containsKey(name)) {
            return false;
        }
        storage.put(name, size);
        return true;
    }

    /**
     * 复制文件
     * @param nameFrom 源文件名
     * @param nameTo 目标文件名
     * @return 如果复制成功返回 true，若源文件不存在或目标文件已存在返回 false
     */
    public boolean copyFile(String nameFrom, String nameTo) {
        // 校验：源文件必须存在，且目标文件不能已存在
        if (!storage.containsKey(nameFrom) || storage.containsKey(nameTo)) {
            return false;
        }

        // 获取源文件大小并存入目标文件名下
        int size = storage.get(nameFrom);
        storage.put(nameTo, size);
        return true;
    }

    /**
     * 获取文件大小
     * @param name 文件名
     * @return 文件大小，若不存在则返回 -1
     */
    public int getFileSize(String name) {
        // getOrDefault 可以优雅地处理不存在的情况
        return storage.getOrDefault(name, -1);
    }
}
