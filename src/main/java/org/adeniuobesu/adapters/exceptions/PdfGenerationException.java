package org.adeniuobesu.adapters.exceptions;

public class PdfGenerationException extends AdapterException {
    public PdfGenerationException(String details) {
        super(String.format("PDF generation failed: %s", details));
    }
    
    public PdfGenerationException(String details, Throwable cause) {
        super(String.format("PDF generation failed: %s", details), cause);
    }
}