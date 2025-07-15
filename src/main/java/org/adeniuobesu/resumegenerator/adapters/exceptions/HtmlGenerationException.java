package org.adeniuobesu.resumegenerator.adapters.exceptions;

public class HtmlGenerationException extends AdapterException {
    public HtmlGenerationException(String details) {
        super(String.format("HTML generation failed: %s", details));
    }
    
    public HtmlGenerationException(String details, Throwable cause) {
        super(String.format("HTML generation failed: %s", details), cause);
    }
}