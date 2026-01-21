package Jack2026.Coinbase;

import java.util.*;

class InterleaveIterator {
    private Queue<Iterator<Integer>> queue;
    private int[][] originalArr;
    private boolean isCycled;

    public InterleaveIterator(int[][] arr, boolean isCycled) {
        this.originalArr = arr;
        this.isCycled = isCycled;
        this.queue = new LinkedList<>();
        initializeQueue();
    }

    /**
     * 将所有非空的子数组迭代器加入队列
     */
    private void initializeQueue() {
        for (int[] subArr : originalArr) {
            if (subArr.length > 0) {
                List<Integer> list = new ArrayList<>();
                for (int val : subArr) list.add(val);
                queue.add(list.iterator());
            }
        }
    }

    public boolean hasNext() {
        // 如果是循环模式，永远有下一个（除非输入全是空数组）
        if (isCycled) {
            return originalArr.length > 0 && hasAnyElements(originalArr);
        }
        return !queue.isEmpty();
    }

    private boolean hasAnyElements(int[][] arr) {
        for (int[] a : arr) if (a.length > 0) return true;
        return false;
    }

    public int next() {
        if (!hasNext()) throw new NoSuchElementException();

        // 如果开启了循环模式且当前队列耗尽，重新初始化队列
        if (isCycled && queue.isEmpty()) {
            initializeQueue();
        }

        Iterator<Integer> currIter = queue.poll();
        int result = currIter.next();

        // 如果当前迭代器还有剩余元素，放回队尾实现 Round-robin
        if (currIter.hasNext()) {
            queue.add(currIter);
        }

        return result;
    }
}

class Solution_3 {
    public static void main(String[] args) {
        System.out.print("Test case 1: ");
        int[][] arr1 = {{1, 2}, {3}, {4}};
        InterleaveIterator iterator1 = new InterleaveIterator(arr1, true);
        System.out.println(stringifyResult(iterator1, 10));

        System.out.print("Test case 2: ");
        int[][] arr2 = {{1, 2}, {3, 4}, {5, 6}};
        InterleaveIterator iterator2 = new InterleaveIterator(arr2, false);
        System.out.println(stringifyResult(iterator2));

        System.out.print("Test case 3: ");
        int[][] arr3 = {{1, 2}, {3}, {4, 5, 6, 7}};
        InterleaveIterator iterator3 = new InterleaveIterator(arr3, true);
        System.out.println(stringifyResult(iterator3, 12));
    }

    private static String stringifyResult(InterleaveIterator iterator) {
        List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            // 注意：题目要求这里打印 hasNext() 的状态
            result.add("true");
            result.add(iterator.next() + "");
        }
        result.add("false");
        return result.toString();
    }

    private static String stringifyResult(InterleaveIterator iterator, int times) {
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext() && times > 0) {
            result.add(iterator.next());
            times--;
        }
        return result.toString();
    }
}
