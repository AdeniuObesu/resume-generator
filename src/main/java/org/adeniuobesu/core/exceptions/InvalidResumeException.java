package org.adeniuobesu.core.exceptions;

// When resume validation fails business rules
public class InvalidResumeException extends DomainException {
    private String field;
    private String violation;

    public InvalidResumeException(String field, String violation) {
        super(String.format("Invalid resume: %s - %s", field, violation));
        this.field = field;
    }

    public String getField() {
        return field;
    }
    public String getViolation() {
        return violation;
    }
}
