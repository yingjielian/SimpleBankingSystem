package Jack2026.Coinbase.DesignCryptoTradingSystem;

import java.util.*;

class CryptoTradingSystem_2 {

    private static final String LIVE = "live";
    private static final String PAUSED = "paused";
    private static final String COMPLETED = "completed";
    private static final String CANCELED = "canceled";

    private class Order {
        String id;
        String currency;
        int amount;
        int timestamp;
        String type;
        String userId;
        String state;

        Order(String id, String currency, int amount, int timestamp, String type, String userId) {
            this.id = id;
            this.currency = currency;
            this.amount = amount;
            this.timestamp = timestamp;
            this.type = type;
            this.userId = userId;
            this.state = LIVE;
        }
    }

    private Map<String, Order> orderMap;
    // 建立 userId 到该用户所有订单 ID 的映射，提高取消效率
    private Map<String, List<String>> userToOrders;

    public CryptoTradingSystem_2() {
        this.orderMap = new HashMap<>();
        this.userToOrders = new HashMap<>();
    }

    public String placeOrder(String id, String currency, int amount, int timestamp, String type, String userId) {
        if (orderMap.containsKey(id)) {
            return "";
        }
        Order newOrder = new Order(id, currency, amount, timestamp, type, userId);
        orderMap.put(id, newOrder);

        // 更新用户关联表
        userToOrders.computeIfAbsent(userId, k -> new ArrayList<>()).add(id);
        return id;
    }

    public String pauseOrder(String id) {
        Order order = orderMap.get(id);
        if (order != null && order.state.equals(LIVE)) {
            order.state = PAUSED;
            return id;
        }
        return "";
    }

    public String resumeOrder(String id) {
        Order order = orderMap.get(id);
        if (order != null && order.state.equals(PAUSED)) {
            order.state = LIVE;
            return id;
        }
        return "";
    }

    public String cancelOrder(String id) {
        Order order = orderMap.get(id);
        if (order != null && (order.state.equals(LIVE) || order.state.equals(PAUSED))) {
            order.state = CANCELED;
            return id;
        }
        return "";
    }

    public String completeOrder(String id) {
        Order order = orderMap.get(id);
        if (order != null && order.state.equals(LIVE)) {
            order.state = COMPLETED;
            return id;
        }
        return "";
    }

    public List<String> displayLiveOrders() {
        List<Order> liveOrders = new ArrayList<>();
        for (Order order : orderMap.values()) {
            if (order.state.equals(LIVE)) {
                liveOrders.add(order);
            }
        }
        // 按时间戳升序排序
        liveOrders.sort(Comparator.comparingInt(o -> o.timestamp));

        List<String> result = new ArrayList<>();
        for (Order order : liveOrders) {
            result.add(order.id);
        }
        return result;
    }

    public int cancelAllOrders(String userId) {
        List<String> userOrderIds = userToOrders.get(userId);
        if (userOrderIds == null) {
            return 0;
        }

        int count = 0;
        for (String orderId : userOrderIds) {
            Order order = orderMap.get(orderId);
            // 只有 live 或 paused 的订单可以被取消
            if (order != null && (order.state.equals(LIVE) || order.state.equals(PAUSED))) {
                order.state = CANCELED;
                count++;
            }
        }
        return count;
    }
}
