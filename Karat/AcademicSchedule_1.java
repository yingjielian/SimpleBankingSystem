package Jack2025.Karat;

import java.util.*;

/*You are a developer for a university. Your current project is to develop a system for students to find courses
        they share with friends. The university has a system for querying courses students are enrolled in, returned
        as a list of (ID, course) pairs.
        Write a function that takes in a collection of (student ID number, course name) pairs and returns, for every
        pair of students, a collection of all courses they share.
        Sample Input:
enrollments1 = [
["58", "Linear Algebra"],
["94", "Art History"],
["94", "Operating Systems"],
["17", "Software Design"],
["58", "Mechanics"],
["58", "Economics"],
["17", "Linear Algebra"],
["17", "Political Science"],
["94", "Economics"],
["25", "Economics"],
["58", "Software Design"],
]
Sample Output (pseudocode, in any order):
find_pairs(enrollments1) =>
{
"58,17": ["Software Design", "Linear Algebra"]
"58,94": ["Economics"]
"58,25": ["Economics"]
"94,25": ["Economics"]
"17,94": []
"17,25": []
}*/
public class AcademicSchedule_1 {

    public static Map<String, List<String>> findSharedCourses(List<String[]> enrollments)
    {
        // First, create a map from studentID to their courses
        Map<String, Set<String>> studentCourses = new HashMap<>();

        for(String[] enrollment : enrollments)
        {
            String studentID = enrollment[0];
            String course = enrollment[1];

            studentCourses.putIfAbsent(studentID, new HashSet<>());
            studentCourses.get(studentID).add(course);
        }

        // Get all student IDs and sort them to ensure consistent pair ordering
        List<String> studentIDs = new ArrayList<>(studentCourses.keySet());
//        Collections.sort(studentIDs);

        Map<String, List<String>> result = new HashMap<>();

        // Compare every pair of students
        for(int i = 0; i < studentIDs.size(); i++)
        {
            for(int j = i + 1; j < studentIDs.size(); j++)
            {
                String student1 = studentIDs.get(i);
                String student2 = studentIDs.get(j);

                Set<String> course1 = studentCourses.get(student1);
                Set<String> course2 = studentCourses.get(student2);

                List<String> sharedCourse = new ArrayList<>();
                for(String course : course1)
                {
                    if(course2.contains(course))
                    {
                        sharedCourse.add(course);
                    }
                }
                // Sort the shared courses for consistant output
//                Collections.sort(sharedCourse);

                // Create the pair key
                String pairKey = student1 + "," + student2;
                result.put(pairKey, sharedCourse);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Test case 1
        List<String[]> enrollments1 = Arrays.asList(
                new String[]{"58", "Linear Algebra"},
                new String[]{"94", "Art History"},
                new String[]{"94", "Operating Systems"},
                new String[]{"17", "Software Design"},
                new String[]{"58", "Mechanics"},
                new String[]{"58", "Economics"},
                new String[]{"17", "Linear Algebra"},
                new String[]{"17", "Political Science"},
                new String[]{"94", "Economics"},
                new String[]{"25", "Economics"},
                new String[]{"58", "Software Design"}
        );

        System.out.println(findSharedCourses(enrollments1));
    }

}
