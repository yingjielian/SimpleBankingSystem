package Jack2025.Karat;
import java.util.*;
public class DomainAnalysis_1 {
    public static Map<String, Integer> calculateClicksByDomain(String[] counts) {
        Map<String, Integer> domainCounts = new HashMap<>();

        for (String entry : counts) {
            String[] parts = entry.split(",");
            int count = Integer.parseInt(parts[0]);
            String domain = parts[1];

            // Split domain into parts
            String[] domainParts = domain.split("\\.");

            // Generate all possible subdomains
            for (int i = 0; i < domainParts.length; i++) {
                String subdomain = String.join(".", Arrays.copyOfRange(domainParts, i, domainParts.length));
                domainCounts.put(subdomain, domainCounts.getOrDefault(subdomain, 0) + count);
            }
        }

        return domainCounts;
    }

    public static void main(String[] argv) {
        String[] counts = {
                "900,google.com",
                "60,mail.yahoo.com",
                "10,mobile.sports.yahoo.com",
                "40,sports.yahoo.com",
                "300,yahoo.com",
                "10,stackoverflow.com",
                "20,overflow.com",
                "5,com.com",
                "2,en.wikipedia.org",
                "1,m.wikipedia.org",
                "1,mobile.sports",
                "1,google.co.uk"
        };

        Map<String, Integer> result = calculateClicksByDomain(counts);

        // Print the result in alphabetical order for better readability
        result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
