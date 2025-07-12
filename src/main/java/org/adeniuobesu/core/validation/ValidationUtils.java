package org.adeniuobesu.core.validation;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.adeniuobesu.core.exceptions.InvalidResumeException;

public final class ValidationUtils {
    // Regex patterns - made package-private for potential reuse
    static final Map<String, String> REGEX_PATTERNS = Map.of(
        "EMAIL", "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
        "PHONE", "^[+\\d\\s()-]{8,20}$",
        "URL", "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
        "ISO_DATE", "^\\d{4}-(0[1-9]|1[0-2])$"
    );

    private static final String CANNOT_BE_EMPTY = "%s cannot be empty";
    private static final String CANNOT_BE_NULL = "%s cannot be null";
    private static final String LENGTH_RANGE = "%s must be %d-%d characters";
    private static final String INVALID_FORMAT = "Invalid %s format";
    private static final String MIN_ITEMS = "%s must have at least %d items";
    private static final String MAX_ITEMS = "%s cannot exceed %d items";

    // Private constructor to prevent instantiation
    private ValidationUtils() {}

    /**
     * Validates string meets length requirements and is non-empty
     * @param value The string to validate
     * @param fieldName Descriptive name for error messages
     * @param min Minimum length (inclusive)
     * @param max Maximum length (inclusive)
     * @throws InvalidResumeException if validation fails
     */
    public static void validateString(String value, String fieldName, int min, int max) {
        requireNonEmpty(value, fieldName);
        
        if (value.length() < min || value.length() > max) {
            throw new InvalidResumeException(
                fieldName, 
                String.format(LENGTH_RANGE, fieldName, min, max)
            );
        }
    }

    /**
     * Validates collection is non-null and non-empty
     * @param collection The collection to validate
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void validateNonEmpty(List<?> collection, String fieldName) {
        if (collection == null || collection.isEmpty()) {
            throw new InvalidResumeException(
                fieldName,
                String.format(CANNOT_BE_EMPTY, fieldName)
            );
        }
    }

    /**
     * Validates date string is in correct ISO-8601 (YYYY-MM) format
     * @param date The date string to validate
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void validateIsoDate(String date, String fieldName) {
        requireNonEmpty(date, fieldName);

        // Regex détendue : juste YYYY-MM
        if (!date.matches("\\d{4}-\\d{2}")) {
            throw new InvalidResumeException(
                fieldName,
                "must be in YYYY-MM format"
            );
        }

        try {
            YearMonth.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidResumeException(
                fieldName,
                "contains invalid date: " + date
            );
        }
    }

    /**
     * Validates string matches specified pattern
     * @param value The string to validate
     * @param fieldName Descriptive name for error messages
     * @param patternKey Key for predefined regex pattern
     * @throws InvalidResumeException if validation fails
     */
    public static void validatePattern(String value, String fieldName, String patternKey) {
        requireNonEmpty(value, fieldName);
        
        String pattern = REGEX_PATTERNS.get(patternKey);
        if (pattern == null) {
            throw new IllegalArgumentException("Unknown pattern key: " + patternKey);
        }
        
        if (!value.matches(pattern)) {
            throw new InvalidResumeException(
                fieldName,
                String.format(INVALID_FORMAT, fieldName)
            );
        }
    }

    /**
     * Validates object is non-null
     * @param obj The object to validate
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new InvalidResumeException(
                fieldName,
                String.format(CANNOT_BE_NULL, fieldName)
            );
        }
    }

    /**
     * Validates string is non-null and non-empty
     * @param value The string to validate
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidResumeException(
                fieldName,
                String.format(CANNOT_BE_EMPTY, fieldName)
            );
        }
    }

    /**
     * Validates collection meets minimum size requirement
     * @param collection The collection to validate
     * @param minSize Minimum required items
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void validateMinSize(List<?> collection, int minSize, String fieldName) {
        requireNonNull(collection, fieldName);
        
        if (collection.size() < minSize) {
            throw new InvalidResumeException(
                fieldName,
                String.format(MIN_ITEMS, fieldName, minSize)
            );
        }
    }

    /**
     * Validates collection meets maximum size requirement
     * @param collection The collection to validate
     * @param maxSize Maximum allowed items
     * @param fieldName Descriptive name for error messages
     * @throws InvalidResumeException if validation fails
     */
    public static void validateMaxSize(List<?> collection, int maxSize, String fieldName) {
        requireNonNull(collection, fieldName);
        
        if (collection.size() > maxSize) {
            throw new InvalidResumeException(
                fieldName,
                String.format(MAX_ITEMS, fieldName, maxSize)
            );
        }
    }
    // TODO: add appropriate JavaDoc
    public static void validateRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidResumeException(
                fieldName,
                String.format("must be between %d and %d", min, max)
            );
        }
    }
}