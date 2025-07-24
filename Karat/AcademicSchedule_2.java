package Jack2025.Karat;

import java.util.*;

public class AcademicSchedule_2 {

    public static List<String> findMidwayCourses(List<List<String>> allCourses)
    {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Set<String> allNodes = new HashSet<>();

        // Build the graph and calculate in-degree
        for(List<String> edge : allCourses)
        {
            String source = edge.get(0);
            String dest = edge.get(1);

            graph.computeIfAbsent(source, k -> new ArrayList<>()).add(dest);
            inDegree.put(dest, inDegree.getOrDefault(dest, 0) + 1);
            allNodes.add(source);
            allNodes.add(dest);
        }

        // Find all root nodes (nodes with in-degree 0)
        List<String> roots = new ArrayList<>();
        for(String node : allNodes)
        {
            if(!inDegree.containsKey(node))
            {
                roots.add(node);
            }
        }

        List<String> result = new ArrayList<>();
        for(String root : roots)
        {
            List<List<String>> paths = new ArrayList<>();
            dfs(root, graph, new ArrayList<>(), paths);
            for(List<String> path : paths)
            {
                int mid = (path.size() - 1) / 2;
                String course = path.get(mid);
                if(!result.contains(course))
                {
                    result.add(course);
                }
            }
        }
        return result;
    }

    private static void dfs(String node, Map<String, List<String>> graph, List<String> currentPath, List<List<String>> paths)
    {
        currentPath.add(node);
        if(!graph.containsKey(node) || graph.get(node).isEmpty())
        {
            paths.add(new ArrayList<>(currentPath));
        }
        else {
            for(String neighbor : graph.get(node))
            {
                dfs(neighbor, graph, currentPath, paths);
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }

    public static void main(String[] args) {
        List<List<String>> allCourses1 = Arrays.asList(
                Arrays.asList("Logic", "COBOL"),
                Arrays.asList("Data Structures", "Algorithms"),
                Arrays.asList("Creative Writing", "Data Structures"),
                Arrays.asList("Algorithms", "COBOL"),
                Arrays.asList("Intro to Computer Science", "Data Structures"),
                Arrays.asList("Logic", "Compilers"),
                Arrays.asList("Data Structures", "Logic"),
                Arrays.asList("Graphics", "Networking"),
                Arrays.asList("Networking", "Algorithms"),
                Arrays.asList("Creative Writing", "System Administration"),
                Arrays.asList("Databases", "System Administration"),
                Arrays.asList("Creative Writing", "Databases"),
                Arrays.asList("Intro to Computer Science", "Graphics")
        );

        System.out.println(findMidwayCourses(allCourses1)); // ["Data Structures", "Networking", "Creative Writing", "Databases"]
    }
}
