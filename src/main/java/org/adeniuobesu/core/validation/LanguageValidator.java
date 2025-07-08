package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.models.Language;

public final class LanguageValidator {
    // Validation constants
    private static final int MIN_LANGUAGE_LENGTH = 2;
    private static final int MAX_LANGUAGE_LENGTH = 30;

    public static void validate(Language language) {
        ValidationUtils.requireNonNull(language, "Language entry");

        // Language name validation
        ValidationUtils.validateString(
            language.language(),
            "Language name",
            MIN_LANGUAGE_LENGTH,
            MAX_LANGUAGE_LENGTH
        );

        // Proficiency validation
        ValidationUtils.requireNonNull(
            language.proficiency(),
            "Language proficiency"
        );
    }
}