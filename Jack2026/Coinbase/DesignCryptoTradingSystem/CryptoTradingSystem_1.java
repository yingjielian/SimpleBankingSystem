package Jack2026.Coinbase.DesignCryptoTradingSystem;

import java.util.*;

class CryptoTradingSystem_1 {

    // 订单状态常量
    private static final String LIVE = "live";
    private static final String PAUSED = "paused";
    private static final String COMPLETED = "completed";
    private static final String CANCELED = "canceled";

    // 内部类用于存储订单信息
    private class Order {
        String id;
        String currency;
        int amount;
        int timestamp;
        String type;
        String state;

        Order(String id, String currency, int amount, int timestamp, String type) {
            this.id = id;
            this.currency = currency;
            this.amount = amount;
            this.timestamp = timestamp;
            this.type = type;
            this.state = LIVE; // 初始状态为 live
        }
    }

    // 使用 HashMap 存储所有订单，以便快速访问
    private Map<String, Order> orderMap;

    public CryptoTradingSystem_1() {
        orderMap = new HashMap<>();
    }

    public String placeOrder(String id, String currency, int amount, int timestamp, String type) {
        if (orderMap.containsKey(id)) {
            return ""; // ID 必须唯一
        }
        Order newOrder = new Order(id, currency, amount, timestamp, type);
        orderMap.put(id, newOrder);
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

        // 筛选出所有状态为 live 的订单
        for (Order order : orderMap.values()) {
            if (order.state.equals(LIVE)) {
                liveOrders.add(order);
            }
        }

        // 按时间戳升序排序
        liveOrders.sort(Comparator.comparingInt(o -> o.timestamp));

        // 转换为 ID 列表返回
        List<String> result = new ArrayList<>();
        for (Order order : liveOrders) {
            result.add(order.id);
        }
        return result;
    }
}
