package com.cydeo.exception;

public class TaskServiceException extends Exception {

    public TaskServiceException () {
        super();
    }

    public TaskServiceException (String message) {
        super(message);
    }

    public TaskServiceException (String message, Throwable cause) {
        super(message, cause);
    }
}
