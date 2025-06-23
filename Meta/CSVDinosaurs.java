package Jack2025.Meta;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Question 1: You will be supplied with two data files in CSV format . The first file contains statistics about various dinosaurs. The second file contains additional data. Given the following formula, speed = ((STRIDE_LENGTH / LEG_LENGTH) - 1) * SQRT(LEG_LENGTH * g) Where g = 9.8 m/s^2 (gravitational constant)
//
//        Write a program to read in the data files from disk, it must then print the names of only the bipedal dinosaurs from fastest to slowest. Do not print any other information.
public class CSVDinosaurs {
    static final double g = 9.8;

    public static void main(String[] args)
    {
        String path1 = "src/Jack2025/Meta/dataset1.csv";
        String path2 = "src/Jack2025/Meta/dataset2.csv";
        printBipedalDinosaursBySpeed(path1, path2);
    }

    public static void printBipedalDinosaursBySpeed(String path1, String path2)
    {
        // 1. Parse stride length for bipedal dinosaurs from dataset2
        Map<String, Double> strideMap = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path2)))
        {
            String line = br.readLine(); // skip header
            while((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if(parts.length < 3)
                    continue;

                String name = parts[0].trim();

                double strideLength = Double.parseDouble(parts[1].trim());
                String stance = parts[2].trim();
                if(stance.equals("bipedal"))
                {
                    strideMap.put(name, strideLength);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<DinoSpeed> dinoSpeeds = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path1)))
        {
            String line = br.readLine(); // skip header
            while((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if(parts.length < 3)
                    continue;

                String name = parts[0].trim();
                double legLength = Double.parseDouble(parts[1].trim());

                if(strideMap.containsKey(name))
                {
                    double strideLength = strideMap.get(name);
                    double speed = Math.abs((strideLength / legLength - 1) * Math.sqrt(legLength * g));
                    dinoSpeeds.add(new DinoSpeed(name, speed));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(dinoSpeeds, (a, b) -> Double.compare(b.speed, a.speed));
        for(DinoSpeed ds : dinoSpeeds)
        {
            System.out.println(ds.name);
        }
    }

    static class DinoSpeed{
        String name;
        double speed;
        DinoSpeed(String name, double speed)
        {
            this.name = name;
            this.speed = speed;
        }
    }
}
