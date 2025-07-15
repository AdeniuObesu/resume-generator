package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    // validateString
    @Test
    void validateString_shouldPassForValidInput() {
        assertDoesNotThrow(() ->
            ValidationUtils.validateString("Computer Science", "Field", 2, 50)
        );
    }

    @Test
    void validateString_shouldThrowForNull() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateString(null, "Field", 2, 50)
        );
        assertEquals("Field", ex.getField());
    }

    @Test
    void validateString_shouldThrowForTooShort() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateString("A", "Field", 2, 50)
        );
        assertTrue(ex.getMessage().contains("must be 2-50 characters"));
    }

    @Test
    void validateString_shouldThrowForTooLong() {
        String tooLong = "A".repeat(51);
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateString(tooLong, "Field", 2, 50)
        );
        assertTrue(ex.getMessage().contains("must be 2-50 characters"));
    }

    // validateNonEmpty
    @Test
    void validateNonEmpty_shouldPassForValidList() {
        assertDoesNotThrow(() -> ValidationUtils.validateNonEmpty(List.of("item"), "List"));
    }

    @Test
    void validateNonEmpty_shouldThrowForEmptyList() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateNonEmpty(List.of(), "List")
        );
        assertTrue(ex.getMessage().contains("cannot be empty"));
    }

    @Test
    void validateNonEmpty_shouldThrowForNull() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateNonEmpty(null, "List")
        );
        assertTrue(ex.getMessage().contains("cannot be empty"));
    }

    // validateIsoDate
    @Test
    void validateIsoDate_shouldPassForValidDate() {
        assertDoesNotThrow(() -> ValidationUtils.validateIsoDate("2023-05", "Start date"));
    }

    @Test
    void validateIsoDate_shouldThrowForInvalidFormat() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateIsoDate("2023/05", "Start date")
        );
        assertTrue(ex.getMessage().contains("must be in YYYY-MM format"));
    }

    @Test
    void validateIsoDate_shouldThrowForUnparsableDate() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateIsoDate("2023-13", "Start date")
        );
        assertTrue(ex.getMessage().contains("contains invalid date"));
    }

    // validatePattern
    @Test
    void validatePattern_shouldPassForValidEmail() {
        assertDoesNotThrow(() ->
            ValidationUtils.validatePattern("test@example.com", "Email", "EMAIL")
        );
    }

    @Test
    void validatePattern_shouldThrowForInvalidEmail() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validatePattern("invalid-email", "Email", "EMAIL")
        );
        assertTrue(ex.getMessage().contains("Invalid Email format"));
    }

    @Test
    void validatePattern_shouldThrowForUnknownPatternKey() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> ValidationUtils.validatePattern("test", "TestField", "UNKNOWN")
        );
        assertEquals("Unknown pattern key: UNKNOWN", ex.getMessage());
    }

    // requireNonNull
    @Test
    void requireNonNull_shouldThrowIfNull() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.requireNonNull(null, "Field")
        );
        assertTrue(ex.getMessage().contains("cannot be null"));
    }

    // requireNonEmpty
    @Test
    void requireNonEmpty_shouldThrowIfEmpty() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.requireNonEmpty("   ", "Field")
        );
        assertTrue(ex.getMessage().contains("cannot be empty"));
    }

    // validateMinSize
    @Test
    void validateMinSize_shouldThrowIfListTooSmall() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateMinSize(List.of("A"), 2, "Items")
        );
        assertTrue(ex.getMessage().contains("must have at least 2 items"));
    }

    // validateMaxSize
    @Test
    void validateMaxSize_shouldThrowIfListTooLarge() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateMaxSize(List.of("A", "B", "C"), 2, "Items")
        );
        assertTrue(ex.getMessage().contains("cannot exceed 2 items"));
    }

    // validateRange
    @Test
    void validateRange_shouldThrowIfBelowMin() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateRange(1, 5, 10, "Number")
        );
        assertTrue(ex.getMessage().contains("must be between 5 and 10"));
    }

    @Test
    void validateRange_shouldThrowIfAboveMax() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> ValidationUtils.validateRange(20, 5, 10, "Number")
        );
        assertTrue(ex.getMessage().contains("must be between 5 and 10"));
    }

    @Test
    void validateRange_shouldPassIfWithinBounds() {
        assertDoesNotThrow(() -> ValidationUtils.validateRange(7, 5, 10, "Number"));
    }
}
