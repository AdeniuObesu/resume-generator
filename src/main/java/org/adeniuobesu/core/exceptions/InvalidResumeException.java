package org.adeniuobesu.core.exceptions;

public class InvalidResumeException extends RuntimeException {
    public InvalidResumeException(String message){
        super("Resume validation failed: " + message);
    }
}
