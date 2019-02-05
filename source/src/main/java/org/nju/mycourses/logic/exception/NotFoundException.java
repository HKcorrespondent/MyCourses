package org.nju.mycourses.logic.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String thins) {
        super("can't find "+thins);
    }
}
