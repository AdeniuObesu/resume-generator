package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.adeniuobesu.core.models.Education;

public final class EducationValidator {
    // Constants for validation rules
    private static final int MIN_INSTITUTION_LENGTH = 2;
    private static final int MAX_INSTITUTION_LENGTH = 100;
    private static final int MIN_DEGREE_LENGTH = 2;
    private static final int MAX_DEGREE_LENGTH = 50;

    public static void validate(Education education) {
        ValidationUtils.requireNonNull(education, "Education entry");
        
        // Institution validation
        ValidationUtils.validateString(
            education.institutionName(),
            "Institution name",
            MIN_INSTITUTION_LENGTH,
            MAX_INSTITUTION_LENGTH
        );

        // Degree validation
        ValidationUtils.validateString(
            education.degree(),
            "Degree",
            MIN_DEGREE_LENGTH,
            MAX_DEGREE_LENGTH
        );

        // Field of study (optional)
        if (education.fieldOfStudy() != null) {
            ValidationUtils.validateString(
                education.fieldOfStudy(),
                "Field of study",
                2,
                50
            );
        }

        // Date validation
        ValidationUtils.validateIsoDate(education.startDate(), "Start date");
        if (education.endDate() != null) {
            ValidationUtils.validateIsoDate(education.endDate(), "End date");
            
            // Verify date ordering
            if (education.endDate().compareTo(education.startDate()) < 0) {
                throw new InvalidResumeException(
                    "Education end date cannot be before start date for " + 
                    education.institutionName()
                );
            }
        }
    }
}