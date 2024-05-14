package dev.blsalin.dic;

public class InstancesCannotBeRedeclaredException extends RuntimeException{

    public InstancesCannotBeRedeclaredException(String message) {
        super(message);
    }
    public InstancesCannotBeRedeclaredException(String message, Throwable cause) {
        super(message, cause);
    }
    public InstancesCannotBeRedeclaredException(Throwable cause) {
        super(cause);
    }
    public InstancesCannotBeRedeclaredException() {
        super("Instances cannot be redeclared");
    }
}
