package Jack2026.Pinterest;

import java.util.*;

class PinBoardConnectivity {
    public boolean areRelated(List<List<Integer>> boards, int pin1, int pin2) {
        if (pin1 == pin2) return true;
        if (boards == null || boards.isEmpty()) return false;

        // pin -> list of board indices containing it
        Map<Integer, List<Integer>> pinToBoards = new HashMap<>();
        for (int i = 0; i < boards.size(); i++) {
            List<Integer> board = boards.get(i);
            if (board == null) continue;
            for (int pin : board) {
                pinToBoards.computeIfAbsent(pin, k -> new ArrayList<>()).add(i);
            }
        }

        // If either pin doesn't appear anywhere, they can't be related
        if (!pinToBoards.containsKey(pin1) || !pinToBoards.containsKey(pin2)) return false;

        Deque<Integer> q = new ArrayDeque<>();
        Set<Integer> visitedPins = new HashSet<>();
        boolean[] visitedBoards = new boolean[boards.size()];

        q.offer(pin1);
        visitedPins.add(pin1);

        while (!q.isEmpty()) {
            int curPin = q.poll();
            if (curPin == pin2) return true;

            List<Integer> boardIdxs = pinToBoards.get(curPin);
            if (boardIdxs == null) continue;

            for (int bIdx : boardIdxs) {
                if (visitedBoards[bIdx]) continue;
                visitedBoards[bIdx] = true;

                List<Integer> boardPins = boards.get(bIdx);
                if (boardPins == null) continue;

                for (int nextPin : boardPins) {
                    if (visitedPins.add(nextPin)) {
                        if (nextPin == pin2) return true;
                        q.offer(nextPin);
                    }
                }
            }
        }

        return false;
    }

    public int minBoardsToConnect(List<List<Integer>> boards, int pin1, int pin2) {
        if (pin1 == pin2) return 0;
        if (boards == null || boards.isEmpty()) return -1;

        // pin -> list of board indices containing it
        Map<Integer, List<Integer>> pinToBoards = new HashMap<>();
        for (int i = 0; i < boards.size(); i++) {
            List<Integer> b = boards.get(i);
            if (b == null) continue;
            for (int pin : b) {
                pinToBoards.computeIfAbsent(pin, k -> new ArrayList<>()).add(i);
            }
        }

        List<Integer> startBoards = pinToBoards.get(pin1);
        List<Integer> targetBoards = pinToBoards.get(pin2);
        if (startBoards == null || targetBoards == null) return -1;

        // Quick check: same board => 0 transitions
        // (optional optimization)
        Set<Integer> targetSet = new HashSet<>(targetBoards);
        for (int sb : startBoards) {
            if (targetSet.contains(sb)) return 0;
        }

        boolean[] visited = new boolean[boards.size()];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int[] dist = new int[boards.size()];

        for (int sb : startBoards) {
            visited[sb] = true;
            dist[sb] = 0;
            q.offer(sb);
        }

        while (!q.isEmpty()) {
            int curBoard = q.poll();
            int d = dist[curBoard];

            // Expand neighbors via shared pins
            for (int pin : boards.get(curBoard)) {
                List<Integer> nextBoards = pinToBoards.get(pin);
                if (nextBoards == null) continue;

                for (int nb : nextBoards) {
                    if (visited[nb]) continue;
                    visited[nb] = true;
                    dist[nb] = d + 1;

                    if (targetSet.contains(nb)) return dist[nb];
                    q.offer(nb);
                }
            }
        }

        return -1;
    }
}

