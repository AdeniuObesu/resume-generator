package org.adeniuobesu.core.exceptions;

// When domain encounters unsupported format
public class UnsupportedFormatException extends DomainException {
    public UnsupportedFormatException(String formatType) {
        super("Unsupported format: " + formatType);
    }
}