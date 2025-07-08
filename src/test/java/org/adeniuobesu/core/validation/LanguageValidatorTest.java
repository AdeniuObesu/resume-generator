package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.models.Language;
import org.adeniuobesu.core.models.LanguageProficiency;
import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LanguageValidatorTest {

    @Test
    void validate_shouldAcceptValidLanguage() {
        Language validLanguage = new Language("English", LanguageProficiency.FLUENT);
        assertDoesNotThrow(() -> LanguageValidator.validate(validLanguage));
    }

    @Test
    void validate_shouldRejectNullLanguage() {
        assertThrows(InvalidResumeException.class, () -> LanguageValidator.validate(null));
    }

    @Test
    void validate_shouldRejectShortLanguageName() {
        Language language = new Language("A", LanguageProficiency.INTERMEDIATE);
        assertThrows(InvalidResumeException.class, () -> LanguageValidator.validate(language));
    }

    @Test
    void validate_shouldRejectLongLanguageName() {
        Language language = new Language("ThisIsAnExtremelyLongLanguageNameThatExceedsLimit", LanguageProficiency.BASIC);
        assertThrows(InvalidResumeException.class, () -> LanguageValidator.validate(language));
    }

    @Test
    void validate_shouldRejectNullProficiency() {
        Language language = new Language("Spanish", null);
        assertThrows(InvalidResumeException.class, () -> LanguageValidator.validate(language));
    }
}