package Jack2026.Podium;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    class Window {
        long start;   // 当前窗口的起始时间（毫秒）
        int count;    // 当前窗口内的请求次数

        Window(long start, int count) {
            this.start = start;
            this.count = count;
        }
    }
    private final int limit;
    private final long windowSize;
    private Map<String, Window> map = new HashMap<>();

    public RateLimiter(int limit, long windowSize) {
        this.limit = limit;
        this.windowSize = windowSize;
    }

    public boolean allow(String userId, long timestamp) {
        Window w = map.get(userId);
        if (w == null || timestamp - w.start >= windowSize) {
            map.put(userId, new Window(timestamp, 1));
            return true;
        }
        if (w.count < limit) {
            w.count++;
            return true;
        }
        return false;
    }
}

