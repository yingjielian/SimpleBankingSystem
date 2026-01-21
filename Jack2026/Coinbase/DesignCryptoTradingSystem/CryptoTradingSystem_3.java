package Jack2026.Coinbase.DesignCryptoTradingSystem;

import java.util.*;

class CryptoTradingSystem_3 {

    // 内部类：代表一个独立的数据流（分片）
    private class OrderStream {
        Map<String, Order> orderMap = new HashMap<>();
        Map<String, List<String>> userToOrders = new HashMap<>();

        String placeOrder(Order order) {
            orderMap.put(order.id, order);
            userToOrders.computeIfAbsent(order.userId, k -> new ArrayList<>()).add(order.id);
            return order.id;
        }

        int cancelAllOrders(String userId) {
            List<String> userOrderIds = userToOrders.get(userId);
            if (userOrderIds == null) return 0;
            int count = 0;
            for (String orderId : userOrderIds) {
                Order o = orderMap.get(orderId);
                if (o != null && (o.state.equals("live") || o.state.equals("paused"))) {
                    o.state = "canceled";
                    count++;
                }
            }
            return count;
        }
    }

    private class Order {
        String id, currency, type, userId, state;
        int amount, timestamp;

        Order(String id, String currency, int amount, int timestamp, String type, String userId) {
            this.id = id; this.currency = currency; this.amount = amount;
            this.timestamp = timestamp; this.type = type; this.userId = userId;
            this.state = "live";
        }
    }

    private final OrderStream[] streams;
    private final int n;
    // 全局索引：快速通过 ID 找到对应的流索引
    private final Map<String, Integer> idToStreamMap;

    public CryptoTradingSystem_3(int n) {
        this.n = n;
        this.streams = new OrderStream[n];
        for (int i = 0; i < n; i++) {
            streams[i] = new OrderStream();
        }
        this.idToStreamMap = new HashMap<>();
    }

    private int getStreamIndex(String userId) {
        // 使用 Math.abs 防止 hashCode 为负数
        return Math.abs(userId.hashCode()) % n;
    }

    public String placeOrder(String id, String currency, int amount, int timestamp, String type, String userId) {
        if (idToStreamMap.containsKey(id)) return "";

        int idx = getStreamIndex(userId);
        Order order = new Order(id, currency, amount, timestamp, type, userId);
        streams[idx].placeOrder(order);
        idToStreamMap.put(id, idx);
        return id;
    }

    public String pauseOrder(String id) {
        Order o = getOrderById(id);
        if (o != null && o.state.equals("live")) {
            o.state = "paused";
            return id;
        }
        return "";
    }

    public String resumeOrder(String id) {
        Order o = getOrderById(id);
        if (o != null && o.state.equals("paused")) {
            o.state = "live";
            return id;
        }
        return "";
    }

    public String cancelOrder(String id) {
        Order o = getOrderById(id);
        if (o != null && (o.state.equals("live") || o.state.equals("paused"))) {
            o.state = "canceled";
            return id;
        }
        return "";
    }

    public String completeOrder(String id) {
        Order o = getOrderById(id);
        if (o != null && o.state.equals("live")) {
            o.state = "completed";
            return id;
        }
        return "";
    }

    public List<String> displayLiveOrders() {
        List<Order> allLive = new ArrayList<>();
        // 汇总所有分片中的 live 订单
        for (OrderStream s : streams) {
            for (Order o : s.orderMap.values()) {
                if (o.state.equals("live")) allLive.add(o);
            }
        }
        // 全局排序
        allLive.sort(Comparator.comparingInt(o -> o.timestamp));
        List<String> res = new ArrayList<>();
        for (Order o : allLive) res.add(o.id);
        return res;
    }

    public int cancelAllOrders(String userId) {
        int idx = getStreamIndex(userId);
        return streams[idx].cancelAllOrders(userId);
    }

    // 辅助方法：通过 ID 定位到具体分片并获取订单
    private Order getOrderById(String id) {
        Integer idx = idToStreamMap.get(id);
        if (idx == null) return null;
        return streams[idx].orderMap.get(id);
    }
}
