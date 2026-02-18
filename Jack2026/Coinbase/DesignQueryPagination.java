package Jack2026.Coinbase;

import java.util.*;

class QueryPaginationSystem {
    private final List<Record> allRecords;

    private static class Record{
        final long timestamp;
        final String id;
        final String userId;
        final String currency;
        final long amount;
        final List<String> rawRecord;
        Record(List<String> record)
        {
            this.timestamp = Long.parseLong(record.get(0));
            this.id = record.get(1);
            this.userId = record.get(2);
            this.currency = record.get(3);
            this.amount = Long.parseLong(record.get(4));
            this.rawRecord = record;
        }
    }
    // 过滤条件
    private Integer pageSize = null;
    private Long startTime = null;
    private Long endTime = null;
    private Long minAmount = null;
    private Long maxAmount = null;
    private String filterUserId = null;
    private String filterCurrency = null;

    // 分页状态
    private List<List<String>> filteredCache = null;
    private int currentPagePointer = 0;
    private List<Integer> pageStarts = new ArrayList<>();

    public QueryPaginationSystem(List<List<String>> records) {
        // 按照 timestamp 升序排序 (timestamp 在索引 0)
        this.allRecords = new ArrayList<>();
        for(List<String> r : records)
        {
            this.allRecords.add(new Record(r));
        }
        this.allRecords.sort(Comparator.comparingLong(a -> a.timestamp));
    }

    public void setPageSize(int size) {
        this.pageSize = size;
        resetPagination();
    }

    public void setTimeRange(int start, int end) {
        this.startTime = (long) start;
        this.endTime = (long) end;
        resetPagination();
    }

    public void setAmountRange(int start, int end) {
        this.minAmount = (long) start;
        this.maxAmount = (long) end;
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
        for (Record r : allRecords) {

            // 执行过滤逻辑
            if (startTime != null && (r.timestamp < startTime || r.timestamp > endTime)) continue;
            if (minAmount != null && (r.amount < minAmount || r.amount > maxAmount)) continue;
            if (filterUserId != null && !filterUserId.equals(r.userId)) continue;
            if (filterCurrency != null && !filterCurrency.equals(r.userId)) continue;

            filteredCache.add(r.rawRecord);
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

        // 记录当前 pointer，作为这一页的起始位置
        if(pageStarts.isEmpty() || pageStarts.get(pageStarts.size() -1) != currentPagePointer)
        {
            pageStarts.add(currentPagePointer);
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

    public List<List<String>> prevPage()
    {
        if(filteredCache == null)
        {
            applyFilters();
        }

        if(pageSize <= 1)
        {
            currentPagePointer = 0;
            if(pageStarts.size() == 1) pageStarts.remove(0);
            return new ArrayList<>();
        }

        pageStarts.remove(pageStarts.size() - 1);
        int prevStart;
        if(pageStarts.isEmpty())
        {
            currentPagePointer = 0;
            return new ArrayList<>();
        }
        else
        {
            prevStart = pageStarts.get(pageStarts.size() - 1);
        }


        currentPagePointer = prevStart;

        return nextPage();
    }

        public static void main(String[] args) {
            // Test Case 1: The Example from the Problem Description
            System.out.println("--- Test Case 1: Standard Example ---");
            List<List<String>> records1 = new ArrayList<>();
            records1.add(Arrays.asList("1", "id-1", "user-1", "USD", "5"));
            records1.add(Arrays.asList("2", "id-2", "user-2", "USD", "10"));
            records1.add(Arrays.asList("3", "id-3", "user-1", "CAD", "20"));
            records1.add(Arrays.asList("4", "id-4", "user-1", "CAD", "10"));
            records1.add(Arrays.asList("5", "id-5", "user-1", "AUD", "30"));
            records1.add(Arrays.asList("6", "id-6", "user-1", "JPY", "100"));

            QueryPaginationSystem system1 = new QueryPaginationSystem(records1);
            system1.setPageSize(2);
            system1.setTimeRange(1, 6);
            system1.setUserId("user-1");

            System.out.println("Page 1: " + system1.nextPage()); // Expecting id-1, id-3
            System.out.println("Page 2: " + system1.nextPage()); // Expecting id-4, id-5
            System.out.println("Page 3: " + system1.nextPage()); // Expecting id-6
            System.out.println("Page 2: " + system1.prevPage()); // Expecting id-4, id-5
            System.out.println("Page 3: " + system1.nextPage()); // Expecting id-6
            System.out.println("Page 2: " + system1.prevPage()); // Expecting id-4, id-5
            System.out.println("Page 1: " + system1.prevPage()); // Expecting id-1, id-3
            System.out.println("Page 0: " + system1.prevPage()); // []
            System.out.println("Page 1: " + system1.nextPage()); // Expecting id-1, id-3

            // Test Case 2: No Page Size (Return All) & Amount Filter
            System.out.println("\n--- Test Case 2: No Page Size & Amount Filter ---");
            List<List<String>> records2 = new ArrayList<>();
            records2.add(Arrays.asList("10", "tx-1", "u1", "USD", "50"));
            records2.add(Arrays.asList("5", "tx-2", "u1", "USD", "100")); // Notice: out of order timestamp
            records2.add(Arrays.asList("15", "tx-3", "u1", "USD", "150"));

            QueryPaginationSystem system2 = new QueryPaginationSystem(records2);
            system2.setAmountRange(100, 200);
            // Page size is NOT set, should return all matching records in one go
            System.out.println("All Matching: " + system2.nextPage()); // Expecting tx-2 (ts:5), then tx-3 (ts:15)
            System.out.println("Next Page: " + system2.nextPage());    // Expecting []

            // Test Case 3: Empty Results
            System.out.println("\n--- Test Case 3: Empty Results ---");
            QueryPaginationSystem system3 = new QueryPaginationSystem(records1);
            system3.setCurrency("EUR"); // No EUR records exist
            System.out.println("Empty Filter: " + system3.nextPage()); // Expecting []

            // Test Case 4: Resetting Filters
            System.out.println("\n--- Test Case 4: Resetting Mid-way ---");
            QueryPaginationSystem system4 = new QueryPaginationSystem(records1);
            system4.setPageSize(1);
            System.out.println("Page 1 (No Filter): " + system4.nextPage()); // Expecting id-1

            system4.setUserId("user-2"); // Filter changed! Pagination should reset.
            System.out.println("Page 1 (New Filter): " + system4.nextPage()); // Expecting id-2
        }
    }
