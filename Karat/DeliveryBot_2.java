package Jack2025.Karat;
import java.util.*;
public class DeliveryBot_2 {
    public static List<String> findAssemblableRobots(List<String> allParts, List<List<Object>> requiredParts) {
        List<String> assemblableRobots = new ArrayList<>();
        Set<String> availableParts = new HashSet<>(allParts);

        for (List<Object> robotParts : requiredParts) {
            String robotName = (String) robotParts.get(0);
            List<String> partsNeeded = (List<String>) robotParts.get(1);

            boolean canAssemble = true;
            for (String part : partsNeeded) {
                if (!availableParts.contains(part)) {
                    canAssemble = false;
                    break;
                }
            }

            if (canAssemble) {
                assemblableRobots.add(robotName);
            }
        }

        return assemblableRobots;
    }

    public static void main(String[] args) {
        // Example usage
        List<String> allParts = Arrays.asList("arm", "leg", "head", "body", "wheel", "sensor");

        List<List<Object>> requiredParts = Arrays.asList(
                Arrays.asList("RobotA", Arrays.asList("arm", "leg", "head")),
                Arrays.asList("RobotB", Arrays.asList("body", "wheel")),
                Arrays.asList("RobotC", Arrays.asList("head", "sensor", "camera")),
                Arrays.asList("RobotD", Arrays.asList("wheel", "body", "sensor"))
        );

        List<String> result = findAssemblableRobots(allParts, requiredParts);
        System.out.println(result); // Output: [RobotA, RobotB, RobotD]
    }
}
