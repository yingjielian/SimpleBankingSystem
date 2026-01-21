package Jack2026.Coinbase;

import java.util.*;

class CryptoMarket {
    // 大顶堆存储买单，为了获取最高买价
    private PriorityQueue<Integer> buyPQ;
    // 小顶堆存储卖单，为了获取最低卖价
    private PriorityQueue<Integer> sellPQ;

    public CryptoMarket(int[] buyOrders, int[] sellOrders) {
        // 初始化堆，买单降序，卖单升序
        buyPQ = new PriorityQueue<>(Collections.reverseOrder());
        sellPQ = new PriorityQueue<>();

        for (int price : buyOrders) {
            buyPQ.offer(price);
        }
        for (int price : sellOrders) {
            sellPQ.offer(price);
        }
    }

    public double addOrder(int price, String orderType) {
        if (orderType.equals("buy")) {
            // 如果是买单，寻找最低的卖单
            if (!sellPQ.isEmpty() && price >= sellPQ.peek()) {
                int lowestSell = sellPQ.poll();
                return (price + (double)lowestSell) / 2.0;
            } else {
                // 未达成交易，加入买单池
                buyPQ.offer(price);
                return -1.0;
            }
        } else {
            // 如果是卖单，寻找最高的买单
            if (!buyPQ.isEmpty() && price <= buyPQ.peek()) {
                int highestBuy = buyPQ.poll();
                return (price + (double)highestBuy) / 2.0;
            } else {
                // 未达成交易，加入卖单池
                sellPQ.offer(price);
                return -1.0;
            }
        }
    }
}
