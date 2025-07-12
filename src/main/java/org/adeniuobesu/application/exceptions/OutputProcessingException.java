package org.adeniuobesu.application.exceptions;

public class OutputProcessingException extends ApplicationException {
    public OutputProcessingException(String target) {
        super(String.format("Failed to process output to: %s", target));
    }
    
    public OutputProcessingException(String target, Throwable cause) {
        super(String.format("Failed to process output to: %s", target), cause);
    }
}