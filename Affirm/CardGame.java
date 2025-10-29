package Jack2025.Affirm;
import java.util.*;
public class CardGame {
    private List<String> players;
    private int totalCards;
    private Map<String, List<Integer>> hands;
    private Map<String, Integer> scores;

    public CardGame(List<String> players, int totalCards)
    {
        this.players = players;
        this.totalCards = totalCards;
        this.hands = new HashMap<>();
        this.scores = new HashMap<>();

        for(String player : players)
        {
            scores.put(player, 0);
        }
        dealCards();
    }

    // Shuffle cards and deal the card evenly
    private void dealCards()
    {
        List<Integer> deck = new ArrayList<>();
        for(int i = 0; i < totalCards; i++)
        {
            deck.add(i);
        }
        Collections.shuffle(deck);

        int n = players.size();
        int cardsPerPlayer = totalCards / n;
        int userCards = cardsPerPlayer * n;

        // Deal cards to each player
        for(int i = 0; i < n; i++)
        {
            // Sublist represents the hand of this player
            List<Integer> hand = new ArrayList<>(deck.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer));
            hands.put(players.get(i), hand);
        }

        // If there are leftover cards, discard them
        if(userCards < totalCards)
        {
            System.out.println("Warn: " + (totalCards - userCards) + " cards are discarded");
        }
    }

    public void playGame()
    {
        int n = players.size();
        int cardsPerPlayer = totalCards / n;

        for(int round = 0; round < cardsPerPlayer; round++)
        {
            System.out.println("Round " + (round + 1) + ": ");

            String winner = null;
            int maxCard = -1;

            // Each player plays one card from their hand
            for(String player : players)
            {
                int card = hands.get(player).get(round);
                System.out.println(player + " -> " + card + " ");

                if(card > maxCard)
                {
                    maxCard = card;
                    winner = player;
                }
            }

            scores.put(winner, scores.get(winner) + n);
            System.out.println("=> Winner: " + winner + " (+" + n + ")");
        }
    }

    public void printResults()
    {
        System.out.println("\nFinal Scores:");
        String winner = null;
        int maxScore = -1;

        // Find the player with the highest score
        for(String player : players)
        {
            int score = scores.get(player);
            System.out.println(player + ": " + score);
            if(score > maxScore)
            {
                maxScore = score;
                winner = player;
            }
        }
        System.out.println("\n Winner: " + winner + " with " + maxScore + " points!");
    }

    public Map<String, Integer> getScores(){
        return scores;
    }
}
