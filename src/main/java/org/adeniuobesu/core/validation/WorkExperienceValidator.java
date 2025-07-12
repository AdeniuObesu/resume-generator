package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.adeniuobesu.core.models.WorkExperience;

import java.util.List;

public final class WorkExperienceValidator {
    // Validation constants
    private static final int MIN_COMPANY_LENGTH = 2;
    private static final int MAX_COMPANY_LENGTH = 100;
    private static final int MIN_JOB_TITLE_LENGTH = 2;
    private static final int MAX_JOB_TITLE_LENGTH = 100;
    private static final int MIN_ACHIEVEMENT_LENGTH = 10;
    private static final int MAX_ACHIEVEMENT_LENGTH = 500;
    private static final int MIN_ACHIEVEMENTS = 1;
    private static final int MAX_ACHIEVEMENTS = 10;

    // Field name constants
    private static final String WORK_EXPERIENCE_FIELD = "Work experience";
    private static final String COMPANY_FIELD = "Company name";
    private static final String JOB_TITLE_FIELD = "Job title";
    private static final String START_DATE_FIELD = "Start date";
    private static final String END_DATE_FIELD = "End date";
    private static final String ACHIEVEMENTS_FIELD = "Key achievements";
    private static final String ACHIEVEMENT_FIELD = "Achievement";

    private WorkExperienceValidator() {}

    public static void validate(WorkExperience experience) {
        ValidationUtils.requireNonNull(experience, WORK_EXPERIENCE_FIELD);
        
        validateCompany(experience.companyName());
        validateJobTitle(experience.jobTitle());
        validateDates(experience);
        validateAchievements(experience.keyAchievements());
    }

    private static void validateCompany(String companyName) {
        ValidationUtils.validateString(
            companyName,
            COMPANY_FIELD,
            MIN_COMPANY_LENGTH,
            MAX_COMPANY_LENGTH
        );
    }

    private static void validateJobTitle(String jobTitle) {
        ValidationUtils.validateString(
            jobTitle,
            JOB_TITLE_FIELD,
            MIN_JOB_TITLE_LENGTH,
            MAX_JOB_TITLE_LENGTH
        );
    }

    private static void validateDates(WorkExperience experience) {
        ValidationUtils.validateIsoDate(experience.startDate(), START_DATE_FIELD);
        
        if (experience.endDate() != null) {
            ValidationUtils.validateIsoDate(experience.endDate(), END_DATE_FIELD);
            validateDateOrder(experience);
        }
    }

    private static void validateDateOrder(WorkExperience experience) {
        if (experience.endDate().compareTo(experience.startDate()) < 0) {
            throw new InvalidResumeException(
                "Employment dates",
                String.format(
                    "End date (%s) cannot be before start date (%s) at %s",
                    experience.endDate(),
                    experience.startDate(),
                    experience.companyName()
                )
            );
        }
    }

    private static void validateAchievements(List<String> achievements) {
        ValidationUtils.validateNonEmpty(achievements, ACHIEVEMENTS_FIELD);
        ValidationUtils.validateMaxSize(achievements, MAX_ACHIEVEMENTS, ACHIEVEMENTS_FIELD);
        ValidationUtils.validateMinSize(achievements, MIN_ACHIEVEMENTS, ACHIEVEMENTS_FIELD);
        
        achievements.forEach(achievement -> 
            ValidationUtils.validateString(
                achievement,
                ACHIEVEMENT_FIELD,
                MIN_ACHIEVEMENT_LENGTH,
                MAX_ACHIEVEMENT_LENGTH
            )
        );
    }
}