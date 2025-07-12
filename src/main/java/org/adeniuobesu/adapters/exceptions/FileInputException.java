package org.adeniuobesu.adapters.exceptions;

public class FileInputException extends AdapterException {
    public FileInputException(String filePath) {
        super(String.format("File input error accessing: %s", filePath));
    }
    
    public FileInputException(String filePath, Throwable cause) {
        super(String.format("File input error accessing: %s", filePath), cause);
    }
}