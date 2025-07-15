package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.adeniuobesu.resumegenerator.core.models.ContactMethod;
import org.adeniuobesu.resumegenerator.core.models.ContactType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ContactMethodValidatorTest {

    // --- Positive Tests ---
    @ParameterizedTest
    @EnumSource(value = ContactType.class, names = {"EMAIL", "PHONE", "LINKEDIN", "GITHUB"})
    void validate_validContactTypes_doesNotThrow(ContactType type) {
        ContactMethod contact = new ContactMethod(type, getValidValueForType(type));
        assertDoesNotThrow(() -> ContactMethodValidator.validate(contact));
    }

    @Test
    void validate_validCity_doesNotThrow() {
        ContactMethod contact = new ContactMethod(ContactType.CITY, "Paris");
        assertDoesNotThrow(() -> ContactMethodValidator.validate(contact));
    }

    @Test
    void validate_validCountry_doesNotThrow() {
        ContactMethod contact = new ContactMethod(ContactType.COUNTRY, "France");
        assertDoesNotThrow(() -> ContactMethodValidator.validate(contact));
    }

    // --- Negative Tests ---
    
    // Null checks
    @Test
    void validate_nullContact_throwsException() {
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(null));
    }

    @Test
    void validate_nullContactType_throwsException() {
        ContactMethod contact = new ContactMethod(null, "test@example.com");
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    // Empty/Short values
    @Test
    void validate_emptyEmail_throwsException() {
        ContactMethod contact = new ContactMethod(ContactType.EMAIL, "");
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    @Test
    void validate_shortCity_throwsException() {
        ContactMethod contact = new ContactMethod(ContactType.CITY, "A"); // < min 2 chars
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    // Format validation
    @ParameterizedTest
    @ValueSource(strings = {"not-an-email", "missing@domain", "@missing.local"})
    void validate_invalidEmailFormat_throwsException(String invalidEmail) {
        ContactMethod contact = new ContactMethod(ContactType.EMAIL, invalidEmail);
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    @ParameterizedTest
    @ValueSource(strings = {"not-a-url", "htt://broken", "www.missing.protocol"})
    void validate_invalidUrlFormat_throwsException(String invalidUrl) {
        ContactMethod contact = new ContactMethod(ContactType.GITHUB, invalidUrl);
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    // Length validation
    @Test
    void validate_longCity_throwsException() {
        ContactMethod contact = new ContactMethod(
            ContactType.CITY, 
            "A".repeat(51) // > max 50 chars
        );
        assertThrows(InvalidResumeException.class,
            () -> ContactMethodValidator.validate(contact));
    }

    // --- Helper Methods ---
    private String getValidValueForType(ContactType type) {
        return switch (type) {
            case EMAIL -> "valid@example.com";
            case PHONE -> "+1234567890";
            case LINKEDIN -> "https://linkedin.com/in/valid";
            case GITHUB -> "https://github.com/valid";
            case PORTFOLIO -> "https://portfolio.valid";
            default -> throw new IllegalArgumentException("Unsupported type for test");
        };
    }
}