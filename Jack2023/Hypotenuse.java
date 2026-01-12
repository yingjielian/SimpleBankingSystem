package Jack2023;

import java.util.Scanner;

public class Hypotenuse
{
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        // Declare the needed variables, make them 'double' type because
        // we are dealing with decimals math.
        double side1, side2, hypotenuse;

        // Use Scanner to get input from the user.
        Scanner in =  new Scanner(System.in);

        // Prompt the user to enter one side of the triangle.
        System.out.println("Enter the length of one side of the triangle: ");
        side1 = in.nextDouble();

        // Prompt the user to enter another side of the triangle.
        System.out.println("Enter the length of another side of the triangle: ");
        side2 = in.nextDouble();

        // Compute the result which is the hypotenuse of the triangle.
        hypotenuse = Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2));

        // Finally, print the results to the console window.
        System.out.println("The hypotenuse of the triangle is " + hypotenuse);
    }
}