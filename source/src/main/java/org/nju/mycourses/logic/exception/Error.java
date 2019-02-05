package org.nju.mycourses.logic.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty(value = "error")
    String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static Error back(String errorMessage){
        return new Error(errorMessage);
    }
}
