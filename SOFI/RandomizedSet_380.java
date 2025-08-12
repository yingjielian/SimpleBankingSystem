package Jack2025.SOFI;
import java.util.*;
public class RandomizedSet_380 {
    List<Integer> list;
    HashMap<Integer, Integer> map;
    Random rand;

    // RandomizedSet() Initializes the RandomizedSet object.
    public RandomizedSet_380() {
        list = new ArrayList<>();
        map = new HashMap<>();
        rand = new Random();
    }

    // bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not
    // present,  false otherwise.
    public boolean insert(int val) {
        if(map.containsKey(val)) {
            return false;
        }

        map.put(val, list.size());
        list.add(val);
        return true;
    }

    // bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false
    // otherwise.
    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;

        int last = list.get(list.size() - 1);
        int idx = map.get(val);
        list.set(idx, last);
        map.put(last, idx);

        list.remove(list.size() - 1); // pop the last element, O(1)
        map.remove(val);
        return true;
    }

    // int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one
    // element exists when this method is called). Each element must have the same probability of being returned.
    public int getRandom() {
        int n = rand.nextInt(list.size());
        return list.get(n);
    }
}
