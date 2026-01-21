package Jack2026.Coinbase.GenerateRandomNFT;

import java.util.*;

public class generateNFT_3 {

    // 内部类，用于存储每个 Trait 的前缀和及对应名称
    private static class WeightedTrait {
        List<String> names = new ArrayList<>();
        int[] prefixSums;
        int totalWeight;

        public WeightedTrait(List<Map<String, Object>> traitOptions) {
            prefixSums = new int[traitOptions.size()];
            int currentSum = 0;
            for (int i = 0; i < traitOptions.size(); i++) {
                Map<String, Object> option = traitOptions.get(i);
                names.add((String) option.get("name"));
                currentSum += (Integer) option.get("weight");
                prefixSums[i] = currentSum;
            }
            totalWeight = currentSum;
        }

        // 二分查找进行权重采样
        public String pickRandom(Random random) {
            int target = random.nextInt(totalWeight) + 1;
            int low = 0, high = prefixSums.length - 1;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (prefixSums[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            return names.get(low);
        }
    }

    public List<String> generateWeightedNFT(Map<String, Object> config, int n) {
        @SuppressWarnings("unchecked")
        Map<String, List<Map<String, Object>>> traitsConfig =
                (Map<String, List<Map<String, Object>>>) config.get("traits");

        if (traitsConfig == null || traitsConfig.isEmpty()) {
            if (n > 0) throw new IllegalArgumentException("No traits provided.");
            return new ArrayList<>();
        }

        // 1. 预处理权重数据并检查最大容量
        Map<String, WeightedTrait> weightedTraitsMap = new LinkedHashMap<>();
        long maxPossible = 1;
        for (String key : traitsConfig.keySet()) {
            List<Map<String, Object>> options = traitsConfig.get(key);
            weightedTraitsMap.put(key, new WeightedTrait(options));

            maxPossible *= options.size();
            // 防止 long 溢出，且如果已经满足 n 则无需继续乘
            if (maxPossible < 0) maxPossible = Long.MAX_VALUE;
        }

        if (n > maxPossible) {
            throw new IllegalArgumentException("n is too large for unique combinations");
        }

        // 2. 循环生成唯一的 NFT
        Set<String> resultSet = new LinkedHashSet<>();
        Random random = new Random();
        List<String> traitKeys = new ArrayList<>(weightedTraitsMap.keySet());

        while (resultSet.size() < n) {
            StringBuilder nftBuilder = new StringBuilder("{");
            for (int i = 0; i < traitKeys.size(); i++) {
                String key = traitKeys.get(i);
                String selectedValue = weightedTraitsMap.get(key).pickRandom(random);

                nftBuilder.append("\"").append(key).append("\":\"").append(selectedValue).append("\"");
                if (i < traitKeys.size() - 1) {
                    nftBuilder.append(",");
                }
            }
            nftBuilder.append("}");
            resultSet.add(nftBuilder.toString());
        }

        return new ArrayList<>(resultSet);
    }

    // ... (Main 及 TestCases 部分保持原样)
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    private static void test1() {
        System.out.println(" ========= Test 1  =========");
        generateNFT_3 solution = new generateNFT_3();
        Map<String, Object> config = new HashMap<>();
        config.put("traits", Map.of(
                "nose", List.of(Map.of("name", "pointy", "weight", 1), Map.of("name", "tiny", "weight", 2), Map.of("name", "flat", "weight", 3)),
                "mouth", List.of(Map.of("name", "small", "weight", 1000), Map.of("name", "wide", "weight", 1), Map.of("name", "thin", "weight", 1)),
                "eyes", List.of(Map.of("name", "blue", "weight", 10), Map.of("name", "green", "weight", 2), Map.of("name", "brown", "weight", 1))
        ));
        printResult(solution.generateWeightedNFT(config, 5));
    }

    private static void test2() {
        System.out.println(" ========= Test 2  =========");
        generateNFT_3 solution = new generateNFT_3();
        Map<String, Object> config = new HashMap<>();
        config.put("traits", Map.of(
                "color", List.of(Map.of("name", "red", "weight", 3), Map.of("name", "blue", "weight", 1)),
                "shape", List.of(Map.of("name", "circle", "weight", 5), Map.of("name", "square", "weight", 2))
        ));
        printResult(solution.generateWeightedNFT(config, 4));
    }

    private static void test3() {
        System.out.println(" ========= Test 3  =========");
        generateNFT_3 solution = new generateNFT_3();
        Map<String, Object> config = new HashMap<>();
        config.put("traits", Map.of(
                "pattern", List.of(Map.of("name", "striped", "weight", 1), Map.of("name", "spotted", "weight", 1))
        ));
        printResult(solution.generateWeightedNFT(config, 2));
    }

    private static void test4() {
        System.out.println(" ========= Test 4  =========");
        generateNFT_3 solution = new generateNFT_3();
        Map<String, Object> config = new HashMap<>();
        config.put("traits", Map.of(
                "flavor", List.of(Map.of("name", "sweet", "weight", 1), Map.of("name", "sour", "weight", 3))
        ));
        try {
            printResult(solution.generateWeightedNFT(config, 10));
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
