package org.nju.mycourses.logic.exception;

public class TokenErrorException extends RuntimeException{
    public TokenErrorException() {
        super("Token无效或过期");
    }
    public TokenErrorException(String message) {
        super(message);
        System.out.println("发生TOKEN错误: "+message);
    }
}
