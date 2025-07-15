package org.adeniuobesu.resumegenerator.core.exceptions;

public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String fieldName, String error) {
        super(String.format("Validation error in %s: %s", fieldName, error));
    }
}