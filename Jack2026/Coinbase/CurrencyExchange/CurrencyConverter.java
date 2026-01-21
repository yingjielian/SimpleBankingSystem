package Jack2026.Coinbase.CurrencyExchange;

import java.util.*;

class CurrencyConverter {
    // 存储图：货币 -> 邻接边列表
    private final Map<String, List<Edge>> adj = new HashMap<>();

    private static class Edge {
        String to;
        double rate;
        Edge(String to, double rate) {
            this.to = to;
            this.rate = rate;
        }
    }

    public CurrencyConverter(String[] fromArr, String[] toArr, double[] rateArr) {
        for (int i = 0; i < fromArr.length; i++) {
            String u = fromArr[i];
            String v = toArr[i];
            double r = rateArr[i];

            // 添加双向边
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(new Edge(v, r));
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(new Edge(u, 1.0 / r));
        }
    }

    public double getBestRate(String from, String toCurrency) {
        // 如果货币不存在
        if (!adj.containsKey(from) || !adj.containsKey(toCurrency)) {
            // 特殊处理：如果起点终点相同且存在于图中
            if (from.equals(toCurrency) && adj.containsKey(from)) return 1.0;
            return -1.0;
        }
        if (from.equals(toCurrency)) return 1.0;

        // 使用 Set 记录访问过的节点，防止路径成环
        Set<String> visited = new HashSet<>();
        double result = dfs(from, toCurrency, 1.0, visited);

        return result > 0 ? result : -1.0;
    }

    /**
     * DFS 搜索所有可能的简单路径并返回最大乘积
     */
    private double dfs(String current, String target, double currentRate, Set<String> visited) {
        // 找到目标
        if (current.equals(target)) {
            return currentRate;
        }

        visited.add(current);
        double maxRate = -1.0;

        List<Edge> neighbors = adj.get(current);
        if (neighbors != null) {
            for (Edge edge : neighbors) {
                if (!visited.contains(edge.to)) {
                    // 递归搜索分支
                    double rate = dfs(edge.to, target, currentRate * edge.rate, visited);
                    if (rate > maxRate) {
                        maxRate = rate;
                    }
                }
            }
        }

        // 回溯：移除访问标记，允许其他路径经过此节点
        visited.remove(current);
        return maxRate;
    }
}