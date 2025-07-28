package Jack2025.Karat;
import java.util.*;
public class AcademicSchedule_3 {
    public static String halfwayCourse(List<List<String>> pairs) {
        if (pairs.isEmpty()) {
            return "";
        }

        // Build a map from course to its next course
        Map<String, String> nextCourseMap = new HashMap<>();
        Set<String> allCourses = new HashSet<>();
        Set<String> hasPrerequisite = new HashSet<>();

        for (List<String> pair : pairs) {
            String prerequisite = pair.get(0);
            String course = pair.get(1);
            nextCourseMap.put(prerequisite, course);
            allCourses.add(prerequisite);
            allCourses.add(course);
            hasPrerequisite.add(course);
        }

        // Find the starting course (the one not in hasPrerequisite)
        String start = null;
        for (String course : allCourses) {
            if (!hasPrerequisite.contains(course)) {
                start = course;
                break;
            }
        }

        if (start == null) {
            return ""; // should not happen as per problem statement
        }

        // Build the full course sequence
        List<String> courseSequence = new ArrayList<>();
        String current = start;
        while (current != null) {
            courseSequence.add(current);
            current = nextCourseMap.get(current);
        }

        int midIndex = (courseSequence.size() - 1) / 2;
        return courseSequence.get(midIndex);
    }

    public static void main(String[] args) {
        List<List<String>> pairs1 = Arrays.asList(
                Arrays.asList("Foundations of Computer Science", "Operating Systems"),
                Arrays.asList("Data Structures", "Algorithms"),
                Arrays.asList("Computer Networks", "Computer Architecture"),
                Arrays.asList("Algorithms", "Foundations of Computer Science"),
                Arrays.asList("Computer Architecture", "Data Structures"),
                Arrays.asList("Software Design", "Computer Networks")
        );
        System.out.println(halfwayCourse(pairs1)); // Output: "Data Structures"

        List<List<String>> pairs2 = Arrays.asList(
                Arrays.asList("Algorithms", "Foundations of Computer Science"),
                Arrays.asList("Data Structures", "Algorithms"),
                Arrays.asList("Foundations of Computer Science", "Logic"),
                Arrays.asList("Logic", "Compilers"),
                Arrays.asList("Compilers", "Distributed Systems")
        );
        System.out.println(halfwayCourse(pairs2)); // Output: "Foundations of Computer Science"

        List<List<String>> pairs3 = Arrays.asList(
                Arrays.asList("Data Structures", "Algorithms")
        );
        System.out.println(halfwayCourse(pairs3)); // Output: "Data Structures"
    }
}
