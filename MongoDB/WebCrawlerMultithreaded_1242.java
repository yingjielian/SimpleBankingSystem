package Jack2025.MongoDB;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class WebCrawlerMultithreaded_1242{
    /**
     * This is the entry point:
     * Extracts the hostname from the startUrl (using getHostname).
     * Creates a thread-safe visited set (ConcurrentHashMap.newKeySet()).
     * Adds the startUrl to visited.
     * Calls the recursive crawl method and collects all URLs into a List<String>.
     * So this ensures we don’t revisit links and stay under the same hostname.
     * @param startUrl
     * @param htmlParser
     * @return
     */
    public List<String> crawl(String startUrl, HtmlParser htmlParser)
    {
        String hostname = getHostname(startUrl);

        Set<String> visited = ConcurrentHashMap.newKeySet();
        visited.add(startUrl);

        return crawl(startUrl, htmlParser, hostname, visited)
                .collect(Collectors.toList());
    }

    /**
     * This method:
     * Calls htmlParser.getUrls(startUrl) (blocking call to fetch child URLs).
     * Converts them into a parallel stream (multi-threaded crawling).
     * Applies filters:
     * isSameHostname(url, hostname) → ensures we only stay inside the domain of startUrl.
     * visited.add(url) → ensures we crawl each URL only once.
     * For each new URL, it recursively calls crawl(...).
     * Finally, it concatenates:
     * Stream.of(startUrl) (the current page)
     * With the recursive results from child pages.
     * So it’s basically DFS traversal, but parallelized.
     * @param startUrl
     * @param htmlParser
     * @param hostname
     * @param visited
     * @return
     */
    private Stream<String> crawl(String startUrl, HtmlParser htmlParser, String hostname, Set<String> visited)
    {
        // Get all URLs from the current page
        Stream<String> stream = htmlParser.getUrls(startUrl)
                .parallelStream() // process links in parallel (multi-threading)
                .filter(url -> isSameHostname(url, hostname))// keep only URLs with the same hostname
                .filter(url -> visited.add(url))// keep only new URLs (avoid revisiting)
                // For each valid new URL, recursively crawl deeper
                .flatMap(url -> crawl(url, htmlParser, hostname, visited));

        // Combine:
        // - the current URL (as a Stream)
        // - all recursively crawled URLs
        return Stream.concat(Stream.of(startUrl), stream);
    }

    private String getHostname(String url)
    {
        // Look inside the string url and find the first '/' character starting from position 7.
        //If it finds one, it returns its index (position).
        //If not, it returns -1.
        //String url = "http://news.yahoo.com/news/topics/";
        //"http://" → 7 characters.
        //Starting at index 7, the code searches for the next '/'.
        //It finds one at "http://news.yahoo.com/...".
        int index = url.indexOf('/', 7);

        return (index != -1) ? url.substring(0, index) : url;
    }

    /**
     * Check if the given URL belongs to the same hostname
     * @param url
     * @param hostname
     * @return
     */
    private boolean isSameHostname(String url, String hostname)
    {
        // 1. First, make sure the URL actually starts with the hostname
        // e.g. hostname = "http://news.yahoo.com", url = "http://google.com"
        if(!url.startsWith(hostname))
        {
            return false;
        }

        // 2. If the URL equals the hostname exactly, it's valid
        //    e.g. "http://news.yahoo.com" == "http://news.yahoo.com"
        // OR
        // 3. If the next character after the hostname is '/', then it's still part of the same domain
        //    e.g. "http://news.yahoo.com/us" → after hostname comes '/'
        // URL is exactly the hostname
        // Or it continues with a path
        return url.length() == hostname.length() || url.charAt(hostname.length()) == '/';
    }

    static class MockHtmlParser implements HtmlParser {
        private final Map<String, List<String>> graph;

        public MockHtmlParser(Map<String, List<String>> graph) {
            this.graph = graph;
        }

        @Override
        public List<String> getUrls(String url) {
            return graph.getOrDefault(url, Collections.emptyList());
        }
    }

    public static class TestCrawler {
        public static void main(String[] args) {
            WebCrawlerMultithreaded_1242 crawler = new WebCrawlerMultithreaded_1242();

            // Test Case 1: Example from problem
            Map<String, List<String>> graph1 = new HashMap<>();
            graph1.put("http://news.yahoo.com/news/topics/", Arrays.asList(
                    "http://news.yahoo.com",
                    "http://news.yahoo.com/news"
            ));
            graph1.put("http://news.yahoo.com/news", Arrays.asList(
                    "http://news.yahoo.com/us"
            ));
            graph1.put("http://news.yahoo.com", Collections.emptyList());
            graph1.put("http://news.yahoo.com/us", Collections.emptyList());

            HtmlParser parser1 = new MockHtmlParser(graph1);

            System.out.println("Test Case 1:");
            System.out.println(crawler.crawl("http://news.yahoo.com/news/topics/", parser1));

            // Expected: all yahoo.com pages, no google.com


            // Test Case 2: Different hostname
            Map<String, List<String>> graph2 = new HashMap<>();
            graph2.put("http://leetcode.com/problems", Arrays.asList(
                    "http://leetcode.com/contest",
                    "http://example.org/test"
            ));
            graph2.put("http://leetcode.com/contest", Collections.emptyList());

            HtmlParser parser2 = new MockHtmlParser(graph2);

            System.out.println("\nTest Case 2:");
            System.out.println(crawler.crawl("http://leetcode.com/problems", parser2));

            // Expected: ["http://leetcode.com/problems", "http://leetcode.com/contest"]
            // Should not include example.org


            // Test Case 3: Cyclic graph
            Map<String, List<String>> graph3 = new HashMap<>();
            graph3.put("http://a.com", Arrays.asList("http://a.com/b"));
            graph3.put("http://a.com/b", Arrays.asList("http://a.com/c"));
            graph3.put("http://a.com/c", Arrays.asList("http://a.com"));

            HtmlParser parser3 = new MockHtmlParser(graph3);

            System.out.println("\nTest Case 3:");
            System.out.println(crawler.crawl("http://a.com", parser3));

            // Expected: ["http://a.com", "http://a.com/b", "http://a.com/c"]


            // Test Case 4: Single URL (no children)
            Map<String, List<String>> graph4 = new HashMap<>();
            graph4.put("http://single.com", Collections.emptyList());

            HtmlParser parser4 = new MockHtmlParser(graph4);

            System.out.println("\nTest Case 4:");
            System.out.println(crawler.crawl("http://single.com", parser4));

            // Expected: ["http://single.com"]
        }
    }
}
