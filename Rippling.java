import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rippling {

    class Delivey
    {
        int driverId;
        int startTime;
        int endTime;
        boolean isPaid;

        // Constructor for Delivery
        public Delivey(int driverId, int startTime, int endTime)
        {
            this.driverId = driverId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.isPaid = false;
        }
    }

//       - The given driver will not already be in the system
//    // id must be uniq, map, absentornew()
//   - The hourly rate applies per delivery, and a driver can be paid for simultaneous deliveries
    static Map<Integer, Double> drivers = new HashMap<>();
    static ArrayList<Delivey> deliveys = new ArrayList<>();
    static double totalCost = 0.0;
    static double costPerDriver = 0.0;
    static double payUpToCost = 0.0;
    static double totalUnpaid = 0.0;
    public static void addDriver(int driverid, double usdHourlyRate)
    {
        // since it's valid, don't need to check null; try catch expection
        drivers.putIfAbsent(driverid, usdHourlyRate);
    }

    public void recordDelivery(int driverid, int startTime, int endTime)
    {
        Delivey delivey = new Delivey(driverid, startTime, endTime);
        int timeDiff = 0;


        timeDiff = (delivey.endTime - delivey.startTime) / 3600;

        deliveys.add(delivey);
        costPerDriver = timeDiff * drivers.get(delivey.driverId);
        totalCost += costPerDriver;
    }

    // get the Delivery, calculate the time diffence, and times the driver hourly rate.
    // N drivers, and M deliveries,
    // O(N*M)
    static public double getTotalCost()
    {
        return totalCost;
    }

    public static double payUpTo (int payTime)
    {
        for(Delivey delivey : deliveys)
        {

            delivey.isPaid = true;
        }

        return totalUnpaid;
    }

    public static double getTotalCostUnpaid()
    {
        return totalUnpaid;
    }

    public static void main(String[] args)
    {
        Rippling rp = new Rippling();
        addDriver(1, 10);
        addDriver(2, 15);
        // 1 * 10 = 10
        rp.recordDelivery(1, 0, 3600);
        // 15
        rp.recordDelivery(2, 0, 3600);

        // 10+15 = 25
        System.out.println(totalCost);
    }




}
