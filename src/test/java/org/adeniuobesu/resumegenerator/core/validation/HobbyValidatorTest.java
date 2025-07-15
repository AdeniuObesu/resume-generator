package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.models.Hobby;
import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HobbyValidatorTest {

    @Test
    void validate_shouldAcceptValidHobby() {
        Hobby validHobby = new Hobby("Reading", "Enjoying books of various genres");
        assertDoesNotThrow(() -> HobbyValidator.validate(validHobby));
    }

    @Test
    void validate_shouldRejectNullHobby() {
        assertThrows(InvalidResumeException.class, () -> HobbyValidator.validate(null));
    }

    @Test
    void validate_shouldRejectShortName() {
        Hobby hobby = new Hobby("A", null);
        assertThrows(InvalidResumeException.class, () -> HobbyValidator.validate(hobby));
    }

    @Test
    void validate_shouldRejectLongName() {
        Hobby hobby = new Hobby("ExtremelyLongHobbyNameThatExceedsMaximumAllowedLength", null);
        assertThrows(InvalidResumeException.class, () -> HobbyValidator.validate(hobby));
    }

    @Test
    void validate_shouldRejectShortDescription() {
        Hobby hobby = new Hobby("Gardening", "Too short");
        assertThrows(InvalidResumeException.class, () -> HobbyValidator.validate(hobby));
    }

    @Test
    void validate_shouldAcceptNullDescription() {
        Hobby hobby = new Hobby("Swimming", null);
        assertDoesNotThrow(() -> HobbyValidator.validate(hobby));
    }
}