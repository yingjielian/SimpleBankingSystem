package Jack2026.Coinbase.BankingSystem;

import java.util.*;
public class BankingSystem_3 {
    private Map<String, Integer> balances = new HashMap<>();
    private Map<String, Integer> outgoingTotals = new HashMap<>();

    private Map<String, PaymentRecord> payments = new HashMap<>();

    private PriorityQueue<CashbackEvent> cashbackQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.timestamp));
    private int paymentCounter = 0;


    private static final int CASHBACK_DELAY = 86400000;
    private static class PaymentRecord{
        String accountId;
        int cashbackAmount;
        int scheduledTime;
        boolean isProcessed = false;

        PaymentRecord(String accountId, int cashbackAmount, int scheduledTime)
        {
            this.accountId = accountId;
            this.cashbackAmount = cashbackAmount;
            this.scheduledTime = scheduledTime;
        }
    }

    public static class CashbackEvent{
        int timestamp;
        String paymentId;

        CashbackEvent(int timestamp, String paymentId)
        {
            this.timestamp = timestamp;
            this.paymentId = paymentId;
        }
    }

    private void processCashbacks(int currentTimestamp)
    {
        while(!cashbackQueue.isEmpty() && cashbackQueue.peek().timestamp <= currentTimestamp)
        {
            CashbackEvent event = cashbackQueue.poll();
            PaymentRecord record = payments.get(event.paymentId);

            balances.put(record.accountId, balances.get(record.accountId) + record.cashbackAmount);
            record.isProcessed = true;
        }
    }

    public boolean createAccount(int timestamp, String accountId) {
        processCashbacks(timestamp);
        if(balances.containsKey(accountId)) return false;
        balances.put(accountId, 0);
        outgoingTotals.put(accountId, 0);
        return true;
    }

    public int deposit(int timestamp, String accountId, int amount) {
        processCashbacks(timestamp);
        if (!balances.containsKey(accountId)) return -1;
        balances.put(accountId, balances.get(accountId) + amount);
        return balances.get(accountId);
    }

    public int transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        processCashbacks(timestamp);
        if (sourceAccountId.equals(targetAccountId) || !balances.containsKey(sourceAccountId) || !balances.containsKey(targetAccountId)) return -1;
        if (balances.get(sourceAccountId) < amount) return -1;

        balances.put(sourceAccountId, balances.get(sourceAccountId) - amount);
        balances.put(targetAccountId, balances.get(targetAccountId) + amount);
        outgoingTotals.put(sourceAccountId, outgoingTotals.get(sourceAccountId) + amount);
        return balances.get(sourceAccountId);
    }

    public String pay(int timestamp, String accountId, int amount) {
        processCashbacks(timestamp);
        if(!balances.containsKey(accountId) || balances.get(accountId) < amount) return "";

        balances.put(accountId, balances.get(accountId) - amount);
        outgoingTotals.put(accountId, outgoingTotals.get(accountId) + amount);

        paymentCounter++;
        String paymentId = "payment" + paymentCounter;
        int cashBackAmount = (int) (amount * 0.02);
        int scheduledTime = timestamp + CASHBACK_DELAY;

        PaymentRecord record = new PaymentRecord(accountId, cashBackAmount, scheduledTime);
        payments.put(paymentId, record);
        cashbackQueue.add(new CashbackEvent(scheduledTime, paymentId));

        return paymentId;
    }

    public String getPaymentStatus(int timestamp, String accountId, String paymentId) {
        processCashbacks(timestamp);
        if(!payments.containsKey(paymentId)) return "";
        PaymentRecord record = payments.get(paymentId);

        if(!record.accountId.equals(accountId)) return "";

        return record.isProcessed ? "CASHBACK_RECEIVED" : "IN_PROGRESS";
    }

    public List<String> topSpenders(int timestamp, int n)
    {
        processCashbacks(timestamp);
        List<String> ids = new ArrayList<>(outgoingTotals.keySet());
        ids.sort((a, b) -> {
            int outA = outgoingTotals.get(a), outB = outgoingTotals.get(b);
            return outA != outB ? outB - outA : a.compareTo(b);
        });

        List<String> res = new ArrayList<>();
        for(int i = 0; i < Math.min(n, ids.size()); i++)
        {
            res.add(ids.get(i) + "(" + outgoingTotals.get(ids.get(i)) + ")");
        }

        return res;
    }


}
