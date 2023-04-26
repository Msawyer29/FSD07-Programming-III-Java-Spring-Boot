import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Globals {
    public static ArrayList<Todo> todoList = new ArrayList<>();
    static final SimpleDateFormat dateFormatScreen = new SimpleDateFormat("MM/dd/yyyy"); // why different format/order from SimpleDateFormat? display to user

    static {
        dateFormatScreen.setLenient(false);
    }
}
