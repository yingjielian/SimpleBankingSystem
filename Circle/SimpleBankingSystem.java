package Jack2025.Circle;

import java.util.*;

public class SimpleBankingSystem {
    private static final long MILLISECONDS_IN_1_DAY = 24 * 60 * 60 * 1000L;

    private Map<String, Integer> accounts = new HashMap<>();
    private Map<String, Integer> transactionTotals = new HashMap<>();

    // Transfer tracking
    private Map<String, Transfer> transfers = new HashMap<>();
    private int transferCount = 0;

    private static class Transfer {
        String transferId;
        String source;
        String target;
        int amount;
        long timestamp;
        boolean accepted;
        boolean expired;

        Transfer(String transferId, String source, String target, int amount, long timestamp) {
            this.transferId = transferId;
            this.source = source;
            this.target = target;
            this.amount = amount;
            this.timestamp = timestamp;
            this.accepted = false;
            this.expired = false;
        }
    }

    public boolean createAccount(int timestamp, String accountId) {
        if (accounts.containsKey(accountId)) return false;
        accounts.put(accountId, 0);
        transactionTotals.put(accountId, 0);
        return true;
    }

    public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
        if (!accounts.containsKey(accountId)) return Optional.empty();
        int newBalance = accounts.get(accountId) + amount;
        accounts.put(accountId, newBalance);
        transactionTotals.put(accountId, transactionTotals.get(accountId) + amount);
        return Optional.of(newBalance);
    }

    public Optional<Integer> pay(int timestamp, String accountId, int amount) {
        if (!accounts.containsKey(accountId)) return Optional.empty();
        int currentBalance = accounts.get(accountId);
        if (currentBalance < amount) return Optional.empty();
        int newBalance = currentBalance - amount;
        accounts.put(accountId, newBalance);
        transactionTotals.put(accountId, transactionTotals.get(accountId) + amount);
        return Optional.of(newBalance);
    }

    public List<String> topActivity(int timestamp, int n) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(transactionTotals.entrySet());
        entries.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey());
        });
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            result.add(entry.getKey() + "(" + entry.getValue() + ")");
        }
        return result;
    }

    // --- TRANSFER METHODS ---

    public Optional<String> transfer(long timestamp, String sourceAccountId, String targetAccountId, int amount) {
        if (sourceAccountId.equals(targetAccountId)) return Optional.empty();
        if (!accounts.containsKey(sourceAccountId) || !accounts.containsKey(targetAccountId)) return Optional.empty();
        int sourceBalance = accounts.get(sourceAccountId);
        if (sourceBalance < amount) return Optional.empty();

        // Deduct from source and update transaction total
        accounts.put(sourceAccountId, sourceBalance - amount);
        transactionTotals.put(sourceAccountId, transactionTotals.get(sourceAccountId) + amount);
        transactionTotals.put(targetAccountId, transactionTotals.get(targetAccountId) + amount);

        // Create transfer
        String transferId = "transfer" + (++transferCount);
        Transfer transfer = new Transfer(transferId, sourceAccountId, targetAccountId, amount, timestamp);
        transfers.put(transferId, transfer);
        return Optional.of(transferId);
    }

    public boolean acceptTransfer(long timestamp, String accountId, String transferId) {
        Transfer transfer = transfers.get(transferId);
        if (transfer == null) return false;
        if (transfer.accepted) return false;
        if (transfer.expired) return false;
        if (!transfer.target.equals(accountId)) return false;

        long expirationTime = transfer.timestamp + MILLISECONDS_IN_1_DAY;
        if (timestamp > expirationTime) {
            // Expired, return funds to source if not already done
            if (!transfer.expired) {
                accounts.put(transfer.source, accounts.get(transfer.source) + transfer.amount);
                transfer.expired = true;
            }
            return false;
        }
        // Accept transfer
        accounts.put(accountId, accounts.get(accountId) + transfer.amount);
        transfer.accepted = true;
        return true;
    }

    // For testing
    public static void main(String[] args) {
        final long MILLISECONDS_IN_1_DAY = 24 * 60 * 60 * 1000L;
        SimpleBankingSystem bank = new SimpleBankingSystem();
        System.out.println(bank.createAccount(1, "account1")); // true
        System.out.println(bank.createAccount(2, "account2")); // true
        System.out.println(bank.deposit(3, "account1", 2000)); // Optional[2000]
        System.out.println(bank.deposit(4, "account2", 3000)); // Optional[3000]
        System.out.println(bank.transfer(5, "account1", "account2", 5000)); // Optional.empty()
        System.out.println(bank.transfer(16, "account1", "account2", 1000)); // Optional[transfer1]
        System.out.println(bank.acceptTransfer(20, "account1", "transfer1")); // false
        System.out.println(bank.acceptTransfer(21, "non-existing", "transfer1")); // false
        System.out.println(bank.acceptTransfer(22, "account1", "transfer2")); // false
        System.out.println(bank.acceptTransfer(25, "account2", "transfer1")); // true
        System.out.println(bank.acceptTransfer(30, "account2", "transfer1")); // false
        System.out.println(bank.transfer(40, "account1", "account2", 1000)); // Optional[transfer2]
        System.out.println(bank.acceptTransfer(45 + MILLISECONDS_IN_1_DAY, "transfer2", "transfer2")); // false
        System.out.println(bank.transfer(50 + MILLISECONDS_IN_1_DAY, "account1", "account1", 1000)); // Optional.empty()
    }
}
