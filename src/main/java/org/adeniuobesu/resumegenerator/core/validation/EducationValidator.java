package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.adeniuobesu.resumegenerator.core.models.Education;

public final class EducationValidator {
    // Constants for validation rules - good use of constants
    private static final int MIN_INSTITUTION_LENGTH = 2;
    private static final int MAX_INSTITUTION_LENGTH = 100;
    private static final int MIN_DEGREE_LENGTH = 2;
    private static final int MAX_DEGREE_LENGTH = 50;
    private static final int MIN_FIELD_OF_STUDY_LENGTH = 2;
    private static final int MAX_FIELD_OF_STUDY_LENGTH = 50;

    // Private constructor to prevent instantiation - good for utility class
    private EducationValidator() {}

    public static void validate(Education education) {
        if (education == null) {
            throw new InvalidResumeException("Education", "cannot be null");
        }
        
        validateInstitution(education.institutionName());
        validateDegree(education.degree());
        validateFieldOfStudy(education.fieldOfStudy());
        validateDates(education.startDate(), education.endDate(), education.institutionName());
    }

    private static void validateInstitution(String institution) {
        ValidationUtils.validateString(
            institution,
            "Institution name",
            MIN_INSTITUTION_LENGTH,
            MAX_INSTITUTION_LENGTH
        );
    }

    private static void validateDegree(String degree) {
        ValidationUtils.validateString(
            degree,
            "Degree",
            MIN_DEGREE_LENGTH,
            MAX_DEGREE_LENGTH
        );
    }

    private static void validateFieldOfStudy(String fieldOfStudy) {
        if (fieldOfStudy != null) {
            ValidationUtils.validateString(
                fieldOfStudy,
                "Field of study",
                MIN_FIELD_OF_STUDY_LENGTH,
                MAX_FIELD_OF_STUDY_LENGTH
            );
        }
    }

    private static void validateDates(String startDate, String endDate, String institutionName) {
        ValidationUtils.validateIsoDate(startDate, "Start date");
        
        if (endDate != null) {
            ValidationUtils.validateIsoDate(endDate, "End date");
            
            if (endDate.compareTo(startDate) < 0) {
                throw new InvalidResumeException(
                    "Education dates",
                    String.format("End date cannot be before start date for %s", institutionName)
                );
            }
        }
    }
}