package Jack2026.Coinbase.FoodDeliverySystem;

import java.util.*;

class FoodDeliverySystem_2 {
    // 存储菜品名到单价的映射
    private Map<String, Integer> itemPrices = new HashMap<>();

    // 存储订单信息：Key 为时间戳，Value 为订单详情列表
    // 使用 TreeMap 是为了让时间戳有序，方便范围查找
    private TreeMap<Integer, List<OrderInfo>> timeline = new TreeMap<>();

    // 内部类，用于存储订单详情
    private static class OrderInfo {
        double totalValue;
        OrderInfo(double totalValue) {
            this.totalValue = totalValue;
        }
    }

    public FoodDeliverySystem_2(List<List<String>> menuData, List<List<String>> orderData) {
        // 1. 构建价格表
        for (List<String> menu : menuData) {
            itemPrices.put(menu.get(1), Integer.parseInt(menu.get(2)));
        }

        // 2. 预处理订单数据
        for (List<String> order : orderData) {
            String itemName = order.get(1);
            int count = Integer.parseInt(order.get(2));
            int timestamp = Integer.parseInt(order.get(3));

            int price = itemPrices.getOrDefault(itemName, 0);
            double totalValue = price * count;

            timeline.putIfAbsent(timestamp, new ArrayList<>());
            timeline.get(timestamp).add(new OrderInfo(totalValue));
        }
    }

    /**
     * 获取指定时间范围内的订单总数
     */
    public int countOrdersInRange(int start, int end) {
        int count = 0;
        // 获取 [start, end] 范围内的所有时间戳子集
//        NavigableMap<Integer, List<OrderInfo>> subMap = timeline.subMap(start, true, end, true);
//        for (List<OrderInfo> orders : subMap.values()) {
//            count += orders.size();
//        }
        for(int timestamp : timeline.keySet())
        {
            if(timestamp >= start && timestamp <= end)
            {
                count += timeline.get(timestamp).size();
            }
        }
        return count;
    }

    /**
     * 获取指定时间范围内的总金额
     */
    public int totalAmountInRange(int start, int end) {
        double total = 0;
//        NavigableMap<Integer, List<OrderInfo>> subMap = timeline.subMap(start, true, end, true);
//        for (List<OrderInfo> orders : subMap.values()) {
//            for (OrderInfo order : orders) {
//                total += order.totalValue;
//            }
//        }
        for(int timestamp : timeline.keySet())
        {
            if(timestamp >= start && timestamp <= end)
            {
                for(OrderInfo orderInfo : timeline.get(timestamp))
                {
                    total += orderInfo.totalValue;
                }
            }
        }
        return (int) total;
    }

    /**
     * 获取指定时间范围内的平均订单价值
     */
    public double averageOrderValueInRange(int start, int end) {
        double totalSum = 0;
        int totalCount = 0;

//        NavigableMap<Integer, List<OrderInfo>> subMap = timeline.subMap(start, true, end, true);
//        for (List<OrderInfo> orders : subMap.values()) {
//            totalCount += orders.size();
//            for (OrderInfo order : orders) {
//                totalSum += order.totalValue;
//            }
//        }
        for(int timestamp : timeline.keySet())
        {
            if(timestamp >= start && timestamp <= end)
            {
                totalCount += timeline.get(timestamp).size();
                for(OrderInfo orderInfo : timeline.get(timestamp))
                {
                    totalSum += orderInfo.totalValue;
                }
            }
        }

        return totalCount == 0 ? 0.0 : totalSum / totalCount;
    }

    public static void main(String[] args) {
        // Menu Data: ["restaurantId", "itemName", "price"]
        List<List<String>> menuData = new ArrayList<>();
        menuData.add(Arrays.asList("1", "burger", "8"));
        menuData.add(Arrays.asList("1", "pizza", "10"));
        menuData.add(Arrays.asList("2", "sushi", "15"));
        menuData.add(Arrays.asList("3", "noodles", "9"));
        menuData.add(Arrays.asList("1", "drink", "3"));

        // Order Data: ["orderId", "itemName", "count", "timestamp"]
        List<List<String>> orderData = new ArrayList<>();
        orderData.add(Arrays.asList("1", "burger", "2", "100")); // 8*2 = 16
//        orderData.add(Arrays.asList("6", "rice", "2", "100")); // 8*2 = 16
        orderData.add(Arrays.asList("2", "pizza", "1", "150"));  // 10*1 = 10
        orderData.add(Arrays.asList("3", "sushi", "3", "200"));  // 15*3 = 45
        orderData.add(Arrays.asList("4", "noodles", "1", "250")); // 9*1 = 9
        orderData.add(Arrays.asList("5", "drink", "4", "300"));   // 3*4 = 12

        // Initialize System
        FoodDeliverySystem_2 system = new FoodDeliverySystem_2(menuData, orderData);

        System.out.println("--- Part 2 Testing ---");

        // Test 1: Count orders between 100 and 200 (100, 150, 200)
        System.out.println("Count [100, 200] (Expected: 3): " + system.countOrdersInRange(100, 200));

        // Test 2: Total amount between 100 and 280 (100, 150, 200, 250)
        // (16 + 10 + 45 + 9) = 80
        System.out.println("Total Amount [100, 280] (Expected: 80): " + system.totalAmountInRange(100, 280));

        // Test 3: Average value between 100 and 200
        // (16 + 10 + 45) / 3 = 23.666...
        System.out.printf("Avg Value [100, 200] (Expected: 23.66667): %.5f\n", system.averageOrderValueInRange(100, 200));

        // Test 4: Empty range
        System.out.println("Avg Value [500, 600] (Expected: 0.0): " + system.averageOrderValueInRange(500, 600));
    }
}
