package org.nju.mycourses.logic.exception;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException() {
        super("Server Error");
    }
    public ServerErrorException(String message) {
        super(message);
    }
}
