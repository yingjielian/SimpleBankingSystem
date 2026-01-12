package Jack2026.Coinbase.BankingSystem;

import java.util.*;

public class BankingSystem_4 {
    private Map<String, Integer> currentBalances = new HashMap<>();
    private Map<String, Integer> outgoingTotals = new HashMap<>();
    private Map<String, TreeMap<Integer, Integer>> history = new HashMap<>();
    private Map<String, Integer> creationTimes = new HashMap<>();
    private Map<String, Integer> mergeTimes = new HashMap<>();

    // Ownership for redirected cashbacks (DSU)
    private Map<String, String> ownerMap = new HashMap<>();

    private Map<String, PaymentRecord> payments = new HashMap<>();
    private PriorityQueue<CashbackEvent> cashbackQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.timestamp));

    private int paymentCounter = 0;
    private static final int CASHBACK_DELAY = 86400000;

    private static class PaymentRecord {
        String originalAccountId;
        int cashbackAmount;
        boolean isProcessed = false;
        PaymentRecord(String id, int amt) { this.originalAccountId = id; this.cashbackAmount = amt; }
    }

    private static class CashbackEvent {
        int timestamp;
        String paymentId;
        CashbackEvent(int t, String id) { this.timestamp = t; this.paymentId = id; }
    }

    // Resolves who currently owns an account's funds (handles multiple merges)
    private String findCurrentOwner(String accountId) {
        if (!ownerMap.containsKey(accountId)) return accountId;
        String owner = ownerMap.get(accountId);
        if (owner.equals(accountId)) return accountId;
        String root = findCurrentOwner(owner);
        ownerMap.put(accountId, root); // Path compression
        return root;
    }

    private void updateHistory(String accountId, int timestamp, int balance) {
        history.get(accountId).put(timestamp, balance);
    }

    private void processCashbacks(int currentTimestamp) {
        while (!cashbackQueue.isEmpty() && cashbackQueue.peek().timestamp <= currentTimestamp) {
            CashbackEvent event = cashbackQueue.poll();
            PaymentRecord record = payments.get(event.paymentId);

            String currentOwner = findCurrentOwner(record.originalAccountId);
            int newBal = currentBalances.get(currentOwner) + record.cashbackAmount;

            currentBalances.put(currentOwner, newBal);
            updateHistory(currentOwner, event.timestamp, newBal);
            record.isProcessed = true;
        }
    }

    public boolean createAccount(int timestamp, String accountId) {
        processCashbacks(timestamp);
        if (currentBalances.containsKey(accountId)) return false;

        currentBalances.put(accountId, 0);
        outgoingTotals.put(accountId, 0);
        creationTimes.put(accountId, timestamp);
        history.put(accountId, new TreeMap<>());
        updateHistory(accountId, timestamp, 0);
        return true;
    }

    public int deposit(int timestamp, String accountId, int amount) {
        processCashbacks(timestamp);
        if (!currentBalances.containsKey(accountId)) return -1;

        int newBal = currentBalances.get(accountId) + amount;
        currentBalances.put(accountId, newBal);
        updateHistory(accountId, timestamp, newBal);
        return newBal;
    }

    public int transfer(int timestamp, String src, String tar, int amount) {
        processCashbacks(timestamp);
        if (src.equals(tar) || !currentBalances.containsKey(src) || !currentBalances.containsKey(tar)) return -1;
        if (currentBalances.get(src) < amount) return -1;

        int newSrc = currentBalances.get(src) - amount;
        int newTar = currentBalances.get(tar) + amount;

        currentBalances.put(src, newSrc);
        currentBalances.put(tar, newTar);
        updateHistory(src, timestamp, newSrc);
        updateHistory(tar, timestamp, newTar);

        outgoingTotals.put(src, outgoingTotals.get(src) + amount);
        return newSrc;
    }

    public String pay(int timestamp, String accountId, int amount) {
        processCashbacks(timestamp);
        if (!currentBalances.containsKey(accountId) || currentBalances.get(accountId) < amount) return "";

        int newBal = currentBalances.get(accountId) - amount;
        currentBalances.put(accountId, newBal);
        updateHistory(accountId, timestamp, newBal);
        outgoingTotals.put(accountId, outgoingTotals.get(accountId) + amount);

        String pId = "payment" + (++paymentCounter);
        payments.put(pId, new PaymentRecord(accountId, amount / 50)); // 2% is amount/50
        cashbackQueue.add(new CashbackEvent(timestamp + CASHBACK_DELAY, pId));
        return pId;
    }

    public boolean mergeAccounts(int timestamp, String id1, String id2)
    {
        processCashbacks(timestamp);
        if(id1.equals(id2) || !currentBalances.containsKey(id1) || !currentBalances.containsKey(id2)) return false;

        int newBal1 = currentBalances.get(id1) + currentBalances.get(id2);
        currentBalances.put(id1, newBal1);
        updateHistory(id1, timestamp, newBal1);

        outgoingTotals.put(id1, outgoingTotals.get(id1) + outgoingTotals.get(id2));

        currentBalances.remove(id2);
        outgoingTotals.remove(id2);
        mergeTimes.put(id2, timestamp);
        ownerMap.put(id2, id1);

        return true;
    }

    public int getBalance(int timestamp, String accountId, int timeAt)
    {
        processCashbacks(timestamp);
        if(!creationTimes.containsKey(accountId) || creationTimes.get(accountId) > timeAt) return -1;
        if(mergeTimes.containsKey(accountId) && mergeTimes.get(accountId) <= timeAt) return -1;

        return history.get(accountId).floorEntry(timeAt).getValue();
    }

    public String getPaymentStatus(int timestamp, String accountId, String pId) {
        processCashbacks(timestamp);
        if (!payments.containsKey(pId)) return "";
        PaymentRecord p = payments.get(pId);
        if (!findCurrentOwner(p.originalAccountId).equals(accountId)) return "";
        return p.isProcessed ? "CASHBACK_RECEIVED" : "IN_PROGRESS";
    }

    public List<String> topSpenders(int timestamp, int n) {
        processCashbacks(timestamp);
        List<String> ids = new ArrayList<>(outgoingTotals.keySet());
        ids.sort((a, b) -> {
            int outA = outgoingTotals.get(a), outB = outgoingTotals.get(b);
            return outA != outB ? outB - outA : a.compareTo(b);
        });
        List<String> res = new ArrayList<>();
        for (int i = 0; i < Math.min(n, ids.size()); i++) res.add(ids.get(i) + "(" + outgoingTotals.get(ids.get(i)) + ")");
        return res;
    }
}
