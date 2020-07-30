package it.unimore.dipi.iot.wldt.example.ikea.exception;

public class TradfriException extends Exception {

    public TradfriException(String message, Exception cause) {
        super(message, cause);
    }

    public TradfriException(String message) {
        super(message);
    }

    public TradfriException(Exception cause) {
        super(cause);
    }
}
