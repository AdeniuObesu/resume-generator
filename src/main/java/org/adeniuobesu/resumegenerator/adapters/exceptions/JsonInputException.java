package org.adeniuobesu.resumegenerator.adapters.exceptions;

public class JsonInputException extends AdapterException {
    public JsonInputException(String jsonContent) {
        super(String.format("Failed to parse JSON: %s", 
              jsonContent.substring(0, Math.min(50, jsonContent.length()))));
    }
    
    public JsonInputException(String jsonContent, Throwable cause) {
        super(String.format("Failed to parse JSON: %s", 
              jsonContent.substring(0, Math.min(50, jsonContent.length()))), cause);
    }
}