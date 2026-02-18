package Jack2026.Coinbase.OrderSystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderSystem_1 {


    private class Order
    {
        String symbol;
        int totalQuantity;
        int filledQuantity;
        boolean isCancelled; // follow up
//        private final Set<String> processedEventIds = new HashSet<>();

        Order(String symbol, int totalQuantity)
        {
            this.symbol = symbol;
            this.totalQuantity = totalQuantity;
            this.filledQuantity = 0;
            this.isCancelled = false;
        }

        public synchronized String getStatus(){
            if(isCancelled) return "CANCELLED";
            else if(filledQuantity == 0)
            {
                return "NEW";
            } else if (filledQuantity < totalQuantity) {
                return "IN_PROGRESS";
            }else {
                return "COMPLETED";
            }
        }

        // String eventId,
        public synchronized void addFilledQuantity(int qty)
        {
            if(isCancelled) return;

//            if(processedEventIds.contains(eventId))
//            {
//                return;
//            }

            this.filledQuantity += qty;
//            processedEventIds.add(eventId);
        }
        public synchronized void cancel(){
            if(this.filledQuantity == 0)
            {
                this.isCancelled = true;
            }
        }
    }


    private final Map<String, Order> database = new ConcurrentHashMap<>();

    // Implement the API **consumeMessages** which is responsible for reading the message
    // from Kafka and storing it in the database.
    public void consumeMessages(String orderId, String operation, String symbol, int quantity, String orderType)
    {
        if("NEW".equalsIgnoreCase(orderType))
        {
            database.putIfAbsent(orderId, new Order(symbol, quantity));
        } else if ("FILLED".equalsIgnoreCase(orderType)) {
            Order order = database.get(orderId);
            if(order != null)
            {
                order.addFilledQuantity(quantity);
            }
        } else if ("CANCEL".equalsIgnoreCase(orderType)) {
            Order order = database.get(orderId);
            if(order != null)
            {
                order.cancel();
            }
        }
    }


    // Implement the API **getOrderStatus** which is responsible for getting the order details
    // for a given orderId.
    public String getOrderStatus(String orderId)
    {
        Order order = database.get(orderId);
        if(order == null)
        {
            return "NOT_FOUND";
        }
        return order.getStatus();
    }
}
