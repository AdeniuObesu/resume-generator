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
    //private static final int MIN_ACHIEVEMENTS = 1;
    private static final int MAX_ACHIEVEMENTS = 10;

    public static void validate(WorkExperience exp) {
        ValidationUtils.requireNonNull(exp, "Work experience entry");

        // Company validation
        ValidationUtils.validateString(
            exp.companyName(),
            "Company name",
            MIN_COMPANY_LENGTH,
            MAX_COMPANY_LENGTH
        );

        // Job title validation
        ValidationUtils.validateString(
            exp.jobTitle(),
            "Job title",
            MIN_JOB_TITLE_LENGTH,
            MAX_JOB_TITLE_LENGTH
        );

        // Date validation
        ValidationUtils.validateIsoDate(exp.startDate(), "Start date");
        if (exp.endDate() != null) {
            ValidationUtils.validateIsoDate(exp.endDate(), "End date");
            validateDateOrder(exp);
        }

        // Achievements validation
        validateAchievements(exp.keyAchievements());
    }

    private static void validateDateOrder(WorkExperience exp) {
        if (exp.endDate().compareTo(exp.startDate()) < 0) {
            throw new InvalidResumeException(
                String.format(
                    "Invalid date range at %s: end date (%s) before start (%s)",
                    exp.companyName(),
                    exp.endDate(),
                    exp.startDate()
                )
            );
        }
    }

    private static void validateAchievements(List<String> achievements) {
        ValidationUtils.validateNonEmpty(achievements, "Key achievements");
        
        if (achievements.size() > MAX_ACHIEVEMENTS) {
            throw new InvalidResumeException(
                String.format(
                    "Maximum %d achievements allowed per position (found %d)",
                    MAX_ACHIEVEMENTS,
                    achievements.size()
                )
            );
        }

        achievements.forEach(ach -> 
            ValidationUtils.validateString(
                ach,
                "Achievement",
                MIN_ACHIEVEMENT_LENGTH,
                MAX_ACHIEVEMENT_LENGTH
            )
        );
    }
}