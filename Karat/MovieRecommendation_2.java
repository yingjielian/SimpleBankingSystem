package Jack2025.Karat;
import java.util.*;
public class MovieRecommendation_2 {
    public static List<String> recommendations(String user, List<List<String>> ratings) {
        // Step 1: Build a map of user to their movie ratings
        Map<String, Map<String, Integer>> userMovieRatings = new HashMap<>();
        // And a set of all movies for quick lookup
        Set<String> allMovies = new HashSet<>();

        for (List<String> rating : ratings) {
            String currentUser = rating.get(0);
            String movie = rating.get(1);
            int score = Integer.parseInt(rating.get(2));

            allMovies.add(movie);

            if (!userMovieRatings.containsKey(currentUser)) {
                userMovieRatings.put(currentUser, new HashMap<>());
            }
            userMovieRatings.get(currentUser).put(movie, score);
        }

        // Step 2: Find similar users (who watched and rated >3 the same movies)
        Set<String> similarUsers = new HashSet<>();
        Map<String, Integer> userMovies = userMovieRatings.getOrDefault(user, new HashMap<>());

        for (String otherUser : userMovieRatings.keySet()) {
            if (otherUser.equals(user)) continue; // skip self

            Map<String, Integer> otherUserMovies = userMovieRatings.get(otherUser);
            boolean isSimilar = false;

            for (String movie : userMovies.keySet()) {
                if (otherUserMovies.containsKey(movie) &&
                        userMovies.get(movie) > 3 &&
                        otherUserMovies.get(movie) > 3) {
                    isSimilar = true;
                    break;
                }
            }

            if (isSimilar) {
                similarUsers.add(otherUser);
            }
        }

        // Step 3: Find movies watched by similar users that the target user hasn't watched
        Set<String> recommendations = new HashSet<>();
        Set<String> userWatchedMovies = userMovies.keySet();

        for (String similarUser : similarUsers) {
            Map<String, Integer> similarUserMovies = userMovieRatings.get(similarUser);

            for (String movie : similarUserMovies.keySet()) {
                if (!userWatchedMovies.contains(movie)) {
                    recommendations.add(movie);
                }
            }
        }

        return new ArrayList<>(recommendations);
    }

    public static void main(String[] args) {
        List<List<String>> ratings = Arrays.asList(
                Arrays.asList("Alice", "Frozen", "5"),
                Arrays.asList("Bob", "Mad Max", "5"),
                Arrays.asList("Dennis", "Mad Max", "4"),
                Arrays.asList("Bob", "Lost In Translation", "5")
        );

        System.out.println(recommendations("Dennis", ratings)); // Should output ["Lost In Translation"]
    }
}
