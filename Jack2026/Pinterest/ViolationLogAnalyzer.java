package Jack2026.Pinterest;

import java.util.*;

/**
 * Each log entry: [postId, policy, date] where date is "YYYY-MM-DD"
 *
 * Preprocessing:
 *  - postId -> unique policies count
 *  - policy -> unique postIds count
 *  - date   -> unique postIds count (and for range queries)
 *
 * Date strings in "YYYY-MM-DD" can be compared lexicographically for chronology.
 */
class ViolationLogAnalyzer {

    private final Map<String, Set<String>> postToPolicies = new HashMap<>();
    private final Map<String, Set<String>> policyToPosts = new HashMap<>();
    private final Map<String, Set<String>> dateToPosts = new HashMap<>();

    // For efficient date range queries in chronological order
    private final TreeMap<String, LinkedHashSet<String>> dateTree = new TreeMap<>();

    public ViolationLogAnalyzer(String[][] logs) {
        if (logs == null) return;

        // Copy + sort by date (stable tie-breaker by original index to keep deterministic order)
        List<String[]> list = new ArrayList<>(logs.length);
        for (String[] e : logs) {
            if (e == null || e.length < 3) continue;
            list.add(e);
        }

        // Stable sort: date asc, then keep original order if same date
        // (Java's Collections.sort is stable, so sorting by date alone is sufficient)
        list.sort(Comparator.comparing(a -> a[2]));

        // Build indexes
        for (String[] e : list) {
            String postId = e[0];
            String policy = e[1];
            String date = e[2];

            if (postId == null || policy == null || date == null) continue;

            // postId -> policies
            postToPolicies.computeIfAbsent(postId, k -> new HashSet<>()).add(policy);

            // policy -> posts
            policyToPosts.computeIfAbsent(policy, k -> new HashSet<>()).add(postId);

            // date -> posts
            dateToPosts.computeIfAbsent(date, k -> new HashSet<>()).add(postId);

            // date range structure: keep insertion order within each date
            dateTree.computeIfAbsent(date, k -> new LinkedHashSet<>()).add(postId);
        }
    }

    // Returns number of unique violation policies committed by this postId
    public int countViolationByPost(String postId) {
        Set<String> policies = postToPolicies.get(postId);
        return policies == null ? 0 : policies.size();
    }

    // Returns count of unique post IDs that violated this policy
    public int countByPolicy(String policy) {
        Set<String> posts = policyToPosts.get(policy);
        return posts == null ? 0 : posts.size();
    }

    // Counts distinct post IDs whose date exactly matches the provided date
    public int countByDate(String date) {
        Set<String> posts = dateToPosts.get(date);
        return posts == null ? 0 : posts.size();
    }

    // Returns post IDs whose dates fall between startDate and endDate (inclusive), no duplicates.
    // Order: chronological by date, and within the same date, in first-seen order.
    public List<String> postsInDateRange(String startDate, String endDate) {
        if (startDate == null || endDate == null) return Collections.emptyList();
        if (startDate.compareTo(endDate) > 0) return Collections.emptyList();

        NavigableMap<String, LinkedHashSet<String>> sub =
                dateTree.subMap(startDate, true, endDate, true);

        LinkedHashSet<String> result = new LinkedHashSet<>();
        for (LinkedHashSet<String> posts : sub.values()) {
            result.addAll(posts);
        }
        return new ArrayList<>(result);
    }

    // Quick demo
    public static void main(String[] args) {
        String[][] logs = new String[][]{
                {"0", "spam", "2022-01-03"},
                {"1", "spam", "2022-01-03"},
                {"0", "privacy", "2022-01-03"},
                {"2", "child", "2022-02-06"},
                {"3", "spam", "2022-02-06"}
        };

        ViolationLogAnalyzer analyzer = new ViolationLogAnalyzer(logs);
        System.out.println(analyzer.countViolationByPost("0"));         // 2
        System.out.println(analyzer.countByPolicy("spam"));             // 3
        System.out.println(analyzer.countByDate("2022-01-03"));         // 2
        System.out.println(analyzer.postsInDateRange("2022-01-03", "2022-02-06")); // [0, 1, 2, 3]
    }
}

