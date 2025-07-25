package Jack2025.Karat;
import java.util.*;
public class PickingRestaurant {

    public static String restaurantRecs(String[][] friends, String[][] likes, String person1, String person2) {
        // Step 1: Find mutual friends between person1 and person2
        Set<String> person1Friends = new HashSet<>();
        Set<String> person2Friends = new HashSet<>();

        for (String[] pair : friends) {
            if (pair[0].equals(person1)) {
                person1Friends.add(pair[1]);
            } else if (pair[1].equals(person1)) {
                person1Friends.add(pair[0]);
            }

            if (pair[0].equals(person2)) {
                person2Friends.add(pair[1]);
            } else if (pair[1].equals(person2)) {
                person2Friends.add(pair[0]);
            }
        }

        // Find intersection of friends
        Set<String> mutualFriends = new HashSet<>(person1Friends);
        mutualFriends.retainAll(person2Friends);

        if (mutualFriends.isEmpty()) {
            return null;
        }

        // Step 2: Get restaurants liked by person1 and person2
        Set<String> person1Likes = new HashSet<>();
        Set<String> person2Likes = new HashSet<>();

        for (String[] likeEntry : likes) {
            if (likeEntry[0].equals(person1)) {
                for (int i = 1; i < likeEntry.length; i++) {
                    person1Likes.add(likeEntry[i]);
                }
            } else if (likeEntry[0].equals(person2)) {
                for (int i = 1; i < likeEntry.length; i++) {
                    person2Likes.add(likeEntry[i]);
                }
            }
        }

        // Step 3: Count restaurant popularity among mutual friends
        Map<String, Integer> restaurantCounts = new HashMap<>();

        for (String[] likeEntry : likes) {
            String person = likeEntry[0];
            if (mutualFriends.contains(person)) {
                for (int i = 1; i < likeEntry.length; i++) {
                    String restaurant = likeEntry[i];
                    if (!person1Likes.contains(restaurant) && !person2Likes.contains(restaurant)) {
                        restaurantCounts.put(restaurant, restaurantCounts.getOrDefault(restaurant, 0) + 1);
                    }
                }
            }
        }

        if (restaurantCounts.isEmpty()) {
            return null;
        }

        // Step 4: Find restaurant(s) with highest count
        int maxCount = Collections.max(restaurantCounts.values());
        List<String> candidates = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : restaurantCounts.entrySet()) {
            if (entry.getValue() == maxCount) {
                candidates.add(entry.getKey());
            }
        }

        // If multiple candidates, return any (or first one as per requirements)
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    public static void main(String[] args) {
        // Test case 1
        String[][] friends1 = {
                {"Ted", "Lily"},
                {"Ted", "Robin"},
                {"Lily", "Robin"},
                {"Ted", "Marshall"},
                {"Marshall", "Lily"}
        };

        String[][] likes1 = {
                {"Lily", "Restaurant_1", "Restaurant_17", "Restaurant_3"},
                {"Ted", "Restaurant_17", "Restaurant_1"},
                {"Robin", "Restaurant_5"},
                {"Marshall", "Restaurant_17", "Restaurant_5", "Restaurant_4"}
        };

        System.out.println(restaurantRecs(friends1, likes1, "Ted", "Lily")); // Should print Restaurant_5

        // Other test cases would be similar
    }
}
