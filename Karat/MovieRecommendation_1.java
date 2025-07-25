package Jack2025.Karat;
import java.util.*;
public class MovieRecommendation_1 {
    public static List<List<String>> grouping(List<List<String>> events, int n) {
        Map<String, Set<String>> followingMap = new HashMap<>();

        for (List<String> event : events) {
            String follower = event.get(0);
            String followed = event.get(1);
            String action = event.get(2);

            if (!followingMap.containsKey(follower)) {
                followingMap.put(follower, new HashSet<>());
            }

            Set<String> following = followingMap.get(follower);

            if (action.equals("CONNECT")) {
                following.add(followed);
            } else if (action.equals("DISCONNECT")) {
                following.remove(followed);
            }
        }

        List<String> lessThanN = new ArrayList<>();
        List<String> nOrMore = new ArrayList<>();

        for (String user : followingMap.keySet()) {
            int count = followingMap.get(user).size();
            if (count < n) {
                lessThanN.add(user);
            } else {
                nOrMore.add(user);
            }
        }

        // Also include users who are followed but never followed anyone
        // (they won't appear in followingMap but might be in the input)
        Set<String> allUsers = new HashSet<>();
        for (List<String> event : events) {
            allUsers.add(event.get(0));
            allUsers.add(event.get(1));
        }

        for (String user : allUsers) {
            if (!followingMap.containsKey(user)) {
                lessThanN.add(user);
            }
        }

        List<List<String>> result = new ArrayList<>();
        result.add(lessThanN);
        result.add(nOrMore);

        return result;
    }

    public static void main(String[] args) {
        List<List<String>> events = Arrays.asList(
                Arrays.asList("Nicole", "Alice", "CONNECT"),
                Arrays.asList("Nicole", "Alice", "DISCONNECT"),
                Arrays.asList("Charlie", "Alice", "CONNECT"),
                Arrays.asList("Edward", "Alice", "CONNECT")
        );

        System.out.println(grouping(events, 3));
        System.out.println(grouping(events, 1));
        System.out.println(grouping(events, 10));
    }

}
