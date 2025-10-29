package Jack2025.Affirm;

import java.util.Deque;
import java.util.LinkedList;

public class DesignHitCounter_362 {
    class HitCounter
    {
        private Deque<Pair<Integer, Integer>> deque;
        private int total;
        public HitCounter()
        {
            this.deque = new LinkedList<>();
            this.total = 0;
        }

        public void hit(int timeStamp)
        {
            if(deque.isEmpty())
            {
                deque.add(new Pair<Integer, Integer>(timeStamp, 1));
            }
            else {
                if(deque.getLast().getKey() == timeStamp)
                {
                    int count = deque.getLast().getValue();
                    deque.removeLast();
                    deque.add(new Pair<Integer, Integer>(timeStamp, count + 1));
                }
                else
                {
                    deque.add(new Pair<Integer, Integer>(timeStamp, 1));
                }
            }
            total++;
        }

        public int getHits(int timeStamp)
        {
            while(!deque.isEmpty() && deque.getFirst().getKey() + 300 <= timeStamp)
            {
                int count = deque.getFirst().getValue();
                deque.removeFirst();
                total -= count;
            }
            return total;
        }
    }

    class Pair<K, V>{
        private final K key;
        private final V value;

        public Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }
    }




//    class HitCounter
//    {
//        private Queue<Integer> queue;
//        public HitCounter(){
//            this.queue = new LinkedList<>();
//        }
//
//        public void hit(int timeStamp)
//        {
//            queue.offer(timeStamp);
//        }
//
//        public int getHits(int timeStamp)
//        {
//            while(!queue.isEmpty() && queue.peek() + 300 <= timeStamp)
//            {
//                queue.poll();
//            }
//
//            return queue.size();
//        }
//    }
}
