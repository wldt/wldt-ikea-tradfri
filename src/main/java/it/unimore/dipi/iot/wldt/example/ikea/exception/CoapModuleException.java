package it.unimore.dipi.iot.wldt.example.ikea.exception;

public class CoapModuleException extends Exception {

    public CoapModuleException(String message, Exception cause) {
        super(message, cause);
    }

    public CoapModuleException(String message) {
        super(message);
    }

    public CoapModuleException(Exception cause) {
        super(cause);
    }
}
