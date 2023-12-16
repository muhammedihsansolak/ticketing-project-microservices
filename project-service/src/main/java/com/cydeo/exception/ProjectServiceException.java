package com.cydeo.exception;

public class ProjectServiceException extends Exception {

    public ProjectServiceException () {
        super();
    }

    public ProjectServiceException (String message) {
        super(message);
    }

    public ProjectServiceException (String message, Throwable cause) {
        super(message, cause);
    }
}
