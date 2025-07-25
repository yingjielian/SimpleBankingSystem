package Jack2025.Karat;
import java.util.*;
public class DomainAnalysis_3 {
    public static List<String> analyzeAdData(List<String> completedPurchaseUserIds,
                                             List<String> adClicks,
                                             List<String> allUserIps) {
        // Create a set of user IDs who completed a purchase for quick lookup
        Set<String> purchasedUserIds = new HashSet<>(completedPurchaseUserIds);

        // Create a map from IP address to user ID
        Map<String, String> ipToUserId = new HashMap<>();
        for (String userIp : allUserIps) {
            String[] parts = userIp.split(",");
            String userId = parts[0];
            String ip = parts[1];
            ipToUserId.put(ip, userId);
        }

        // Maps to keep track of total clicks and bought clicks per ad text
        Map<String, Integer> totalClicks = new HashMap<>();
        Map<String, Integer> boughtClicks = new HashMap<>();

        for (String adClick : adClicks) {
            String[] parts = adClick.split(",");
            String ip = parts[0];
            String adText = parts[2];

            // Update total clicks for the ad text
            totalClicks.put(adText, totalClicks.getOrDefault(adText, 0) + 1);

            // Check if the click was from a user who made a purchase
            String userId = ipToUserId.get(ip);
            if (userId != null && purchasedUserIds.contains(userId)) {
                boughtClicks.put(adText, boughtClicks.getOrDefault(adText, 0) + 1);
            }
        }

        // Prepare the result
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totalClicks.entrySet()) {
            String adText = entry.getKey();
            int total = entry.getValue();
            int bought = boughtClicks.getOrDefault(adText, 0);
            result.add(bought + " of " + total + " " + adText);
        }

        return result;
    }

    public static void main(String[] argv) {
        List<String> completedPurchaseUserIds = Arrays.asList(
                "3123122444", "234111110", "8321125440", "99911063"
        );

        List<String> adClicks = Arrays.asList(
                "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
                "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
                "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
                "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
                "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
                "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens"
        );

        List<String> allUserIps = Arrays.asList(
                "2339985511,122.121.0.155",
                "234111110,122.121.0.1",
                "3123122444,92.130.6.145",
                "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
                "8321125440,82.1.106.8",
                "99911063,92.130.6.144"
        );

        List<String> result = analyzeAdData(completedPurchaseUserIds, adClicks, allUserIps);
        for (String line : result) {
            System.out.println(line);
        }
    }
}
