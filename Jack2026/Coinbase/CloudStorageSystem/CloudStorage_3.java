package Jack2026.Coinbase.CloudStorageSystem;

import java.util.*;

class CloudStorage_3 {
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

    public CloudStorage_3() {
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
        // findFile 排序规则：大小降序，名字升序
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
            // 修正后的删除排序规则：
            // 大小降序，若大小相同，则名字升序（因为题目说 largest files 包括字典序靠前的）
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
}
