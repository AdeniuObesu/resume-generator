package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.models.Hobby;

public final class HobbyValidator {
    // Validation constants
    private static final int MIN_HOBBY_NAME_LENGTH = 2;
    private static final int MAX_HOBBY_NAME_LENGTH = 30;
    private static final int MAX_DESCRIPTION_LENGTH = 150;

    public static void validate(Hobby hobby) {
        ValidationUtils.requireNonNull(hobby, "Hobby entry");

        // Name validation
        ValidationUtils.validateString(
            hobby.name(),
            "Hobby name",
            MIN_HOBBY_NAME_LENGTH,
            MAX_HOBBY_NAME_LENGTH
        );

        // Optional description validation
        if (hobby.description() != null && !hobby.description().isBlank()) {
            ValidationUtils.validateString(
                hobby.description(),
                "Hobby description",
                10,  // Minimum description length
                MAX_DESCRIPTION_LENGTH
            );
        }
    }
}