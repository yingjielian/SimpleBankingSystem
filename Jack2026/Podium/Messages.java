package Jack2026.Podium;

import java.util.*;
public class Messages {
    public static List<String> getMessageStatus(List<Integer> timestamps, List<String> messages, int k) {
        List<String> result = new ArrayList<>();
        Map<String, Integer> lastSeen = new HashMap<>();

        for (int i = 0; i < messages.size(); i++) {
            String msg = messages.get(i);
            int time = timestamps.get(i);

            if (!lastSeen.containsKey(msg)) {
                // 第一次出现，直接投递
                result.add("true");
            } else {
                int prevTime = lastSeen.get(msg);
                if (time - prevTime >= k) {
                    result.add("true");
                } else {
                    result.add("false");
                }
            }

            // ⚠️ 无论 true / false，都要更新最近出现时间
            lastSeen.put(msg, time);
        }

        return result;
    }
}
