package Jack2025.SOFI;
import java.util.*;
public class LoggerRateLimiter_359 {
    HashMap<String, Integer> map;
    public LoggerRateLimiter_359() {
        map = new HashMap<>();
    }

    public boolean shouldPrintMessage(int timestamp, String message) {
        if(!map.containsKey(message)) {
            map.put(message, timestamp);
            return true;
        }

        if(map.get(message) + 10 <= timestamp) {
            map.put(message, timestamp);
            return true;
        }

        return false;
    }
}
