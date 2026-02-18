package Jack2023;

import java.util.*;
class CryptoTradingSystem{

    private static final String LIVE = "live";
    private static final String PAUSED = "paused";
    private static final String COMPLETED = "completed";
    private static final String CANCELED = "canceled";


    private class Order
    {
        String id;
        String currency;
        int amount;
        int timestamp;
        String type;
        String state;

        Order(String id, String currency, int amount, int timestamp, String type)
        {
            this.id = id;
            this.currency = currency;
            this.amount = amount;
            this.timestamp = timestamp;
            this.type = type;
            this.state = LIVE;
        }
    }

    // <id, order>
    private final Map<String, Order> orderMap = new HashMap<>();

    public CryptoTradingSystem()
    {
    }

    // String placeOrder(String id, String currency, int amount, int timestamp, String type): Place a new order with live status. Returns the order ID if successful, otherwise an empty string.

    public String placeOrder(String id, String currency, int amount, int timestamp, String type)
    {
        if(orderMap.containsKey(id))
        {
            return "";
        }
        Order newOrder = new Order(id, currency, amount, timestamp, type);
        orderMap.put(id, newOrder);

        return id;
    }

    // String pauseOrder(String id): Pause a live order. Returns the order ID if successful, otherwise an empty string.

    public String pauseOrder(String id)
    {
        Order order = orderMap.get(id);
        if(order != null && order.state.equals(LIVE))
        {
            order.state = PAUSED;
            return id;
        }
        return "";
    }

    // String resumeOrder(String id): Resume a paused order. Returns the order ID if successful, otherwise an empty string.
    public String resumeOrder(String id)
    {
        Order order = orderMap.get(id);
        if(order != null && order.state.equals(PAUSED))
        {
            order.state = LIVE;
            return id;
        }
        return "";
    }

    // String cancelOrder(String id): Cancel an existing order that is either live or paused. Returns the order ID if successful, otherwise an empty string.
    public String cancelOrder(String id)
    {
        Order order = orderMap.get(id);
        if(order != null)
        {
            if(order.state.equals(LIVE) || order.state.equals(PAUSED))
            {
                order.state = CANCELED;
                return id;
            }
        }
        return "";
    }

    // String completeOrder(String id): Complete a live order. Returns the order ID if successful, otherwise an empty string.
    public String completeOrder(String id)
    {
        Order order = orderMap.get(id);
        if(order != null && order.state.equals(LIVE))
        {
            order.state = COMPLETED;
            return id;
        }
        return "";
    }

    // List<String> displayLiveOrders(): Display all live orders, sorted by ascending timestamp.
    public List<String> displayLiveOrders()
    {
        List<Order> liveOrders = new ArrayList<>();

        for(Order order : orderMap.values())
        {
            if(order.state.equals(LIVE))
            {
                liveOrders.add(order);
            }
        }

        liveOrders.sort(Comparator.comparingInt(o -> o.timestamp));
        List<String> result = new ArrayList<>();
        for(Order order : liveOrders)
        {
            result.add(order.id);
        }

        return result;
    }
}

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        CryptoTradingSystem cp = new CryptoTradingSystem();

        cp.placeOrder("1","USD", 500, 20, "sell");
        cp.placeOrder("1","USD", 300, 50, "sell");
        cp.placeOrder("2","CNY", 100, 10, "buy");
        cp.placeOrder("3","JPY", 1000, 30, "sell");
        cp.placeOrder("4","USD", 200, 21, "sell");
        cp.pauseOrder("1");
        System.out.println(cp.displayLiveOrders());
    }
}