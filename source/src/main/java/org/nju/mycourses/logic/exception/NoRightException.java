package org.nju.mycourses.logic.exception;

public class NoRightException extends RuntimeException{
    public NoRightException(String message) {
        super(message);
    }
    public NoRightException() {
        super("session check failed");
    }
}
