import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Todo {

    // Defined constructor:
    // Note: exception thrown in the constructor, it aborts the creation/instantiation of the object
    public Todo(String task, Date dueDate, int hoursOfWork, TaskStatus status) throws DataInvalidException {
        setTask(task);
        setDueDate(dueDate);
        setHoursOfWork(hoursOfWork);
        setStatus(status);
    }

    // HW Part 2: add String dataLine constructor
    // This modification is used to split and parse line's content and assign values to fields using setters
    // field exceptions are handled
    // Note: setters are unchecked
    public Todo(String dataLine) throws DataInvalidException {
        // no longer thrown: ParseException, NumberFormatException, IllegalArgumentException
        String[] data = dataLine.split(";");
        if (data.length != 4) { // task, dueDate, hoursOfWork, status
            throw new DataInvalidException("Invalid number of items in line");
        }
        try {
            // continue parsing, use setters to set values
            String task = data[0];
            Date dueDate = dateFormatFile.parse(data[1]); // ex ParseException
            int hoursOfWork = Integer.parseInt(data[2]); // ex NumberFormatException
            TaskStatus status = TaskStatus.valueOf(data[3]); // ex IllegalArgumentException
            setTask(task); // ex DataInvalidException
            setDueDate(dueDate); // ex DataInvalidException
            setHoursOfWork(hoursOfWork); // ex DataInvalidException
            setStatus(status);
        } catch (ParseException ex) {
            // example of exception chaining
            throw new DataInvalidException("Date format invalid", ex);
        } catch (NumberFormatException ex) {
            throw new DataInvalidException("Integer value expected", ex);
        } catch (IllegalArgumentException ex) {
            throw new DataInvalidException("Enum value invalid", ex);
        }
    }

    // INITIALIZE DATE - standard format
    // SimpleDateFormat + static initializer, ex dateFormatFile to ParseException above
    private static final SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd");

    static { // static Initializer
        dateFormatFile.setLenient(false);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // Note: MM = months, mm = minutes
        return task + ", " + sdf.format(dueDate) + ", will take " + hoursOfWork + " hour(s) of work," + status + ".";
    } // end toString method

    // HW Part 2 add method toDataString to format entry data for the data file "todo.txt"
    // toDataString is formatted for data entry into "todos.txt"
    public String toDataString() {
        // returns a string similar to "Buy milk;2023-04-26;1"
        String dueDateStr = dateFormatFile.format(dueDate);
        return String.format("%s;%s;%d;%s", task, dueDateStr, hoursOfWork, status);
    } // end toDataString method


    // FIELDS
    private String task; // 2-50 characters long, must NOT contain a semicolon or | or ` (reverse single quote) characters
    private Date dueDate; // (Should be called DateTime because it includes both) Date between year 1900 and 2100
    private int hoursOfWork; // 0 or greater number
    // Part 3 add TaskStatus status; and the following definition: enum TaskStatus { Pending, Done };
    private TaskStatus status;
    enum TaskStatus {Pending, Done}


    // GETTERS AND SETTERS (PROPERTIES)
    public String getTask() {
        return task;
    }

    public void setTask(String task) throws DataInvalidException {
        if (task == null || task.length() < 2 || task.length() > 50 || task.contains(";") || task.contains("|") || task.contains("`")) {
            // HW Part 2: no semicolon, pipe or reverse single quote
            throw new DataInvalidException("Task must be 2-50 characters long and must NOT contain a semicolon, | or ` characters.");
        }
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) throws DataInvalidException {
        if (dueDate.getYear() + 1900 < 1900 || dueDate.getYear() + 1900 > 2100) {
            throw new DataInvalidException("Due date must be between 1900 and 2100.");
        }
        this.dueDate = dueDate;
    }

    public int getHoursOfWork() {
        return hoursOfWork;
    }

    public void setHoursOfWork(int hoursOfWork) {
        if (hoursOfWork < 0) {
            throw new IllegalArgumentException("Hours of work must be 0 or greater.");
        }
        this.hoursOfWork = hoursOfWork;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
