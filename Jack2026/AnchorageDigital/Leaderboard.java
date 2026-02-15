package Jack2026.AnchorageDigital;

import java.util.*;

public class Leaderboard {
    // Map to store each player's total score (playerId -> totalScore)
    private Map<Integer, Integer> playerScores = new HashMap<>();
    // TreeMap to store score frequencies in descending order (score -> count)
    // Using custom comparator to sort scores from highest to lowest
    private TreeMap<Integer, Integer> scoreFrequency = new TreeMap<>((a, b) -> b - a);

    // Empty constructor - data structures already initialized
    public Leaderboard()
    {

    }

    public void addScore(int playerId, int score)
    {
        // Get the player's current total score (0 if new player)
        int oldScore = playerScores.getOrDefault(playerId, 0);
        // Calculate the new total score for this player
        int newScore = oldScore + score;

        // Update the player's total score in the map
        playerScores.put(playerId, newScore);

        // If player had a previous score, decrease its frequency count
        if(oldScore > 0) {
            // Decrease the frequency of the old score
            int oldFrequency = scoreFrequency.get(oldScore);
            if (oldScore == 1)
            {
                // Remove the score entirely if no other player has it
                scoreFrequency.remove(oldScore);
            }
            else {
                // Otherwise just decrease the count
                scoreFrequency.put(oldScore, oldFrequency - 1);
            }
        }

        // Increase the frequency count for the new score
        scoreFrequency.put(newScore, scoreFrequency.getOrDefault(newScore, 0) + 1);
    }

    public int top(int k)
    {
        int totalSum = 0;
        int remainingPlayers = k;

        // Iterate through scores in descending order
        for(Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet())
        {
            int score = entry.getKey();
            int playerCount = entry.getValue();

            // Take minimum of available players with this score and remaining slots
            int playersToInclude = Math.min(playerCount, remainingPlayers);

            // Add their contribution to the total
            totalSum += score * playersToInclude;

            // Decrease the remaining slots
            remainingPlayers -= playersToInclude;

            // Stop if we've included K players
            if(remainingPlayers == 0)
            {
                break;
            }
        }

        return totalSum;
    }

    public void reset(int playerId)
    {
        Integer score = playerScores.remove(playerId);

        if(score == null) return;

        Integer frequency = scoreFrequency.get(score);
        if(frequency == 1)
        {
            scoreFrequency.remove(score);
        }
        else
        {
            scoreFrequency.put(score, frequency - 1);
        }
    }

    public static void main(String[] args)
    {
        Leaderboard lb = new Leaderboard();

        lb.addScore(1, 73);
        lb.addScore(2, 56);
        lb.addScore(3, 39);
        lb.addScore(4, 51);
        lb.addScore(5, 4);

        System.out.println(lb.top(1)); // 73
        System.out.println(lb.top(3)); // 73 + 56 + 51 = 180
    }

}
