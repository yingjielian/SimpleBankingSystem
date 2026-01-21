package Jack2026.Coinbase.BankingSystem;

import java.util.*;

class BankingSystem_2 {
    private Map<String, Integer> balances;
    private Map<String, Integer> outgoingTotals;

    public BankingSystem_2()
    {
        this.balances = new HashMap<>();
        this.outgoingTotals = new HashMap<>();
    }

    public boolean createAccount(int timestamp, String accountId)
    {
        if(balances.containsKey(accountId))
        {
            return false;
        }

        balances.put(accountId, 0);
        outgoingTotals.put(accountId, 0);
        return true;
    }

    public int deposit(int timestamp, String accountId, int amount)
    {
        if(!balances.containsKey(accountId))
        {
            return -1;
        }

        int newBalance = balances.get(accountId) + amount;
        balances.put(accountId, newBalance);
        return newBalance;
    }

    public int transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {

        if(sourceAccountId.equals(targetAccountId) ||
                !balances.containsKey(sourceAccountId) ||
                !balances.containsKey(targetAccountId))
        {
            return -1;
        }

        int sourceBalance = balances.get(sourceAccountId);
        if(sourceBalance < amount)
        {
            return -1;
        }

        balances.put(sourceAccountId, sourceBalance - amount);
        balances.put(targetAccountId, balances.get(targetAccountId) + amount);

        int currentOutgoing = outgoingTotals.get(sourceAccountId);
        outgoingTotals.put(sourceAccountId, currentOutgoing + amount);

        return balances.get(sourceAccountId);
    }

    public List<String> topSpenders(int timestamp, int n)
    {
        List<String> accountIds = new ArrayList<>(outgoingTotals.keySet());

        Collections.sort(accountIds, (a, b) -> {
            int outA = outgoingTotals.get(a);
            int outB = outgoingTotals.get(b);

            if(outA != outB)
            {
                return outB - outA;
            }
            return a.compareTo(b);
        });

        List<String> result = new ArrayList<>();
        int limit = Math.min(n, accountIds.size());

        for(int i = 0; i < limit; i++)
        {
            String id = accountIds.get(i);
            result.add(id + "(" + outgoingTotals.get(id) + ")");
        }

        return result;
    }
}