package Jack2026.Socure;

import java.util.*;
class DetectFraud {

    static class Transaction
    {
        int transaction_id;
        int user_id;
        double amount;
        int timestamp;

        Transaction(int transaction_id, int user_id, double amount, int timestamp)
        {
            this.transaction_id = transaction_id;
            this.user_id = user_id;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        // <user_id, PQ<>>
        // <t2, 20>
        //
        @Override
        public String toString()
        {
            return "ID: " + transaction_id + " (T:" + timestamp + ")";
        }
    }

//    static Map<Integer, PriorityQueue<Transaction>> userToTransactions = new HashMap<>();

    public static List<Transaction> nonFraudTransactions(List<Transaction> transactions)
    {
        if(transactions == null || transactions.isEmpty()) return new ArrayList<>();

        Map<Integer, List<Transaction>> userToTransactions = new HashMap<>();

        for(Transaction t : transactions)
        {
            userToTransactions.computeIfAbsent(t.user_id, k -> new ArrayList<>()).add(t);
        }

        Set<Transaction> fraudSet = new HashSet<>();

        for(List<Transaction> userList : userToTransactions.values())
        {
            userList.sort(Comparator.comparingInt(t -> t.timestamp));

            int left = 0;
            for(int right = 0; right < userList.size(); right++)
            {
                while(userList.get(right).timestamp - userList.get(left).timestamp > 60)
                {
                    left++;
                }

                if(right - left + 1 >= 3)
                {
                    for(int i = left; i <= right; i++)
                    {
                        fraudSet.add(userList.get(i));
                    }
                }
            }
        }

        List<Transaction> result = new ArrayList<>();
        for(Transaction t : transactions)
        {
            if(!fraudSet.contains(t))
            {
                result.add(t);
            }
        }

        return result;
    }

    public static void main(String[] args) {

        //     input:
        // trans1, 1, 10
        // trans2, 1, 20
        // trans3, 1, 40
        // trans4, 2, 50
        // trans5, 1, 70
        // trans6, 1, 100

        Transaction tran1 = new Transaction(101, 1, 50, 10);
        Transaction tran2 = new Transaction(102, 1, 50, 20);
        Transaction tran3 = new Transaction(103, 1, 50, 40);
        Transaction tran4 = new Transaction(104, 2, 50, 50);
        Transaction tran5 = new Transaction(105, 1, 50, 70);
        Transaction tran6 = new Transaction(105, 3, 50, 100);

        List<Transaction> list = new ArrayList<>();
        list.add(tran1);
        list.add(tran2);
        list.add(tran3);
        list.add(tran4);
        list.add(tran5);
        list.add(tran6);

        System.out.println(nonFraudTransactions(list));

    }
}
