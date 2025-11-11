package Jack2025.Oscilar;
import java.util.*;

public class DesignHashMap_706 {
    class Pair<K, V>{
        public K key;
        public V value;

        public Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }

    class Bucket {
        private List<Pair<Integer, Integer>> bucket;

        public Bucket(){
            this.bucket = new LinkedList<>();
        }

        public Integer get(Integer key)
        {
            for(Pair<Integer, Integer> pair : this.bucket)
            {
                if(pair.key.equals(key))
                {
                    return pair.value;
                }
            }
            return -1;
        }

        public void update(Integer key, Integer value)
        {
            boolean found = false;
            for(Pair<Integer, Integer> pair : this.bucket)
            {
                if(pair.key.equals(key))
                {
                    pair.value = value;
                    found = true;
                }
            }

            if(!found)
            {
                this.bucket.add(new Pair<>(key, value));
            }

        }

        public void remove(Integer key)
        {
            for(Pair<Integer, Integer> pair : this.bucket)
            {
                if(pair.key.equals(key))
                {
                    this.bucket.remove(pair);
                    break;
                }
            }
        }
    }

    private int mod;
    private List<Bucket> buckets;

    public DesignHashMap_706(){
        this.mod = 2069; // A prime number, but not too big or small
        this.buckets = new ArrayList<>();

        for(int i = 0; i < this.mod; i++)
        {
            this.buckets.add(new Bucket());
        }
    }

    public void put(int key, int value)
    {
        int hashKey = key % this.mod;
        this.buckets.get(hashKey).update(key, value);
    }

    public int get(int key){
        int hashKey = key % this.mod;
        return this.buckets.get(hashKey).get(key);
    }

    public void remove(int key)
    {
        int hashKey = key % this.mod;
        this.buckets.get(hashKey).remove(key);
    }
}
