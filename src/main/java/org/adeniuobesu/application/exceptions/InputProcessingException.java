package org.adeniuobesu.application.exceptions;

public class InputProcessingException extends ApplicationException {
    public InputProcessingException(String source) {
        super(String.format("Failed to process input from: %s", source));
    }
    
    public InputProcessingException(String source, Throwable cause) {
        super(String.format("Failed to process input from: %s", source), cause);
    }
}