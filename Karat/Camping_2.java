package Jack2025.Karat;
import java.util.*;
public class Camping_2 {

    public static List<List<String>> carpool(String[][] roads, String[] starts, String[][] people) {
        // Build the graph: map from origin to list of [destination, duration]
        Map<String, List<String[]>> graph = new HashMap<>();
        for (String[] road : roads) {
            String origin = road[0];
            String destination = road[1];
            String duration = road[2];
            graph.putIfAbsent(origin, new ArrayList<>());
            graph.get(origin).add(new String[]{destination, duration});
        }

        // Precompute the travel times from each start to all reachable locations
        Map<String, Integer> start1Times = computeTravelTimes(graph, starts[0]);
        Map<String, Integer> start2Times = computeTravelTimes(graph, starts[1]);

        // Assign people to cars based on travel times
        List<String> car1 = new ArrayList<>();
        List<String> car2 = new ArrayList<>();

        for (String[] person : people) {
            String name = person[0];
            String location = person[1];
            Integer time1 = start1Times.get(location);
            Integer time2 = start2Times.get(location);

            if (time1 == null && time2 == null) {
                // Person's location is unreachable by both cars (shouldn't happen per problem statement)
                continue;
            } else if (time1 == null) {
                car2.add(name);
            } else if (time2 == null) {
                car1.add(name);
            } else {
                if (time1 < time2) {
                    car1.add(name);
                } else if (time2 < time1) {
                    car2.add(name);
                } else {
                    // Equal times, can go in either car; here we alternate for fairness
                    if (car1.size() <= car2.size()) {
                        car1.add(name);
                    } else {
                        car2.add(name);
                    }
                }
            }
        }

        List<List<String>> result = new ArrayList<>();
        result.add(car1);
        result.add(car2);
        return result;
    }

    private static Map<String, Integer> computeTravelTimes(Map<String, List<String[]>> graph, String start) {
        Map<String, Integer> travelTimes = new HashMap<>();
        Queue<Object[]> queue = new LinkedList<>();
        queue.add(new Object[]{start, 0});

        while (!queue.isEmpty()) {
            Object[] current = queue.poll();
            String location = (String) current[0];
            int time = (Integer) current[1];

            if (travelTimes.containsKey(location)) {
                continue;
            }

            travelTimes.put(location, time);

            if (graph.containsKey(location)) {
                for (String[] edge : graph.get(location)) {
                    String nextLocation = edge[0];
                    int duration = Integer.parseInt(edge[1]);
                    queue.add(new Object[]{nextLocation, time + duration});
                }
            }
        }

        return travelTimes;
    }

    public static void main(String[] args) {
        String[][] roads1 = {
                {"Bridgewater", "Caledonia", "30"},
                {"Caledonia", "New Grafton", "15"},
                {"New Grafton", "Campground", "5"},
                {"Liverpool", "Milton", "10"},
                {"Milton", "New Grafton", "30"}
        };
        String[] starts1 = {"Bridgewater", "Liverpool"};
        String[][] people1 = {
                {"Jessie", "Bridgewater"}, {"Travis", "Caledonia"},
                {"Jeremy", "New Grafton"}, {"Katie", "Liverpool"}
        };
        System.out.println(carpool(roads1, starts1, people1)); // [[Jessie, Travis], [Katie, Jeremy]]

        String[][] roads2 = {
                {"Riverport", "Chester", "40"},
                {"Chester", "Campground", "60"},
                {"Halifax", "Chester", "30"}
        };
        String[] starts2 = {"Riverport", "Halifax"};
        String[][] people2 = {
                {"Colin", "Riverport"}, {"Sam", "Chester"}, {"Alyssa", "Halifax"}
        };
        System.out.println(carpool(roads2, starts2, people2)); // [[Colin, Sam], [Alyssa]] or [[Colin], [Alyssa, Sam]]

        String[][] roads3 = {
                {"Riverport", "Bridgewater", "1"},
                {"Bridgewater", "Liverpool", "1"},
                {"Liverpool", "Campground", "1"}
        };
        String[] starts3_1 = {"Riverport", "Bridgewater"};
        String[][] people3 = {
                {"Colin", "Riverport"}, {"Jessie", "Bridgewater"}, {"Sam", "Liverpool"}
        };
        System.out.println(carpool(roads3, starts3_1, people3)); // [[Colin], [Jessie, Sam]]
    }
}
