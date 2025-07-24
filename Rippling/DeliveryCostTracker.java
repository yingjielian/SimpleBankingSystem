package Jack2025.Rippling;

import java.util.*;

public class DeliveryCostTracker {
    private static class Delivery {
        int startTime;
        int endTime;
        boolean paid;

        Delivery(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.paid = false;
        }

        int getDuration() {
            return endTime - startTime;
        }
    }

    private Map<String, List<Delivery>> driverDeliveries;
    private static final double COST_PER_MINUTE = 1.0;

    private double totalCost = 0.0;
    private double unpaidCost = 0.0;

    public DeliveryCostTracker() {
        driverDeliveries = new HashMap<>();
    }

    public void add_driver(String driverId) {
        driverDeliveries.putIfAbsent(driverId, new ArrayList<>());
    }

    public void add_delivery(String driverId, int startTime, int endTime) {
        if (!driverDeliveries.containsKey(driverId)) {
            throw new IllegalArgumentException("Driver not found: " + driverId);
        }

        if (startTime >= endTime) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        Delivery delivery = new Delivery(startTime, endTime);
        driverDeliveries.get(driverId).add(delivery);

        double cost = delivery.getDuration() * COST_PER_MINUTE;

        totalCost += cost;
        unpaidCost += cost;

    }
// O(N)
//    public double get_total_cost() {
//        double totalCost = 0.0;
//        for (List<Delivery> deliveries : driverDeliveries.values()) {
//            for (Delivery delivery : deliveries) {
//                totalCost += delivery.getDuration() * COST_PER_MINUTE;
//            }
//        }
//
//        return totalCost;
//    }

    // O(1)
    public double get_total_cost()
    {
        return totalCost;
    }

    public void pay_up_to_time(int upToTime)
    {
        for (List<Delivery> deliveries : driverDeliveries.values())
        {
            for(Delivery delivery : deliveries)
            {
                if(!delivery.paid && delivery.endTime <= upToTime)
                {
                    double cost = delivery.getDuration() * COST_PER_MINUTE;
                    unpaidCost -= cost;
                    delivery.paid = true;
                }
            }
        }
    }

    public double get_cost_to_be_paid()
    {
        return unpaidCost;
    }

    public int get_max_active_drivers_in_last_24_hours(int currentTime) {
        int windowStart = currentTime - 1440; // 24 hours = 1440 minutes
        // Event: (time, isStart, driverId)
        class Event implements Comparable<Event> {
            int time;
            boolean isStart;
            String driverId;
            Event(int time, boolean isStart, String driverId) {
                this.time = time;
                this.isStart = isStart;
                this.driverId = driverId;
            }
            public int compareTo(Event other) {
                if (this.time != other.time)
                    return Integer.compare(this.time, other.time);
                // End events before start events at the same time
                if (this.isStart != other.isStart)
                    return this.isStart ? 1 : -1;
                return 0;
            }
        }
        List<Event> events = new ArrayList<>();
        for (Map.Entry<String, List<Delivery>> entry : driverDeliveries.entrySet()) {
            String driverId = entry.getKey();
            for (Delivery delivery : entry.getValue()) {
                // Only consider deliveries overlapping the window
                int start = Math.max(delivery.startTime, windowStart);
                int end = Math.min(delivery.endTime, currentTime);
                if (start < end) { // There is overlap with the window
                    events.add(new Event(start, true, driverId));
                    events.add(new Event(end, false, driverId));
                }
            }
        }
        Collections.sort(events);
        Set<String> activeDrivers = new HashSet<>();
        int maxActive = 0;
        for (Event event : events) {
            if (event.isStart) {
                activeDrivers.add(event.driverId);
            } else {
                activeDrivers.remove(event.driverId);
            }
            maxActive = Math.max(maxActive, activeDrivers.size());
        }
        return maxActive;
    }

    // Main method for demonstration
    public static void main(String[] args) {
        DeliveryCostTracker tracker = new DeliveryCostTracker();
        tracker.add_driver("driver1");
        tracker.add_driver("driver2");
        tracker.add_driver("driver3");
        // Let's say currentTime = 2000
        tracker.add_delivery("driver1", 500, 1600);   // Active 500-1600
        tracker.add_delivery("driver2", 1000, 1800);  // Active 1000-1800
        tracker.add_delivery("driver3", 1500, 2100);  // Active 1500-2100
        int currentTime = 2000;
        System.out.println("Max active drivers in last 24 hours: " +
                tracker.get_max_active_drivers_in_last_24_hours(currentTime));
        // Should print 3 (all three overlap between 1500 and 1600)
    }
}
