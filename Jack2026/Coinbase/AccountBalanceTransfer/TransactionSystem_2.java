package Jack2026.Coinbase.AccountBalanceTransfer;

import java.util.*;

class TransactionSystem_2 {
    // 存储初始账户余额（用于重新计算）
    private final Map<String, Double> initialBalances;
    // 存储所有交易数据
    private final List<List<String>> allTransactions;
    // 记录每笔交易是否被回滚（true 表示已回滚）
    private final boolean[] isRolledBack;
    // 缓存当前的计算结果，避免重复计算
    private TreeMap<String, Double> currentBalances;
    private boolean needsRecompute;

    public TransactionSystem_2(List<List<String>> transactions, List<List<String>> accounts) {
        this.allTransactions = transactions;
        this.isRolledBack = new boolean[transactions.size()];
        this.initialBalances = new HashMap<>();

        // 初始化账户信息
        for (List<String> account : accounts) {
            initialBalances.put(account.get(0), Double.parseDouble(account.get(1)));
        }

        // 标记需要首次计算
        this.needsRecompute = true;
    }

    public void rollbackTransfer(int idx) {
        // 如果索引有效且尚未被回滚，则标记并触发重新计算
        if (idx >= 0 && idx < isRolledBack.length && !isRolledBack[idx]) {
            isRolledBack[idx] = true;
            needsRecompute = true;
        }
    }

    public List<Double> getBalances() {
        if (needsRecompute) {
            recompute();
        }
        return new ArrayList<>(currentBalances.values());
    }

    private void recompute() {
        // 1. 回到初始状态（使用 TreeMap 保证字母顺序）
        currentBalances = new TreeMap<>(initialBalances);

        // 2. 按顺序遍历所有交易
        for (int i = 0; i < allTransactions.size(); i++) {
            // 跳过已回滚的交易
            if (isRolledBack[i]) continue;

            List<String> trans = allTransactions.get(i);
            String from = trans.get(0);
            String to = trans.get(1);
            double percentage = Double.parseDouble(trans.get(2).replace("%", "")) / 100.0;

            // 执行交易逻辑
            double fromBalance = currentBalances.get(from);
            double amount = fromBalance * percentage;

            currentBalances.put(from, fromBalance - amount);
            currentBalances.put(to, currentBalances.get(to) + amount);
        }

        needsRecompute = false;
    }
}