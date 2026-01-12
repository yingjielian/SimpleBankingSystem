package Jack2026.Podium;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

class HTTPResonse {

    public static List<String> maximumTransfer(String name, String city) {
        String baseUrl = "https://jsonmock.hackerrank.com/api/transactions";

        double maxCredit = -1;
        double maxDebit = -1;

        String maxCreditStr = "";
        String maxDebitStr = "";

        try {
            int page = 1;
            int totalPages = 1;

            while (page <= totalPages) {
                URL url = new URL(baseUrl + "?page=" + page);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                JSONObject response = new JSONObject(sb.toString());
                totalPages = response.getInt("total_pages");

                JSONArray data = response.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject txn = data.getJSONObject(i);

                    if (!name.equals(txn.getString("userName"))) {
                        continue;
                    }

                    JSONObject location = txn.getJSONObject("location");
                    if (!city.equals(location.getString("city"))) {
                        continue;
                    }

                    String txnType = txn.getString("txnType");
                    String amountStr = txn.getString("amount");

                    double amount = parseAmount(amountStr);

                    if ("credit".equals(txnType)) {
                        if (amount > maxCredit) {
                            maxCredit = amount;
                            maxCreditStr = amountStr;
                        }
                    } else if ("debit".equals(txnType)) {
                        if (amount > maxDebit) {
                            maxDebit = amount;
                            maxDebitStr = amountStr;
                        }
                    }
                }

                page++;
            }
        } catch (Exception e) {
            // HackerRank 通常不需要特殊处理
        }

        return Arrays.asList(
                maxCreditStr.isEmpty() ? "$0.00" : maxCreditStr,
                maxDebitStr.isEmpty() ? "$0.00" : maxDebitStr
        );
    }

    private static double parseAmount(String amount) {
        // "$1,234.56" -> 1234.56
        return Double.parseDouble(
                amount.replace("$", "").replace(",", "")
        );
    }

    public static void main(String[] args)
    {
        System.out.println(maximumTransfer("Helena Fernandez", "Bourg"));
    }
}
