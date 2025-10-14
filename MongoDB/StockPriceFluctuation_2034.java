package Jack2025.MongoDB;
import java.util.*;
public class StockPriceFluctuation_2034 {
    private final Map<Integer, Integer> map;
    private Map<Integer, Integer> occurency;
    private TreeSet<Integer> treeSet;
    private int currentStockPrice;
    private int currentTimeStamp;
    public StockPriceFluctuation_2034() {
        map = new HashMap<>();
        occurency = new HashMap<>();
        treeSet = new TreeSet<>();
        currentStockPrice = 0;
        currentTimeStamp = 0;

    }

    public void update(int timestamp, int price) {
        if(timestamp >= currentTimeStamp)
        {
            currentTimeStamp = timestamp;
            currentStockPrice = price;
        }

        if(map.containsKey(timestamp))
        {
            int oldPrice = map.get(timestamp);
            if(occurency.get(oldPrice) == 1)
            {
                occurency.remove(oldPrice);
                treeSet.remove(oldPrice);
            }
            else
            {
                occurency.put(oldPrice, occurency.get(oldPrice) - 1);
            }
        }
        treeSet.add(price);
        map.put(timestamp, price);
        occurency.put(price, occurency.getOrDefault(price, 0) + 1);
    }

    public int current() {
        return currentStockPrice;
    }

    public int maximum() {
        return treeSet.last();
    }

    public int minimum() {
        return treeSet.first();
    }
}
