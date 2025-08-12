package Jack2025.SOFI;

import java.util.*;
public class TransactionFrequency_YIMU {
    public static Map<String, List<String>> mostFrequentTransactions(List<String[]> transactions) {
        // Map<transaction_type, Map<user, count>>
        Map<String, Map<String, Integer>> txnUserCount = new HashMap<>();
        // Map<transaction_type, total_count>
        Map<String, Integer> txnTotalCount = new HashMap<>();

        // Count frequencies
        for (String[] t : transactions) {
            String user = t[0];
            String txn = t[1];

            txnUserCount.putIfAbsent(txn, new HashMap<>());
            Map<String, Integer> userCount = txnUserCount.get(txn);
            userCount.put(user, userCount.getOrDefault(user, 0) + 1);

            txnTotalCount.put(txn, txnTotalCount.getOrDefault(txn, 0) + 1);
        }

        // Filter out transactions with total count < 2
        Map<String, Map<String, Integer>> filteredTxnUserCount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : txnTotalCount.entrySet()) {
            if (entry.getValue() >= 2) {
                filteredTxnUserCount.put(entry.getKey(), txnUserCount.get(entry.getKey()));
            }
        }

        // Sort transaction types by total count descending
        List<String> sortedTxnTypes = new ArrayList<>(filteredTxnUserCount.keySet());
        sortedTxnTypes.sort((a, b) -> txnTotalCount.get(b) - txnTotalCount.get(a));

        // Build result
        Map<String, List<String>> result = new LinkedHashMap<>();

        for (String txn : sortedTxnTypes) {
            Map<String, Integer> userCount = filteredTxnUserCount.get(txn);
            // Sort users by name ascending
            List<String> users = new ArrayList<>(userCount.keySet());
            Collections.sort(users);
            result.put(txn, users);
        }

        return result;
    }

    public static void main(String[] args) {
        List<String[]> transactions = Arrays.asList(
                new String[]{"josh", "transaction_type_a"},
                new String[]{"josh", "transaction_type_b"},
                new String[]{"josh", "transaction_type_b"},
                new String[]{"alan", "transaction_type_a"},
                new String[]{"alan", "transaction_type_a"},
                new String[]{"alan", "transaction_type_a"},
                new String[]{"alan", "transaction_type_c"}
        );

        Map<String, List<String>> res = mostFrequentTransactions(transactions);

        System.out.println(res);
    }
}
