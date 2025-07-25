package Jack2025.Karat;
import java.util.*;
public class Camping_1 {
    public static int shopping(String[][] products, String[] list) {
        // Create a map from product name to department
        Map<String, String> productToDept = new HashMap<>();
        for (String[] product : products) {
            productToDept.put(product[0], product[1]);
        }

        // Calculate original department visits
        if (list.length == 0) {
            return 0;
        }

        int originalVisits = 1;
        String currentDept = productToDept.get(list[0]);
        for (int i = 1; i < list.length; i++) {
            String dept = productToDept.get(list[i]);
            if (!dept.equals(currentDept)) {
                originalVisits++;
                currentDept = dept;
            }
        }

        // Calculate optimized visits: number of unique departments in the list
        Set<String> uniqueDepts = new HashSet<>();
        for (String product : list) {
            uniqueDepts.add(productToDept.get(product));
        }
        int optimizedVisits = uniqueDepts.size();

        return originalVisits - optimizedVisits;
    }

    public static void main(String[] args) {
        String[][] products = {
                {"Cheese", "Dairy"},
                {"Carrots", "Produce"},
                {"Potatoes", "Produce"},
                {"Canned Tuna", "Pantry"},
                {"Romaine Lettuce", "Produce"},
                {"Chocolate Milk", "Dairy"},
                {"Flour", "Pantry"},
                {"Iceberg Lettuce", "Produce"},
                {"Coffee", "Pantry"},
                {"Pasta", "Pantry"},
                {"Milk", "Dairy"},
                {"Blueberries", "Produce"},
                {"Pasta Sauce", "Pantry"}
        };

        String[] list1 = {"Blueberries", "Milk", "Coffee", "Flour", "Cheese", "Carrots"};
        System.out.println(shopping(products, list1)); // 2

        String[] list2 = {"Blueberries", "Carrots", "Coffee", "Milk", "Flour", "Cheese"};
        System.out.println(shopping(products, list2)); // 2

        String[] list3 = {"Blueberries", "Carrots", "Romaine Lettuce", "Iceberg Lettuce"};
        System.out.println(shopping(products, list3)); // 0

        String[] list4 = {"Milk", "Flour", "Chocolate Milk", "Pasta Sauce"};
        System.out.println(shopping(products, list4)); // 2

        String[] list5 = {"Cheese", "Potatoes", "Blueberries", "Canned Tuna"};
        System.out.println(shopping(products, list5)); // 0
    }
}
