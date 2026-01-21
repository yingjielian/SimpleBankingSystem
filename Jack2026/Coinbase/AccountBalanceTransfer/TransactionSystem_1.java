package Jack2026.Coinbase.AccountBalanceTransfer;

import java.util.*;

class TransactionSystem_1 {
    // 使用 TreeMap 存储账户名及其余额，以便自动按字母顺序排序
    private TreeMap<String, Double> accountMap;

    public TransactionSystem_1(List<List<String>> transactions, List<List<String>> accounts) {
        accountMap = new TreeMap<>();

        // 1. 初始化账户余额
        for (List<String> accountInfo : accounts) {
            String name = accountInfo.get(0);
            double balance = Double.parseDouble(accountInfo.get(1));
            accountMap.put(name, balance);
        }

        // 2. 按顺序处理每一笔交易
        for (List<String> trans : transactions) {
            String fromAccount = trans.get(0);
            String toAccount = trans.get(1);
            String percentStr = trans.get(2);

            // 解析百分比（去掉 '%' 符号并转换为小数）
            double percentage = Double.parseDouble(percentStr.replace("%", "")) / 100.0;

            if (accountMap.containsKey(fromAccount) && accountMap.containsKey(toAccount)) {
                double currentFromBalance = accountMap.get(fromAccount);
                double transferAmount = currentFromBalance * percentage;

                // 更新转出账户余额
                accountMap.put(fromAccount, currentFromBalance - transferAmount);
                // 更新转入账户余额
                accountMap.put(toAccount, accountMap.get(toAccount) + transferAmount);
            }
        }
    }

    public List<Double> getBalances() {
        // 由于使用了 TreeMap，values() 返回的顺序已经是按键（账户名）字母排序的
        return new ArrayList<>(accountMap.values());
    }
}
