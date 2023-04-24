import java.util.Scanner;
import java.util.Random;

public class Day01Randoms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("How many random integers do you want to generate?");
        int numRandoms;
        try {
            numRandoms = Integer.parseInt(scanner.nextLine());
            if (numRandoms < 0) {
                System.err.println("Error: Number of random integers must be non-negative.");
                scanner.close();
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid integer.");
            scanner.close();
            return;
        }

        System.out.println("Enter minimum:");
        int minValue;
        try {
            minValue = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid integer.");
            scanner.close();
            return;
        }

        System.out.println("Enter maximum:");
        int maxValue;
        try {
            maxValue = Integer.parseInt(scanner.nextLine());
            if (minValue > maxValue) {
                System.err.println("Error: Minimum value must be smaller or equal to maximum value.");
                scanner.close();
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid integer.");
            scanner.close();
            return;
        }

        System.out.print("Result: ");
        for (int i = 0; i < numRandoms; i++) {
            int randomValue = minValue + random.nextInt(maxValue - minValue + 1);
            System.out.print(randomValue);
            if (i < numRandoms - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        scanner.close();
    }
}
