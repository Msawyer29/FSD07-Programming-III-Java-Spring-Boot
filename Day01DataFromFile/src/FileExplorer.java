import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FileExplorer {
    static ArrayList<String> namesList = new ArrayList<>();
    static ArrayList<Double> numsList = new ArrayList<>();

    public static void main(String[] args) {
        readFile("input.txt");
        processLists();
    }

    public static void readFile(String fileName) {
        try (InputStream is = FileExplorer.class.getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    double value = Double.parseDouble(line);
                    numsList.add(value);
                } catch (NumberFormatException e) {
                    namesList.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read the file.");
        }
    }

    public static void processLists() {
        // 1. Sort names alphabetically
        Collections.sort(namesList);

        // 2. Sort numbers from smallest to largest
        Collections.sort(numsList);

        // 3. Display sorted names on a single line, comma-separated
        System.out.println("Sorted names: " + String.join(", ", namesList));

        // 4. Display sorted numbers on a single line, comma-separated
        System.out.println("Sorted numbers: " + numsList.stream().map(String::valueOf).collect(Collectors.joining(", ")));

        // 5. Compute the average length of names in characters (floating point) and display it
        double averageNameLength = namesList.stream().mapToInt(String::length).average().orElse(0);
        System.out.printf("Average name length: %.2f%n", averageNameLength);

        // 6. Find and display any names that occur more than once in the list. Only report each such name once.
        Set<String> duplicates = namesList.stream().filter(name -> Collections.frequency(namesList, name) > 1).collect(Collectors.toSet());
        System.out.println("Duplicate names: " + String.join(", ", duplicates));

        // 7. Write the list from item 6 into "duplicates.txt" file, one per line
        writeFile("duplicates.txt", duplicates);
    }

    public static void writeFile(String fileName, Set<String> duplicates) {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(fileName))) {
            for (String duplicate : duplicates) {
                bw.write(duplicate);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to write to the file.");
        }
    }
}
