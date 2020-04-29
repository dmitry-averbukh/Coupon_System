package common.ex;

/**
 * A general exceptions that is used to describe a bad situation that happened in core elements of the system.
 */
public class SystemMalfunctionException extends Exception {
    public SystemMalfunctionException(String msg) {
        super(msg);
    }
}
