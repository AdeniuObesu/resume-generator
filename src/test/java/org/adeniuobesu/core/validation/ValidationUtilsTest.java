package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    // validateString tests
    @Test
    void validateString_shouldAcceptValidString() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateString("Valid", "Test Field", 2, 10));
    }

    @Test
    void validateString_shouldRejectNull() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateString(null, "Test Field", 2, 10));
    }

    @Test
    void validateString_shouldRejectEmpty() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateString("   ", "Test Field", 2, 10));
    }

    @Test
    void validateString_shouldRejectShortString() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateString("A", "Test Field", 2, 10));
    }

    @Test
    void validateString_shouldRejectLongString() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateString("ThisIsTooLong", "Test Field", 2, 5));
    }

    // validateNonEmpty tests
    @Test
    void validateNonEmpty_shouldAcceptNonEmptyList() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateNonEmpty(List.of("item"), "Test List"));
    }

    @Test
    void validateNonEmpty_shouldRejectNullList() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateNonEmpty(null, "Test List"));
    }

    @Test
    void validateNonEmpty_shouldRejectEmptyList() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateNonEmpty(List.of(), "Test List"));
    }

    // validateIsoDate tests
    @Test
    void validateIsoDate_shouldAcceptValidDate() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateIsoDate("2023-12", "Date"));
    }

    @Test
    void validateIsoDate_shouldRejectInvalidFormat() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateIsoDate("12-2023", "Date"));
    }

    @Test
    void validateIsoDate_shouldRejectInvalidDate() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateIsoDate("2023-13", "Date"));
    }

    // validatePattern tests
    @Test
    void validatePattern_shouldAcceptValidEmail() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validatePattern("test@example.com", "Email", "EMAIL"));
    }

    @Test
    void validatePattern_shouldRejectInvalidEmail() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validatePattern("invalid.com", "Email", "EMAIL"));
    }

    // requireNonNull tests
    @Test
    void requireNonNull_shouldAcceptNonNull() {
        assertDoesNotThrow(() -> 
            ValidationUtils.requireNonNull("object", "Field"));
    }

    @Test
    void requireNonNull_shouldRejectNull() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.requireNonNull(null, "Field"));
    }

    // Collection size tests
    @Test
    void validateMinSize_shouldAcceptValidList() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateMinSize(List.of(1, 2, 3), 2, "List"));
    }

    @Test
    void validateMinSize_shouldAcceptNullList() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateMinSize(null, 2, "List"));
    }

    @Test
    void validateMinSize_shouldRejectSmallList() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateMinSize(List.of(1), 2, "List"));
    }

    @Test
    void validateMaxSize_shouldAcceptValidList() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateMaxSize(List.of(1, 2), 3, "List"));
    }

    @Test
    void validateMaxSize_shouldAcceptNullList() {
        assertDoesNotThrow(() -> 
            ValidationUtils.validateMaxSize(null, 2, "List"));
    }

    @Test
    void validateMaxSize_shouldRejectLargeList() {
        assertThrows(InvalidResumeException.class, () -> 
            ValidationUtils.validateMaxSize(List.of(1, 2, 3), 2, "List"));
    }
}