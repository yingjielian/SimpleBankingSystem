package Jack2026.Coinbase.MiningBlock;

import java.util.*;

public class MiningBlock_2 {
    private static class Transaction {
        int id;
        int size;
        double fee;
        List<Integer> parentIds; // Part 2 新增：依赖的父交易 ID

        public Transaction(int id, int size, double fee, List<Integer> parentIds) {
            this.id = id;
            this.size = size;
            this.fee = fee;
            this.parentIds = parentIds != null ? parentIds : new ArrayList<>();
        }
    }

    // 方便查找，用 Map 存储 ID -> Transaction 的映射
    Map<Integer, Transaction> txMap;
    // 记录已经打包进区块的交易 ID
    Set<Integer> addedInBlock;

    public List<Integer> getMaxFeeBlockWithDependencies(List<Transaction> transactions, int maxBlockSize) {
        List<Integer> result = new ArrayList<>();
        txMap = new HashMap<>();
        addedInBlock = new HashSet<>();

        // 初始化 Map
        for (Transaction tx : transactions) {
            txMap.put(tx.id, tx);
        }

        int currentSize = 0;

        // 这里的逻辑稍微复杂：因为每次打包一组交易后，
        // 剩余交易的“有效组合费率”可能会变（因为父节点被免除了），
        // 所以我们在 while 循环里每轮都重新寻找当前最优的 Candidate Set。
        while (currentSize < maxBlockSize) {
            List<Integer> bestCluster = null;
            double bestClusterRate = -1.0;
            int bestClusterSize = 0;

            // 遍历所有尚未打包的交易，尝试把它们作为“子节点”来拉取整个家族
            for (Transaction tx : transactions) {
                if (addedInBlock.contains(tx.id)) {
                    continue;
                }

                // --- DFS 核心部分 ---
                // 获取该交易及其所有必须绑定的未打包祖先
                Set<Integer> clusterSet = new HashSet<>();
                getUnvisitedAncestors(tx, clusterSet);

                // 计算这个组合的各种参数
                double clusterFee = 0;
                int clusterSize = 0;
                boolean fits = true;

                for (int id : clusterSet) {
                    Transaction t = txMap.get(id);
                    clusterFee += t.fee;
                    clusterSize += t.size;
                }

                // 如果组合太大放不进剩余空间，跳过
                if (currentSize + clusterSize > maxBlockSize) {
                    continue;
                }

                // 计算组合费率
                double currentClusterRate = clusterFee / clusterSize;

                // 更新全局最优选择
                if (currentClusterRate > bestClusterRate) {
                    bestClusterRate = currentClusterRate;
                    bestCluster = new ArrayList<>(clusterSet);
                    bestClusterSize = clusterSize;
                }
            }

            // 如果遍历完一圈，没有找到任何可以放入的组合，结束循环
            if (bestCluster == null) {
                break;
            }

            // 将最优组合加入区块
            result.addAll(bestCluster);
            addedInBlock.addAll(bestCluster);
            currentSize += bestClusterSize;
        }

        return result;
    }

    /**
     * Helper: DFS 递归查找所有未被打包的祖先
     * 这里直接把结果放入 Set 中，自动去重
     */
    private void getUnvisitedAncestors(Transaction currentTx, Set<Integer> cluster) {
        // 如果已经包含在这个临时的 cluster 里，或者是已经在全局 block 里，就停止
        if (cluster.contains(currentTx.id) || addedInBlock.contains(currentTx.id)) {
            return;
        }

        // 把自己加入组合
        cluster.add(currentTx.id);

        // 递归检查所有父节点
        for (int parentId : currentTx.parentIds) {
            if (txMap.containsKey(parentId)) {
                getUnvisitedAncestors(txMap.get(parentId), cluster);
            }
        }
    }

    public static void main(String[] args) {
        MiningBlock_2 solver = new MiningBlock_2();

        // --- Test Case: Child Pays For Parent (CPFP) ---
        List<Transaction> mempool = new ArrayList<>();

        // Tx 1 (Parent): 很重，没油水。
        // Size: 100, Fee: 10 -> Rate: 0.1 (垃圾)
        mempool.add(new Transaction(1, 100, 10.0, null));

        // Tx 2 (Child): 依赖 Tx 1。极小，油水极大。
        // Size: 10, Fee: 200 -> Rate: 20.0 (极品)
        // 组合 (1+2): Size 110, Fee 210 -> Rate ~1.9
        mempool.add(new Transaction(2, 10, 200.0, Arrays.asList(1)));

        // Tx 3 (Independent): 独立的普通交易
        // Size: 50, Fee: 60 -> Rate: 1.2
        mempool.add(new Transaction(3, 50, 60.0, null));

        // 设定 Block Size Limit = 120
        // 策略分析：
        // 1. 单看 Tx 1: Rate 0.1 (输给 Tx 3)
        // 2. 单看 Tx 3: Rate 1.2
        // 3. 看组合 (Tx 1 + Tx 2): Rate 1.9 (赢了 Tx 3!)
        //
        // 正确结果应该选: [1, 2]。总 Size 110 <= 120。
        // 如果选了 3，剩下空间 70，放不下 1 (也就放不下 2)，总 Fee 只有 60。
        // 如果选 1+2，总 Fee 是 210。

        int maxBlockSize = 120;

        System.out.println("--- Part 2 Test: Dependency Handling ---");
        List<Integer> block = solver.getMaxFeeBlockWithDependencies(mempool, maxBlockSize);

        System.out.println("Max Block Size: " + maxBlockSize);
        System.out.println("Selected Tx IDs: " + block);

        // 验证逻辑
        boolean hasParent = block.contains(1);
        boolean hasChild = block.contains(2);
        boolean hasIndependent = block.contains(3);

        if (hasParent && hasChild && !hasIndependent) {
            System.out.println("✅ Passed: Child correctly 'paid for' the parent.");
        } else if (hasIndependent) {
            System.out.println("❌ Failed: Algorithm picked the independent tx, ignoring the high-value cluster.");
        } else if (hasChild && !hasParent) {
            System.out.println("❌ Failed: Invalid state! Child selected without parent.");
        }
    }
}