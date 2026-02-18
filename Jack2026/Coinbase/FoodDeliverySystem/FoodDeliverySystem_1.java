package Jack2026.Coinbase.FoodDeliverySystem;

import java.util.*;

class FoodDeliverySystem_1{

    private int minPrice = Integer.MAX_VALUE;
    private double minDistance = Double.MAX_VALUE;


    public FoodDeliverySystem_1(List<String> userLocation, List<List<String>> restaurantData,
                               List<List<String>> menuData)
    {
        double userX = Double.parseDouble(userLocation.get(0));
        double userY = Double.parseDouble(userLocation.get(1));

        for(List<String> res : restaurantData)
        {
            double resX = Double.parseDouble(res.get(1));
            double resY = Double.parseDouble(res.get(2));

            double distance = calculateDistance(userX, userY,resX, resY);
            minDistance = Math.min(distance, minDistance);
        }

        for(List<String> menu : menuData)
        {
            int price = Integer.parseInt(menu.get(2));
            minPrice = Math.min(price, minPrice);
        }
    }

    /**
     * 返回所有菜品中的最低价格
     */
    public int getLowestPrice() {
        return minPrice == Integer.MAX_VALUE ? 0 : minPrice;
    }

    /**
     * 返回最短送餐时间（即最短欧几里得距离）
     */
    public double getLowestDeliveryTime() {
        return minDistance == Double.MAX_VALUE ? 0.0 : minDistance;
    }

    public double calculateDistance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void main(String[] args) {
        // User Location: ["x", "y"]
        List<String> userLocation = Arrays.asList("2.0", "3.0");

        // Restaurant Data: ["restaurantID", "x", "y"]
        List<List<String>> restaurantData = new ArrayList<>();
        restaurantData.add(Arrays.asList("1", "5.0", "7.0"));  // Distance = 5.0
        restaurantData.add(Arrays.asList("2", "1.5", "9.5"));  // Distance ≈ 6.52
        restaurantData.add(Arrays.asList("3", "25.0", "8.0")); // Distance ≈ 23.54

        // Menu Data: ["restaurantID", "itemName", "price"]
        List<List<String>> menuData = new ArrayList<>();
        menuData.add(Arrays.asList("1", "burger", "8"));
        menuData.add(Arrays.asList("1", "pizza", "10"));
        menuData.add(Arrays.asList("2", "sushi", "15"));
        menuData.add(Arrays.asList("3", "noodles", "9"));

        // Initialize System
        FoodDeliverySystem_1 system = new FoodDeliverySystem_1(userLocation, restaurantData, menuData);

        // Test cases
        System.out.println("--- Part 1 Testing ---");
        System.out.println("Lowest Price (Expected: 8): " + system.getLowestPrice());
        System.out.printf("Lowest Delivery Time (Expected: 5.0): %.1f\n", system.getLowestDeliveryTime());
    }
}




