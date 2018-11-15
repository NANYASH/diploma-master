package util.exception;

public class UtilException extends Exception {

    /**
     * Instantiates a new business util exception.
     */
    public UtilException() {
        super();
    }

    /**
     * Instantiates a new business util exception.
     *
     * @param message the message
     */
    public UtilException(String message) {
        super(message);
    }

    /**
     * Instantiates a new business util exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new business util exception.
     *
     * @param cause the cause
     */
    public UtilException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new business util exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
