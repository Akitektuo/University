package ro.ubb.truckers.domain;

/**
 * Created by radu.
 */
public class TruckersException extends RuntimeException{

    public TruckersException(String message) {
        super(message);
    }

    public TruckersException(String message, Object... parameters) {
        super(String.format(message, parameters));
    }

    public TruckersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruckersException(Throwable cause) {
        super(cause);
    }
}
