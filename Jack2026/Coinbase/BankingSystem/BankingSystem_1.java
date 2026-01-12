package Jack2026.Coinbase.BankingSystem;

import java.util.*;

public class BankingSystem_1 {

    // Map to Store accountId as key and the current balance as value
    private Map<String, Integer> accounts;

    // BankingSystem() Initializes the banking system with no accounts.
    public BankingSystem_1() {
        // Initialize the banking system with an empty map
        this.accounts = new HashMap<>();
    }

    // boolean createAccount(int timestamp, String accountId) Creates a new account
    // identified by accountId with a zero balance.
    //Returns true if the account is created successfully.
    //Returns false if an account with the same accountId already exists.
    public boolean createAccount(int timestamp, String accountId) {
        // Check if account already exists
        if (accounts.containsKey(accountId)) {
            return false;
        }
        // Create account with initial balance of 0
        accounts.put(accountId, 0);
        return true;
    }

    // int deposit(int timestamp, String accountId, int amount) Adds
    // amount to the balance of the existing account accountId.
    //
    //Returns the new balance if the account exists.
    //Returns -1 if accountId does not exist.
    public int deposit(int timestamp, String accountId, int amount) {
        // Check if account exists
        if (!accounts.containsKey(accountId)) {
            return -1;
        }

        int newBalance = accounts.get(accountId) + amount;
        accounts.put(accountId, newBalance);

        return newBalance;
    }

    // int transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount)
    // Transfers amount from sourceAccountId to targetAccountId.
    //
    //Returns the new balance of sourceAccountId if the transfer succeeds.
    //Returns -1 if either account does not exist, the IDs are the same, or sourceAccountId has insufficient funds.
    public int transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        // Check if source or target accounts not exist, or sourceAccount has
        // insufficient fund

        if (!accounts.containsKey(sourceAccountId)
                || !accounts.containsKey(targetAccountId)
                || sourceAccountId.equals(targetAccountId)) {
            return -1;
        }

        int sourceAccountBalance = accounts.get(sourceAccountId);
        if(sourceAccountBalance < amount)
        {
            return -1;
        }

        int sourceAccountNewBalance = sourceAccountBalance - amount;
        accounts.put(sourceAccountId, sourceAccountNewBalance);

        int targetAccountNewBalance = accounts.get(targetAccountId) + amount;
        accounts.put(targetAccountId, targetAccountNewBalance);

        return sourceAccountNewBalance;

    }

    public static void main(String[] args) {
        BankingSystem_1 bs = new BankingSystem_1();
        System.out.println(bs.createAccount(1, "account1"));
        System.out.println(bs.createAccount(2, "account1"));
        System.out.println(bs.createAccount(3, "account2"));
        System.out.println(bs.deposit(4, "non-existing", 2700));
        System.out.println(bs.deposit(5, "account1", 2700));
        System.out.println(bs.transfer(6, "account1", "account2", 2701));
        System.out.println(bs.transfer(7, "account1", "account2", 200));
    }
}
