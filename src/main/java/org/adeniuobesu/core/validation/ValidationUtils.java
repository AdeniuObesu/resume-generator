package org.adeniuobesu.core.validation;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.adeniuobesu.core.exceptions.InvalidResumeException;

public final class ValidationUtils {
    // Predefined regex patterns for common validations
    private static final Map<String, String> REGEX_PATTERNS = Map.of(
        "EMAIL", "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
        "PHONE", "^[+\\d\\s()-]{8,20}$",
        "URL", "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
        "ISO_DATE", "^\\d{4}-(0[1-9]|1[0-2])$"
    );

    // String validation
    public static void validateString(String value, String fieldName, int min, int max) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidResumeException(fieldName + " cannot be empty");
        }
        if (value.length() < min || value.length() > max) {
            throw new InvalidResumeException(
                String.format("%s must be %d-%d characters", fieldName, min, max)
            );
        }
    }

    // Collection validation
    public static <T> void validateNonEmpty(List<T> list, String fieldName) {
        if (list == null || list.isEmpty()) {
            throw new InvalidResumeException(fieldName + " cannot be empty");
        }
    }

    // Date validation (ISO-8601: YYYY-MM)
    public static void validateIsoDate(String date, String fieldName) {
        if (!date.matches(REGEX_PATTERNS.get("ISO_DATE"))) {
            throw new InvalidResumeException(
                fieldName + " must be in YYYY-MM format"
            );
        }
        try {
            YearMonth.parse(date);  // Additional validation
        } catch (DateTimeParseException e) {
            throw new InvalidResumeException(
                fieldName + " contains invalid date: " + date
            );
        }
    }

    // Pattern-based validation
    public static void validatePattern(String value, String fieldName, String patternKey) {
        if (!value.matches(REGEX_PATTERNS.get(patternKey))) {
            throw new InvalidResumeException(
                String.format("Invalid %s format", fieldName)
            );
        }
    }

    // Null check with custom message
    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new InvalidResumeException(fieldName + " cannot be null");
        }
    }

    /**
     * Validates a list meets minimum size requirement
     * @param list The collection to validate
     * @param minSize Minimum required items
     * @param fieldName Field name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static <T> void validateMinSize(List<T> list, int minSize, String fieldName) {
        if (list != null && list.size() < minSize) {
            throw new InvalidResumeException(
                String.format("%s must have at least %d items", fieldName, minSize)
            );
        }
    }

    /**
     * Validates a list meets maximum size requirement
     * @param list The collection to validate
     * @param maxSize Maximum allowed items
     * @param fieldName Field name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static <T> void validateMaxSize(List<T> list, int maxSize, String fieldName) {
        if (list != null && list.size() > maxSize) {
            throw new InvalidResumeException(
                String.format("%s cannot exceed %d items", fieldName, maxSize)
            );
        }
    }
}