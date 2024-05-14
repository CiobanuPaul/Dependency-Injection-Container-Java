package dev.blsalin.dic;

public class CannotProvideInstanceException extends RuntimeException{
    public CannotProvideInstanceException(String message) {
        super(message);
    }
    public CannotProvideInstanceException(String message, Throwable cause) {
        super(message, cause);
    }
    public CannotProvideInstanceException(Throwable cause) {
        super(cause);
    }
    public CannotProvideInstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public CannotProvideInstanceException(){
        super("Cannot provide instance");
    }
}
