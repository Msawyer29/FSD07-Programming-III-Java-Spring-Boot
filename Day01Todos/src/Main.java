import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Now using Globals.java ArrayList<Todo>
    // static ArrayList<Todo> todoList = new ArrayList<>();

    final static String DATA_FILE_NAME = "todos.txt";

    static Scanner input = new Scanner(System.in);

    // HW Part 4: create method static int inputInt()
    // It will allow user to enter an integer using Scanner.nextInt().
    // Modify the program so it doesn't crash if user enters non-integer on Scanner.nextInt() in menu, or when adding, or when editing a todo
    // By using the inputInt() method, the program catches InputMismatchException exceptions
    // If it does it will prompt the user to enter a valid integer value without crashing
    static int inputInt(){
        while (true) {
            try {
                int result = input.nextInt(); // ex InputMismatchException
                input.nextLine(); // consume the leftover newLine
                return result;
            } catch (InputMismatchException ex) {
                input.nextLine(); // consume the invalid input (consume the newline left over from invalid input)
                System.out.println("Invalid input. Enter an integer and try again.");
            }
        }
    }

    // ADD static int getMenuChoice() - this organizes the code better
    // instead of writing the menu display and input handling code in the main method, you call getMenuChoice()
    static int getMenuChoice() {
        System.out.print("Please make a choice [0-4]:\n"
                + "1. Add a todo\n"
                + "2. List all todos (numbered)\n"
                + "3. Delete a todo\n"
                + "4. Modify a todo\n"
                + "0. Exit\n"
                + "Your choice is: ");
        int choice = inputInt();
        return choice;
    }

    //
    public static void main(String[] args) {
        loadDataFromFile();
        while (true) { // While (true) is an infinite loop
            int choice = getMenuChoice();
            switch (choice) {
                case 1:
                    addTodo();
                    break;
                case 2:
                    listTodos();
                    break;
                case 3:
                    deleteTodo();
                    break;
                case 4:
                    modifyTodo();
                    break;
                case 0:
                    saveDataToFile();
                    System.out.println("Exiting Todo List.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void addTodo() {
        try {
            System.out.println("Adding a todo.");
            System.out.print("Enter task description: ");
            String task = input.nextLine();
            System.out.print("Enter due Date (mm/dd/yyyy): "); // match Global.java format - show user lowercase mm
            String dueDateStr = input.nextLine();
            Date dueDate = Globals.dateFormatScreen.parse(dueDateStr); // ex ParseException
            System.out.print("Enter hours of work (integer): ");
            int hoursOfWork = inputInt(); // ex InputMismatchException from inputInt() method
            Todo todo = new Todo(task, dueDate, hoursOfWork, Todo.TaskStatus.Pending); // ex DataInvalidException
            Globals.todoList.add(todo);
            System.out.println("You created the following todo:");
            System.out.println(todo);
        } catch (ParseException ex) {
            System.out.println("Error parsing: " + ex.getMessage());
        } catch (DataInvalidException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void listTodos() {
        System.out.println("Listing all todos.");
        if (Globals.todoList.isEmpty()) {
            System.out.println("There are no todos.");
        } else {
            for (int i = 0; i < Globals.todoList.size(); i++) {
                System.out.println("#" + (i + 1) + ": " + Globals.todoList.get(i));
            }
        }
    }

    public static void deleteTodo() {
        System.out.println("Deleting a todo.");
        System.out.print("Which todo # would you like to delete? ");
        int todoNumber = input.nextInt() - 1;
        input.nextLine(); // Consume newline left-over

        if (todoNumber >= 0 && todoNumber < Globals.todoList.size()) {
            Globals.todoList.remove(todoNumber);
            System.out.println("Deleted todo #" + (todoNumber + 1) + " successfully.");
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    public static void modifyTodo() {
        System.out.println("Modifying a todo.");
        System.out.print("Which todo # would you like to modify? ");
        int todoNumber = inputInt() - 1;

        if (todoNumber >= 0 && todoNumber < Globals.todoList.size()) {
            Todo todoToModify = Globals.todoList.get(todoNumber);
            System.out.println("Modifying todo #" + (todoNumber + 1) + ": " + todoToModify);

            System.out.print("Enter new task description: ");
            String newTask = input.nextLine();
            System.out.print("Enter new due Date (yyyy/MM/dd): ");
            String newDateString = input.nextLine();
            System.out.print("Enter new hours of work (integer): ");
            int newHoursOfWork = inputInt();
            System.out.print("Enter if task is 'Done' or 'Pending': ");
            String newStatusString = input.nextLine();

            Date newDueDate = null;
            try {
                newDueDate = Globals.dateFormatScreen.parse(newDateString); // Use Globals.dateFormatScreen
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                return;
            }

            try {
                todoToModify.setTask(newTask);
                todoToModify.setDueDate(newDueDate);
                todoToModify.setHoursOfWork(newHoursOfWork);

                // Check if the input status string is valid and set the status accordingly
                if (newStatusString.equalsIgnoreCase("Done")) {
                    todoToModify.setStatus(Todo.TaskStatus.Done);
                } else if (newStatusString.equalsIgnoreCase("Pending")) {
                    todoToModify.setStatus(Todo.TaskStatus.Pending);
                } else {
                    System.out.println("Invalid task status. Please enter 'Done' or 'Pending'.");
                    return;
                }

                System.out.println("You've modified todo #" + (todoNumber + 1) + " as follows:");
                System.out.println(todoToModify);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (DataInvalidException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    // HW Part 2: add loadDataFromFile method + FileNotFoundException
    static void loadDataFromFile() { // calls Todo(String)
        try (Scanner inputFile = new Scanner(new File(DATA_FILE_NAME))) {
            while (inputFile.hasNextLine()) {
                String line = "";
                try {
                    line = inputFile.nextLine();
                    Globals.todoList.add(new Todo(line)); // ex DataInvalidException
                } catch (DataInvalidException ex) {
                    System.out.printf("Error parsing line (%s) ignoring: %s\n", ex.getMessage(), line);
                }
            }
        } catch (FileNotFoundException ex) {
            // Teacher Note: ignore, it's okay if the file is not there
        }
    }

    // HW Part 2: add saveDataToFile method + IOException
    static void saveDataToFile() { // calls todo.toDataString();
        try (PrintWriter outputFile = new PrintWriter(new File(DATA_FILE_NAME))) {
            for (Todo todo : Globals.todoList) {
                outputFile.println(todo.toDataString());
            }
        } catch (IOException ex) {
            System.out.println("Error writing data back to file");
        }
    }
}

