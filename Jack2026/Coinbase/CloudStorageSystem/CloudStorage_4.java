package Jack2026.Coinbase.CloudStorageSystem;

import java.util.*;

class CloudStorage_4 {
    private static class FileInfo {
        String name;
        int size;
        String ownerId;

        FileInfo(String name, int size, String ownerId) {
            this.name = name;
            this.size = size;
            this.ownerId = ownerId;
        }
    }

    private static class User {
        String userId;
        long capacity;
        long currentUsage;
        Map<String, FileInfo> ownedFiles = new HashMap<>();

        User(String userId, long capacity) {
            this.userId = userId;
            this.capacity = capacity;
            this.currentUsage = 0;
        }
    }

    private Map<String, FileInfo> allFiles;
    private Map<String, User> users;

    public CloudStorage_4() {
        allFiles = new HashMap<>();
        users = new HashMap<>();
        users.put("admin", new User("admin", Long.MAX_VALUE));
    }

    public boolean addUser(String userId, int capacity) {
        if (userId.equals("admin") || users.containsKey(userId)) return false;
        users.put(userId, new User(userId, capacity));
        return true;
    }

    public boolean addFile(String name, int size) {
        return addFileBy("admin", name, size) != -1;
    }

    public int addFileBy(String userId, String name, int size) {
        User user = users.get(userId);
        if (user == null || allFiles.containsKey(name)) return -1;
        if (user.currentUsage + size > user.capacity) return -1;

        FileInfo newFile = new FileInfo(name, size, userId);
        allFiles.put(name, newFile);
        user.ownedFiles.put(name, newFile);
        user.currentUsage += size;

        return (int) (user.capacity - user.currentUsage);
    }

    public boolean copyFile(String nameFrom, String nameTo) {
        FileInfo source = allFiles.get(nameFrom);
        if (source == null || allFiles.containsKey(nameTo)) return false;

        User owner = users.get(source.ownerId);
        if (owner.currentUsage + source.size > owner.capacity) return false;

        FileInfo copy = new FileInfo(nameTo, source.size, source.ownerId);
        allFiles.put(nameTo, copy);
        owner.ownedFiles.put(nameTo, copy);
        owner.currentUsage += source.size;
        return true;
    }

    public int getFileSize(String name) {
        FileInfo file = allFiles.get(name);
        return file == null ? -1 : file.size;
    }

    public List<String> findFile(String prefix, String suffix) {
        List<FileInfo> matches = new ArrayList<>();
        for (FileInfo file : allFiles.values()) {
            if (file.name.startsWith(prefix) && file.name.endsWith(suffix)) {
                matches.add(file);
            }
        }
        matches.sort((a, b) -> {
            if (a.size != b.size) return Integer.compare(b.size, a.size);
            return a.name.compareTo(b.name);
        });

        List<String> result = new ArrayList<>();
        for (FileInfo f : matches) {
            result.add(f.name + "(" + f.size + ")");
        }
        return result;
    }

    public int updateCapacity(String userId, int capacity) {
        User user = users.get(userId);
        if (user == null) return -1;

        user.capacity = capacity;
        int removedCount = 0;

        if (user.currentUsage > user.capacity) {
            List<FileInfo> filesToSort = new ArrayList<>(user.ownedFiles.values());
            filesToSort.sort((a, b) -> {
                if (a.size != b.size) return Integer.compare(b.size, a.size);
                return a.name.compareTo(b.name);
            });

            for (FileInfo file : filesToSort) {
                if (user.currentUsage <= user.capacity) break;
                allFiles.remove(file.name);
                user.ownedFiles.remove(file.name);
                user.currentUsage -= file.size;
                removedCount++;
            }
        }
        return removedCount;
    }

    // --- Follow-up 3: Compression Logic ---

    public int compressFile(String userId, String name) {
        User user = users.get(userId);
        FileInfo file = allFiles.get(name);

        // 校验：用户存在、文件存在、文件属于该用户
        if (user == null || file == null || !file.ownerId.equals(userId)) {
            return -1;
        }

        String newName = name + ".COMPRESSED";
        // 题目保证 compressFile 的 name 不会以 .COMPRESSED 结尾
        // 且 addFile 不能添加带大写字母的文件名，所以理论上 newName 不会冲突

        int oldSize = file.size;
        int newSize = oldSize / 2;

        // 移除旧文件
        allFiles.remove(name);
        user.ownedFiles.remove(name);
        user.currentUsage -= oldSize;

        // 添加压缩后的新文件
        FileInfo compressedFile = new FileInfo(newName, newSize, userId);
        allFiles.put(newName, compressedFile);
        user.ownedFiles.put(newName, compressedFile);
        user.currentUsage += newSize;

        return (int) (user.capacity - user.currentUsage);
    }

    public int decompressFile(String userId, String name) {
        User user = users.get(userId);
        FileInfo file = allFiles.get(name);

        // 校验：用户存在、文件存在、文件属于该用户
        if (user == null || file == null || !file.ownerId.equals(userId)) {
            return -1;
        }

        // 获取原文件名（去掉 .COMPRESSED）
        String originalName = name.substring(0, name.length() - ".COMPRESSED".length());
        int originalSize = file.size * 2;

        // 校验：解压后文件名是否已存在，以及空间是否足够
        if (allFiles.containsKey(originalName)) return -1;

        // 关键：解压增加的空间 = originalSize - 当前压缩文件大小 = file.size
        if (user.currentUsage + file.size > user.capacity) {
            return -1;
        }

        // 移除压缩文件
        allFiles.remove(name);
        user.ownedFiles.remove(name);
        user.currentUsage -= file.size;

        // 还原原文件
        FileInfo originalFile = new FileInfo(originalName, originalSize, userId);
        allFiles.put(originalName, originalFile);
        user.ownedFiles.put(originalName, originalFile);
        user.currentUsage += originalSize;

        return (int) (user.capacity - user.currentUsage);
    }
}
