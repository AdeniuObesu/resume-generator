package org.adeniuobesu.resumegenerator.adapters.exceptions;

public class CliInputException extends AdapterException {
    public CliInputException(String input) {
        super(String.format("Invalid CLI input: %s", input));
    }
    
    public CliInputException(String input, Throwable cause) {
        super(String.format("Invalid CLI input: %s", input), cause);
    }
}