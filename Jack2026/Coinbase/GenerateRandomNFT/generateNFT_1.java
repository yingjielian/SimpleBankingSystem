package Jack2026.Coinbase.GenerateRandomNFT;

import java.util.*;

public class generateNFT_1 {
    public List<String> generateNFT(Map<String, Object> config, int n) {
        List<String> results = new ArrayList<>();

        // 1. 获取 traits 配置
        // 这里的强制类型转换基于题目给出的 Map 结构
        @SuppressWarnings("unchecked")
        Map<String, List<String>> traits = (Map<String, List<String>>) config.get("traits");

        if (traits == null || traits.isEmpty()) {
            return results;
        }

        Random random = new Random();
        List<String> keys = new ArrayList<>(traits.keySet());

        // 2. 循环生成 n 个 NFT
        for (int i = 0; i < n; i++) {
            StringBuilder nftBuilder = new StringBuilder();
            nftBuilder.append("{");

            // 3. 遍历每一个 trait 类别
            for (int j = 0; j < keys.size(); j++) {
                String key = keys.get(j);
                List<String> values = traits.get(key);

                // 随机选择一个值
                String randomValue = values.get(random.nextInt(values.size()));

                // 拼接 JSON 格式字符串
                nftBuilder.append("\"").append(key).append("\":\"").append(randomValue).append("\"");

                // 如果不是最后一个属性，添加逗号
                if (j < keys.size() - 1) {
                    nftBuilder.append(",");
                }
            }

            nftBuilder.append("}");
            results.add(nftBuilder.toString());
        }

        return results;
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println(" ========= Test 1  =========");
        generateNFT_1 solution = new generateNFT_1();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "config-1");
        config.put("size", "large");
        config.put("traits", Map.of(
                "nose", List.of("pointy", "tiny", "flat"),
                "mouth", List.of("small", "wide", "thin"),
                "eyes", List.of("blue", "green", "brown")
        ));
        List<String> result = solution.generateNFT(config, 5);
        printResult(result);
    }

    private static void test2() {
        System.out.println(" ========= Test 2  =========");
        generateNFT_1 solution = new generateNFT_1();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "config-2");
        config.put("size", "small");
        config.put("traits", Map.of(
                "color", List.of("red", "blue", "green"),
                "shape", List.of("circle", "square")
        ));
        List<String> result = solution.generateNFT(config, 3);
        printResult(result);
    }

    private static void test3() {
        System.out.println(" ========= Test 3  =========");
        generateNFT_1 solution = new generateNFT_1();
        Map<String, Object> config = new HashMap<>();
        config.put("name", "config-3");
        config.put("size", "large");
        config.put("traits", Map.of(
                "color", List.of("red", "blue", "green", "yellow", "purple"),
                "texture", List.of("smooth", "rough", "grainy"),
                "size", List.of("tiny", "small", "medium", "large")
        ));
        List<String> result = solution.generateNFT(config, 3);
        printResult(result);
    }

    private static void printResult(List<String> result){
        for(String each : result){
            System.out.println(each);
        }
    }
}
