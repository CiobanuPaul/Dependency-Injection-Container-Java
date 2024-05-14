package dev.blsalin.dic;

public class NullParameterNotAllowedException extends RuntimeException{
    public NullParameterNotAllowedException(String message) {
        super(message);
    }
    public NullParameterNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
    public NullParameterNotAllowedException(Throwable cause) {
        super(cause);
    }
    public NullParameterNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public NullParameterNotAllowedException(){
        super("Null is not allowed as a parameter");
    }
}
