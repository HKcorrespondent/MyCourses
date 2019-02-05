package org.nju.mycourses.logic.exception;

public class ExceptionNotValid extends RuntimeException{
    public ExceptionNotValid(String message) {
        super(message);
    }

    public ExceptionNotValid() {
        super("Not Valid parameter");
    }
}
