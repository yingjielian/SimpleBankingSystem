package Jack2026.Coinbase;

import java.util.*;

class QueryPaginationSystem {
    private List<List<String>> allRecords;

    // 过滤条件
    private Integer pageSize = null;
    private Integer startTime = null;
    private Integer endTime = null;
    private Integer minAmount = null;
    private Integer maxAmount = null;
    private String filterUserId = null;
    private String filterCurrency = null;

    // 分页状态
    private List<List<String>> filteredCache = null;
    private int currentPagePointer = 0;

    public QueryPaginationSystem(List<List<String>> records) {
        // 按照 timestamp 升序排序 (timestamp 在索引 0)
        this.allRecords = new ArrayList<>(records);
        this.allRecords.sort(Comparator.comparingLong(a -> Long.parseLong(a.get(0))));
    }

    public void setPageSize(int size) {
        this.pageSize = size;
        resetPagination();
    }

    public void setTimeRange(int start, int end) {
        this.startTime = start;
        this.endTime = end;
        resetPagination();
    }

    public void setAmountRange(int start, int end) {
        this.minAmount = start;
        this.maxAmount = end;
        resetPagination();
    }

    public void setUserId(String id) {
        this.filterUserId = id;
        resetPagination();
    }

    public void setCurrency(String currency) {
        this.filterCurrency = currency;
        resetPagination();
    }

    private void resetPagination() {
        // 当任何过滤条件改变时，清空缓存并重置指针
        this.filteredCache = null;
        this.currentPagePointer = 0;
    }

    private void applyFilters() {
        filteredCache = new ArrayList<>();
        for (List<String> record : allRecords) {
            long ts = Long.parseLong(record.get(0));
            String userId = record.get(2);
            String currency = record.get(3);
            long amount = Long.parseLong(record.get(4));

            // 执行过滤逻辑
            if (startTime != null && (ts < startTime || ts > endTime)) continue;
            if (minAmount != null && (amount < minAmount || amount > maxAmount)) continue;
            if (filterUserId != null && !filterUserId.equals(userId)) continue;
            if (filterCurrency != null && !filterCurrency.equals(currency)) continue;

            filteredCache.add(record);
        }
    }

    public List<List<String>> nextPage() {
        // 第一次调用或条件更新后，生成筛选结果
        if (filteredCache == null) {
            applyFilters();
        }

        if (currentPagePointer >= filteredCache.size()) {
            return new ArrayList<>();
        }

        // 确定当前页结束位置
        int end;
        if (pageSize == null) {
            end = filteredCache.size();
        } else {
            end = Math.min(currentPagePointer + pageSize, filteredCache.size());
        }

        List<List<String>> pageResult = new ArrayList<>(filteredCache.subList(currentPagePointer, end));
        currentPagePointer = end; // 移动指针到下一页起点

        return pageResult;
    }
}
