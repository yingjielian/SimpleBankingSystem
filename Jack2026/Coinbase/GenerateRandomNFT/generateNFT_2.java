package Jack2026.Coinbase.GenerateRandomNFT;

import java.util.*;

public class generateNFT_2 {
    public List<String> generateUniqueNFT(Map<String, Object> config, int n) {
        // 1. 获取 traits 配置
        @SuppressWarnings("unchecked")
        Map<String, List<String>> traits = (Map<String, List<String>>) config.get("traits");

        if (traits == null || traits.isEmpty()) {
            if (n > 0) throw new IllegalArgumentException("No traits available to generate NFTs.");
            return new ArrayList<>();
        }

        // 2. 预先计算最大可能的唯一组合数，防止死循环
        long maxPossible = 1;
        for (List<String> values : traits.values()) {
            maxPossible *= values.size();
            // 如果 maxPossible 已经超过 n，可以停止计算以防 long 溢出
            if (maxPossible >= n) break;
        }

        if (n > maxPossible) {
            throw new IllegalArgumentException("n is too large for unique combinations. Max: " + maxPossible);
        }

        // 3. 使用 Set 存储生成的 NFT 以确保唯一性
        Set<String> uniqueNFTs = new LinkedHashSet<>(); // 使用 LinkedHashSet 保持插入顺序
        Random random = new Random();
        List<String> keys = new ArrayList<>(traits.keySet());

        while (uniqueNFTs.size() < n) {
            StringBuilder nftBuilder = new StringBuilder();
            nftBuilder.append("{");

            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                List<String> values = traits.get(key);
                String randomValue = values.get(random.nextInt(values.size()));

                nftBuilder.append("\"").append(key).append("\":\"").append(randomValue).append("\"");
                if (i < keys.size() - 1) {
                    nftBuilder.append(",");
                }
            }
            nftBuilder.append("}");

            // Set 自动处理去重，只有不存在时才会增加 size
            uniqueNFTs.add(nftBuilder.toString());
        }

        return new ArrayList<>(uniqueNFTs);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println(" ========= Test 1  =========");
        generateNFT_2 solution = new generateNFT_2();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "config-1");
        config.put("size", "large");
        config.put("traits", Map.of(
                "nose", List.of("pointy", "tiny"),
                "mouth", List.of("small", "wide")
        ));

        // 2x2=4，正好可以生成4个
        List<String> result = solution.generateUniqueNFT(config, 4);
        printResult(result);
    }

    private static void test2() {
        System.out.println(" ========= Test 2  =========");
        generateNFT_2 solution = new generateNFT_2();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "simple");
        config.put("size", "small");
        config.put("traits", Map.of(
                "color", List.of("red", "blue", "green"),
                "shape", List.of("circle", "square")
        ));

        try {
            // 3x2=6, 10 > 6, 应该抛异常
            List<String> result = solution.generateUniqueNFT(config, 10);
            printResult(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }

    private static void test3() {
        System.out.println(" ========= Test 3  =========");
        generateNFT_2 solution = new generateNFT_2();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "limited");
        config.put("size", "medium");
        config.put("traits", Map.of(
                "pattern", List.of("striped", "spotted", "plain", "checkered")
        ));

        try {
            // 4 < 10, 应该抛异常
            List<String> result = solution.generateUniqueNFT(config, 10);
            printResult(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }

    private static void printResult(List<String> result){
        for(String each : result){
            System.out.println(each);
        }
    }
}
