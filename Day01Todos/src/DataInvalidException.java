public class DataInvalidException extends Exception {
    public DataInvalidException (String msg) {
        super(msg);
    }

    // This constructor allows you to chain exceptions
    public DataInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
