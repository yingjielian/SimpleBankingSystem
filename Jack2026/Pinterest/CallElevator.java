package Jack2026.Pinterest;

class CallEvevator {
    public int callElevator(String[][] elevators, String[] passenger) {
        int passengerFloor = parseFloor(passenger[0]);
        String passengerDir = passenger[1];

        int bestIdx = -1;
        int bestDist = Integer.MAX_VALUE;

        for (int i = 0; i < elevators.length; i++) {
            int elevFloor = parseFloor(elevators[i][0]);
            String state = elevators[i][1];

            if (!isEligible(elevFloor, state, passengerFloor, passengerDir)) {
                continue;
            }

            int dist = Math.abs(elevFloor - passengerFloor);
            if (dist < bestDist || (dist == bestDist && i < bestIdx)) {
                bestDist = dist;
                bestIdx = i;
            }
        }

        return bestIdx;
    }

    private boolean isEligible(int elevFloor, String state, int passengerFloor, String passengerDir) {
        if ("idle".equals(state)) {
            return true;
        }

        // Moving elevator: direction must match, and passenger must be ahead in that direction.
        if (!state.equals(passengerDir)) {
            return false;
        }

        if ("up".equals(state)) {
            return passengerFloor >= elevFloor;
        } else { // "down"
            return passengerFloor <= elevFloor;
        }
    }

    private int parseFloor(String floorStr) {
        // Floors are "1".."1000" per problem statement
        return Integer.parseInt(floorStr);
    }
}

