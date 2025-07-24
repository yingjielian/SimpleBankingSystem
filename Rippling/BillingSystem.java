package Jack2025.Rippling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BillingSystem {
    class Driver {
        private int id;
        private float hourlyRate;
        public Driver(int id, float hourlyRate) {
            this.id = id;
            this.hourlyRate = hourlyRate;
        }
        public int getId() {
            return id;
        }
        public float getHourlyRate() {
            return hourlyRate;
        }
    }
    class Delivery {
        private int driverId;
        private int startTime;
        private int endTime;
        private boolean paid; // New field
        public Delivery(int driverId, int startTime, int endTime) {
            this.driverId = driverId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.paid = false;
        }
        public int getDriverId() { return driverId; }
        public int getStartTime() { return startTime; }
        public int getEndTime() { return endTime; }
        public boolean isPaid() { return paid; }
        public void markPaid() { this.paid = true; }
        public int getDurationInSeconds() { return endTime - startTime; }
    }
    private Map<Integer, Driver> drivers;
    private List<Delivery> deliveries;
    private float totalPaid; // Track total paid so far

    public BillingSystem() {
        drivers = new HashMap<>();
        deliveries = new ArrayList<>();
        totalPaid = 0.0f;
    }

    public void add_driver(int driver_id, float cost) {
        if (drivers.containsKey(driver_id)) {
            throw new IllegalArgumentException("Driver already exists: " + driver_id);
        }
        drivers.put(driver_id, new Driver(driver_id, cost));
    }

    public void add_delivery(int driver_id, int start_time, int end_time) {
        if (!drivers.containsKey(driver_id)) {
            throw new IllegalArgumentException("Driver does not exist: " + driver_id);
        }
        if (end_time <= start_time) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        deliveries.add(new Delivery(driver_id, start_time, end_time));
    }

    public float get_total_cost() {
        float totalCost = 0.0f;
        for (Delivery delivery : deliveries) {
            Driver driver = drivers.get(delivery.getDriverId());
            float hourlyRate = driver.getHourlyRate();
            int durationSeconds = delivery.getDurationInSeconds();
            float durationHours = durationSeconds / 3600.0f;
            totalCost += durationHours * hourlyRate;
        }
        return totalCost;
    }

    // New: Pays for all unpaid deliveries ended on or before 'time'
    public float pay_up_to(int time) {
        float paidThisCall = 0.0f;
        for (Delivery delivery : deliveries) {
            if (!delivery.isPaid() && delivery.getEndTime() <= time) {
                Driver driver = drivers.get(delivery.getDriverId());
                float hourlyRate = driver.getHourlyRate();
                int durationSeconds = delivery.getDurationInSeconds();
                float durationHours = durationSeconds / 3600.0f;
                float deliveryCost = durationHours * hourlyRate;
                paidThisCall += deliveryCost;
                delivery.markPaid();
            }
        }
        totalPaid += paidThisCall;
        return paidThisCall; // Optional: return how much was paid in this call
    }

    // New: Returns the unpaid amount
    public float get_unpaid_amount() {
        return get_total_cost() - totalPaid;
    }
}

