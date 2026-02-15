package Jack2026.OKX;

import java.util.*;
public class DesignLeaderboard_1244 {

    private HashMap<Integer, Integer> idToScores = new HashMap<>();
    private TreeMap<Integer, Integer> scoreFrequency = new TreeMap<>((a, b) -> b - a);



    public void addScore(int playerId, int score)
    {
        int oldScore = idToScores.getOrDefault(playerId, 0);

        int newScore = oldScore + score;

        idToScores.put(playerId, newScore);

        if(oldScore > 0)
        {
            int freq = scoreFrequency.get(oldScore);
            if(freq == 1)
            {
                scoreFrequency.remove(oldScore);
            }
            else
            {
                scoreFrequency.put(oldScore, freq - 1);
            }
        }

        scoreFrequency.put(newScore, scoreFrequency.getOrDefault(newScore, 0) + 1);
    }

    public int top(int k)
    {
        int totalSum = 0;
        int remainingPlayer = k;
        for(Map.Entry<Integer, Integer> e : scoreFrequency.entrySet())
        {
            int score = e.getKey();
            int scoreFreq = e.getValue();

            int players = Math.min(scoreFreq, remainingPlayer);

            totalSum += players * score; // KEY

            remainingPlayer -= players;
            if(remainingPlayer == 0) // KEY
            {
                break;
            }
        }

        return totalSum;
    }

    public void reset(int playerId)
    {
        int score = idToScores.remove(playerId);

        int freq = scoreFrequency.get(score);
        if(freq == 1)
        {
            scoreFrequency.remove(score);
        }
        else
        {
            scoreFrequency.put(score, freq - 1);
        }
    }

    public static void main(String[] args)
    {
        DesignLeaderboard_1244 a = new DesignLeaderboard_1244();
        a.addScore(1, 78);
        a.addScore(2, 60);
        a.addScore(3, 84);
        a.addScore(4, 7);
        a.addScore(5, 61);

        System.out.println(a.top(1));
    }
}
