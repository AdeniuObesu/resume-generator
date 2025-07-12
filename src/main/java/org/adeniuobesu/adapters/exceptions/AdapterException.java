package org.adeniuobesu.adapters.exceptions;

// Base exception for all adapters
public class AdapterException extends RuntimeException {
    public AdapterException(String message) {
        super(message);
    }
    
    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}