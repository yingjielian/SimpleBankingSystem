package Jack2026.Coinbase.MiningBlock;

import java.util.*;

// 定义交易类，面试时最好单独定义或者作为静态内部类


public class MiningBlock_1 {
    private static class Transaction {
        int id;
        int size;
        double fee;

        public Transaction(int id, int size, double fee) {
            this.id = id;
            this.size = size;
            this.fee = fee;
        }
    }

    /**
     * Part 1: 选择交易使得总 Fee 最大，但不超过 limit。
     * 策略：贪心算法。优先选择 Fee Rate (Fee / Size) 最高的交易。
     */
    public List<Integer> getMaxFeeBlock(List<Transaction> transactions, int maxBlockSize) {
        List<Integer> result = new ArrayList<>();

        // 1. 边界检查
        if (transactions == null || transactions.isEmpty() || maxBlockSize <= 0) {
            return result;
        }

        // 2. 排序
        // 按照 Fee Rate (Fee/Size) 从大到小排序
        // 注意：使用 Double.compare 避免精度问题
        Collections.sort(transactions, (a, b) -> {
            double rateA = a.fee / (double) a.size;
            double rateB = b.fee / (double) b.size;
            return Double.compare(rateB, rateA); // Descending
        });

        // 3. 贪心选择
        int currentSize = 0;
        for (Transaction tx : transactions) {
            if (currentSize + tx.size <= maxBlockSize) {
                result.add(tx.id);
                currentSize += tx.size;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        MiningBlock_1 solver = new MiningBlock_1();

        // --- Test Case 1: 验证费率优先 ---
        // 场景：
        // Tx1: 大且费高，但费率一般 (100 fee / 100 size = 1.0)
        // Tx2: 小且费低，但费率极高 (20 fee / 1 size = 20.0) -> 应该最先被选
        // Tx3: 中等 (40 fee / 20 size = 2.0) -> 应该第二个被选
        List<Transaction> mempool = new ArrayList<>();
        mempool.add(new Transaction(1, 100, 100.0));
        mempool.add(new Transaction(2, 1, 20.0));
        mempool.add(new Transaction(3, 20, 40.0));

        int maxBlockSize = 50;
        // 预期：Tx1 太大放不下，且费率低。Tx2 和 Tx3 应该被选中。

        System.out.println("--- Part 1 Test: Greedy Strategy ---");
        List<Integer> block = solver.getMaxFeeBlock(mempool, maxBlockSize);

        System.out.println("Max Block Size: " + maxBlockSize);
        System.out.println("Selected Tx IDs: " + block);

        // 验证逻辑
        if (block.contains(2) && block.contains(3) && !block.contains(1)) {
            System.out.println("✅ Passed: High fee-rate items selected.");
        } else {
            System.out.println("❌ Failed: Logic incorrect.");
        }
    }
}