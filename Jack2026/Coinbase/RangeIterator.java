package Jack2026.Coinbase;

import java.util.*;

class RangeIterator implements Iterator<Integer> {
    private final int stop;
    private final int step;
    private int current;

    public RangeIterator(int start, int stop, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step cannot be zero.");
        }
        this.current = start;
        this.stop = stop;
        this.step = step;
    }

    @Override
    public boolean hasNext() {
        // 如果 step 为正，current 必须小于 stop
        // 如果 step 为负，current 必须大于 stop
        if (step > 0) {
            return current < stop;
        } else {
            return current > stop;
        }
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int res = current;
        current += step;
        return res;
    }
}

// Test Cases
class Solution_2 {
    public static void main(String[] args) {
        System.out.print("Test case 1: ");
        RangeIterator iterator1 = new RangeIterator(0, 10, 2);
        System.out.println(stringifyResult(iterator1)); // Expected: [0, 2, 4, 6, 8]

        System.out.print("Test case 2: ");
        RangeIterator iterator2 = new RangeIterator(10, 0, -2);
        System.out.println(stringifyResult(iterator2)); // Expected: [10, 8, 6, 4, 2]

        System.out.print("Test case 3: ");
        RangeIterator iterator3 = new RangeIterator(5, 6, 1);
        System.out.println(stringifyResult(iterator3)); // Expected: [5]

        System.out.print("Test case 4: ");
        RangeIterator iterator4 = new RangeIterator(5, 2, 1);
        System.out.println(stringifyResult(iterator4)); // Expected: []
    }

    private static String stringifyResult(RangeIterator iterator) {
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result.toString();
    }
}
