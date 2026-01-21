package Jack2026.Coinbase.FoodDeliverySystem;

import java.util.*;

class FoodDeliverySystem_1 {
    private int minPrice = Integer.MAX_VALUE;
    private double minDistance = Double.MAX_VALUE;

    public FoodDeliverySystem_1(List<String> userLocation, List<List<String>> restaurantData, List<List<String>> menuData) {
        // 1. 解析用户坐标
        double userX = Double.parseDouble(userLocation.get(0));
        double userY = Double.parseDouble(userLocation.get(1));

        // 2. 计算到所有餐厅的最短距离
        // 距离公式: sqrt((x2-x1)^2 + (y2-y1)^2)
        for (List<String> restaurant : restaurantData) {
            double resX = Double.parseDouble(restaurant.get(1));
            double resY = Double.parseDouble(restaurant.get(2));

            double distance = Math.sqrt(Math.pow(resX - userX, 2) + Math.pow(resY - userY, 2));
            if (distance < minDistance) {
                minDistance = distance;
            }
        }

        // 3. 寻找所有菜单项中的最低价格
        for (List<String> item : menuData) {
            int price = Integer.parseInt(item.get(2));
            if (price < minPrice) {
                minPrice = price;
            }
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
}
