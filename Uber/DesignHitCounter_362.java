package Jack2025.Uber;
import java.util.*;
//import javafx.util.*;
public class DesignHitCounter_362 {

    class HitCounter {
        Queue<Integer> queue;
        public HitCounter() {
            queue = new LinkedList<>();
        }

        public void hit(int timestamp) {
            queue.offer(timestamp);
        }

        public int getHits(int timestamp) {
            try{
                int i = 0;
            }
            catch (Exception e)
            {
                System.err.println();
                e.printStackTrace();
            }
            while(!queue.isEmpty() && queue.peek() + 300 <= timestamp) {
                queue.poll();
            }
            return queue.size();
        }
    }

//    class HitCounter {
//        Deque<Pair<Integer, Integer>> deque;
//        int total;
//        public HitCounter() {
//            deque = new LinkedList<>();
//            total = 0;
//        }
//
//        public void hit(int timestamp) {
//            if(deque.isEmpty()) {
//                deque.add(new Pair<Integer, Integer>(timestamp, 1));
//            } else {
//                if(deque.getLast().getKey() == timestamp) {
//                    int count = deque.getLast().getValue();
//                    deque.removeLast();
//                    deque.add(new Pair<Integer, Integer>(timestamp, count + 1));
//                } else {
//                    deque.add(new Pair<Integer, Integer>(timestamp, 1));
//                }
//            }
//
//            total++;
//        }
//
//        public int getHits(int timestamp) {
//            while(!deque.isEmpty() && deque.getFirst().getKey() + 300 <= timestamp) {
//                int count = deque.getFirst().getValue();
//                deque.removeFirst();
//                total -= count;
//            }
//
//            return total;
//        }
//    }
}
