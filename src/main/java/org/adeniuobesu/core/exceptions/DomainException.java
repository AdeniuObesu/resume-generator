package org.adeniuobesu.core.exceptions;

// Base exception for all domain errors
public class DomainException extends RuntimeException {
    public DomainException(String message) { super(message); }
    public DomainException(String message, Throwable cause) { super(message, cause); }
}