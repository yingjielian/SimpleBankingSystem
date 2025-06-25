package Jack2025.Rippling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryCostTracker {
    private static class Delivery{
        int startTime;
        int endTime;

        Delivery(int startTime, int endTime)
        {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        int getDuration()
        {
            return endTime - startTime;
        }
    }

    private Map<String, List<Delivery>> driverDeliveries;
    private static final double COST_PER_MINUTE = 1.0;

    public DeliveryCostSystem(){
        driverDeliveries = new HashMap<>();
    }

    public void add_driver(String driverId)
    {
        driverDeliveries.putIfAbsent(driverId, new ArrayList<>());
    }

    public void add_delivery(String driverId, int startTime, int endTime)
    {
        if(!driverDeliveries.containsKey(driverId))
        {
            throw new IllegalArgumentException("Driver not found: " + driverId);
        }

        if (startTime >= endTime)
        {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        driverDeliveries.get(driverId).add(new Delivery(startTime, endTime));
    }

    public double get_total_cost() {
        double totalCost = 0.0;
        for(List<Delivery> deliveries : driverDeliveries.values())
        {
            for (Delivery delivery : deliveries)
            {
                totalCost += delivery.getDuration() * COST_PER_MINUTE;
            }
        }

        return totalCost;
    }

    // Main method for demonstration
    public static void main(String[] args) {
        DeliveryCostTracker tracker = new DeliveryCostTracker();
        tracker.add_driver("driver1");
        tracker.add_driver("driver2");
        tracker.add_delivery("driver1", 0, 30);    // 30 minutes
        tracker.add_delivery("driver1", 40, 70);   // 30 minutes
        tracker.add_delivery("driver2", 10, 20);   // 10 minutes
        System.out.println("Total cost: " + tracker.get_total_cost()); // Should print 70.0
}
