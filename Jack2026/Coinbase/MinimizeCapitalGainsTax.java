package Jack2026.Coinbase;

import java.util.*;

class Solution {
    /**
     * 表示一批买入的股票
     */
    class StockBatch {
        long amount;
        long price;

        public StockBatch(long amount, long price) {
            this.amount = amount;
            this.price = price;
        }
    }

    public double calculateMinimalTax(List<List<String>> transactions) {
        // 使用最大堆，按价格降序排列，优先消耗高成本股票以抵税
        PriorityQueue<StockBatch> inventory = new PriorityQueue<>((a, b) -> Long.compare(b.price, a.price));
        double totalTax = 0.0;

        for (List<String> tx : transactions) {
            String type = tx.get(1);
            long amount = Long.parseLong(tx.get(2));
            long price = Long.parseLong(tx.get(3));

            if (type.equals("buy")) {
                // 买入操作：存入库存
                inventory.offer(new StockBatch(amount, price));
            } else {
                // 卖出操作：从高价向低价消耗
                long remainingToSell = amount;
                double currentSaleProfit = 0.0;

                while (remainingToSell > 0 && !inventory.isEmpty()) {
                    StockBatch highestCostBatch = inventory.poll();

                    long sellFromThisBatch = Math.min(remainingToSell, highestCostBatch.amount);

                    // 计算该部分的利润，如果利润为负，按 0 计算（或直接累加负值，税法通常是按单笔正利润计税）
                    // 根据题目 Example 1：只要单笔卖出中包含盈利部分，就对盈利部分收税
                    double profit = (double)(price - highestCostBatch.price) * sellFromThisBatch;
                    if (profit > 0) {
                        currentSaleProfit += profit;
                    }

                    // 更新该批次剩余量和待卖出总量
                    highestCostBatch.amount -= sellFromThisBatch;
                    remainingToSell -= sellFromThisBatch;

                    // 如果该批次还没卖完，放回堆中
                    if (highestCostBatch.amount > 0) {
                        inventory.offer(highestCostBatch);
                    }
                }

                // 累加本次交易的税费 (10%)
                totalTax += currentSaleProfit * 0.1;
            }
        }

        return totalTax;
    }
}
