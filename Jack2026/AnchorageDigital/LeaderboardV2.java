package Jack2026.AnchorageDigital;

import java.util.*;
public class LeaderboardV2 {

    private final Map<Integer, Long> idToScore = new HashMap<>();
    private final TreeMap<Long, TreeSet<Integer>> scoreToIds = new TreeMap<>(Comparator.reverseOrder()); // (o1, o2)
    // -> o2.compareTo(o1)

    public LeaderboardV2() {};

    public void updateScore(int id, long delta)
    {
        long oldScore = idToScore.getOrDefault(id, 0L);
        long newScore = oldScore + delta;

        if(idToScore.containsKey(id))
        {
            removeFronBucket(oldScore, id);
        }

//        if(newScore == 0L)
//        {
//            idToScore.remove(id);
//            return;
//        }
        idToScore.put(id, newScore);
        scoreToIds.putIfAbsent(newScore, new TreeSet<>());
        scoreToIds.get(newScore).add(id);
    }

    public void delete(int id)
    {
        Long score = idToScore.get(id);
        if(score == null) return;
        removeFronBucket(score, id);
    }
    
    public List<Integer> topK(int k)
    {
        List<Integer> res = new ArrayList<>();
        if(k <= 0) return res;
        
        for(Map.Entry<Long, TreeSet<Integer>> entry : scoreToIds.entrySet())
        {
            for(int id : entry.getValue())
            {
                res.add(id);
                if(res.size() == k) return res;
            }
        }
        return res;
    }
    
    public int getRank(int id)
    {
        Long score = idToScore.get(id);
        if(score == null) return -1;
        
        int rank = 1;
        
        for(Map.Entry<Long, TreeSet<Integer>> entry : scoreToIds.entrySet())
        {
            long s = entry.getKey();
            TreeSet<Integer> ids = entry.getValue();
            
            if(s > score)
            {
                rank += ids.size();
            } else if (s == score) {
                rank += ids.headSet(id, false).size();
                return rank;
            }
            else {
                break;
            }
        }

        return -1;
    }

    public void removeFronBucket(long score, int id)
    {
        TreeSet<Integer> ids = scoreToIds.get(score);
        if(ids == null) return;
        ids.remove(id);
        if(ids.isEmpty())
        {
            scoreToIds.remove(score);
        }
    }

    public static void main(String[] args) {
        LeaderboardV2 lb = new LeaderboardV2();

        // Updates
        lb.updateScore(1, 50);
        lb.updateScore(2, 70);
        lb.updateScore(3, 50);
        lb.updateScore(4, 20);

        System.out.println("Top2: " + lb.topK(2)); // expect [2, 1]
        System.out.println("Rank(2): " + lb.getRank(2)); // 1
        System.out.println("Rank(1): " + lb.getRank(1)); // 2 (tie 50, id 1 < 3)
        System.out.println("Rank(3): " + lb.getRank(3)); // 3

        System.out.println("Rank(9): " + lb.getRank(9));

        // Update changes ranking
        lb.updateScore(4, 40); // id 4 becomes 60
        System.out.println("Top3 after update: " + lb.topK(3)); // [2, 4, 1]
        System.out.println("Rank(4): " + lb.getRank(4)); // 2

        // Delete
        lb.delete(2);
        System.out.println("Top3 after delete(2): " + lb.topK(3)); // [4, 1, 3]
        System.out.println("Rank(2): " + lb.getRank(2)); // -1
    }
}
