package Jack2026.Coinbase;

import java.util.*;
import java.util.function.Predicate;

class FilteringIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final Predicate<Integer> filter;
    private Integer nextElement; // 缓存下一个符合条件的元素
    private boolean hasNextElement; // 标记缓存是否有效

    public FilteringIterator(List<Integer> arr, Predicate<Integer> filter) {
        this.iterator = arr.iterator();
        this.filter = filter;
        this.nextElement = null;
        this.hasNextElement = false;
    }

    @Override
    public boolean hasNext() {
        // 如果缓存中已经有值，直接返回 true
        if (hasNextElement) {
            return true;
        }

        // 尝试从原始迭代器中寻找下一个符合条件的元素
        while (iterator.hasNext()) {
            Integer current = iterator.next();
            if (filter.test(current)) {
                nextElement = current;
                hasNextElement = true;
                return true;
            }
        }

        return false;
    }

    @Override
    public Integer next() {
        // 习惯做法：在 next 中调用 hasNext 确保逻辑稳健
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // 标记缓存已消耗，并返回暂存的值
        hasNextElement = false;
        Integer result = nextElement;
        nextElement = null; // 释放引用，有助于 GC
        return result;
    }
}

// Solution 类保持不变...
class Solution_1 {
    public static void test1() {
        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        FilteringIterator iterator = new FilteringIterator(array, isEven);
        System.out.print("Test 1 - Even Numbers: ");
        printResult(iterator); // Expected Output: [2, 4, 6, 8]
    }

    public static void test2() {
        List<Integer> array = Arrays.asList(0, 3, 5, 7, 10, 12, 15);
        Predicate<Integer> greaterThanFive = x -> x > 5;
        FilteringIterator iterator = new FilteringIterator(array, greaterThanFive);
        System.out.print("Test 2 - Numbers Greater Than 5: ");
        printResult(iterator); // Expected Output: [7, 10, 12, 15]
    }

    public static void test3() {
        List<Integer> array = Arrays.asList(2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        Predicate<Integer> isPrime = x -> isPrime(x);
        FilteringIterator iterator = new FilteringIterator(array, isPrime);
        System.out.print("Test 3 - Prime Numbers: ");
        printResult(iterator); // Expected Output: [2, 5, 7, 11, 13]
    }

    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num); i += 2)
            if (num % i == 0) return false;
        return true;
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void printResult(FilteringIterator iterator) {
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        System.out.println(result);
    }
}
