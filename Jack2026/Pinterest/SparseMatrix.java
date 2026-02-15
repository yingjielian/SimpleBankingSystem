package Jack2026.Pinterest;

import java.util.HashMap;
import java.util.Map;

class SparseMatrix {
    private final int m; // rows
    private final int n; // cols

    // rows[r] -> (c -> val), only non-zero
    private final Map<Integer, Map<Integer, Long>> rows = new HashMap<>();

    public SparseMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            this.m = 0;
            this.n = 0;
            return;
        }
        this.m = matrix.length;
        this.n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            Map<Integer, Long> rowMap = null;
            for (int j = 0; j < n; j++) {
                int v = matrix[i][j];
                if (v != 0) {
                    if (rowMap == null) rowMap = new HashMap<>();
                    rowMap.put(j, (long) v);
                }
            }
            if (rowMap != null) rows.put(i, rowMap);
        }
    }

    public int[][] add(int[][] otherMatrix) {
        if (!sameSize(otherMatrix)) return new int[0][0];

        // result in sparse form first
        Map<Integer, Map<Integer, Long>> res = deepCopy(rows);

        // add other's non-zero values
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = otherMatrix[i][j];
                if (v == 0) continue;

                Map<Integer, Long> rowMap = res.computeIfAbsent(i, k -> new HashMap<>());
                long sum = rowMap.getOrDefault(j, 0L) + (long) v;
                if (sum == 0L) {
                    rowMap.remove(j);
                    if (rowMap.isEmpty()) res.remove(i);
                } else {
                    rowMap.put(j, sum);
                }
            }
        }

        return toDense(res, m, n);
    }

    public int[][] multiply(int[][] otherMatrix) {
        if (otherMatrix == null || otherMatrix.length == 0 || otherMatrix[0].length == 0) {
            return new int[0][0];
        }
        int otherM = otherMatrix.length;
        int otherN = otherMatrix[0].length;

        // dimension check: (m x n) * (otherM x otherN) requires n == otherM
        if (this.n != otherM) return new int[0][0];

        // Compress otherMatrix by rows: otherRows[k] -> (j -> val), only non-zero
        Map<Integer, Map<Integer, Long>> otherRows = new HashMap<>();
        for (int k = 0; k < otherM; k++) {
            Map<Integer, Long> rowMap = null;
            for (int j = 0; j < otherN; j++) {
                int v = otherMatrix[k][j];
                if (v != 0) {
                    if (rowMap == null) rowMap = new HashMap<>();
                    rowMap.put(j, (long) v);
                }
            }
            if (rowMap != null) otherRows.put(k, rowMap);
        }

        // Multiply: for each non-zero (i,k) in A, iterate non-zero (k,j) in B
        Map<Integer, Map<Integer, Long>> res = new HashMap<>();

        for (Map.Entry<Integer, Map<Integer, Long>> aRowEntry : rows.entrySet()) {
            int i = aRowEntry.getKey();
            Map<Integer, Long> aRow = aRowEntry.getValue();

            for (Map.Entry<Integer, Long> aEntry : aRow.entrySet()) {
                int k = aEntry.getKey();
                long aVal = aEntry.getValue();

                Map<Integer, Long> bRow = otherRows.get(k);
                if (bRow == null) continue;

                Map<Integer, Long> resRow = res.computeIfAbsent(i, x -> new HashMap<>());
                for (Map.Entry<Integer, Long> bEntry : bRow.entrySet()) {
                    int j = bEntry.getKey();
                    long bVal = bEntry.getValue();

                    long prev = resRow.getOrDefault(j, 0L);
                    long next = prev + aVal * bVal;

                    if (next == 0L) {
                        resRow.remove(j);
                    } else {
                        resRow.put(j, next);
                    }
                }

                if (resRow.isEmpty()) res.remove(i);
            }
        }

        return toDense(res, this.m, otherN);
    }

    // ---------- helpers ----------

    private boolean sameSize(int[][] other) {
        return other != null && other.length == this.m && this.m > 0 && other[0].length == this.n;
    }

    private Map<Integer, Map<Integer, Long>> deepCopy(Map<Integer, Map<Integer, Long>> src) {
        Map<Integer, Map<Integer, Long>> copy = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Long>> e : src.entrySet()) {
            copy.put(e.getKey(), new HashMap<>(e.getValue()));
        }
        return copy;
    }

    private int[][] toDense(Map<Integer, Map<Integer, Long>> sparse, int r, int c) {
        int[][] dense = new int[r][c];
        for (Map.Entry<Integer, Map<Integer, Long>> rowEntry : sparse.entrySet()) {
            int i = rowEntry.getKey();
            for (Map.Entry<Integer, Long> colEntry : rowEntry.getValue().entrySet()) {
                int j = colEntry.getKey();
                dense[i][j] = (int) (long) colEntry.getValue(); // note: may overflow if outside int range
            }
        }
        return dense;
    }
}

