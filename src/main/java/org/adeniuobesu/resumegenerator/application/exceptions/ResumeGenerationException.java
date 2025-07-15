package org.adeniuobesu.resumegenerator.application.exceptions;

public class ResumeGenerationException extends ApplicationException {
    public ResumeGenerationException(String step) {
        super(String.format("Resume generation failed at step: %s", step));
    }
    
    public ResumeGenerationException(String step, Throwable cause) {
        super(String.format("Resume generation failed at step: %s", step), cause);
    }
}