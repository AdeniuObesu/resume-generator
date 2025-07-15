package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.adeniuobesu.resumegenerator.core.models.WorkExperience;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkExperienceValidatorTest {

    // Test data builders
    private WorkExperience createValidWorkExperience() {
        return new WorkExperience(
            "Tech Corp",
            "Senior Developer",
            "2020-01",
            "2023-12",
            List.of("Optimized database queries reducing response time by 40%")
        );
    }

    // --- Positive Tests ---
    @Test
    void validate_validWorkExperience_doesNotThrow() {
        assertDoesNotThrow(() -> 
            WorkExperienceValidator.validate(createValidWorkExperience())
        );
    }

    @Test
    void validate_currentPositionWithNullEndDate_doesNotThrow() {
        WorkExperience currentJob = new WorkExperience(
            "Tech Corp",
            "CTO",
            "2023-01",
            null,
            List.of("Led engineering team of 20 developers")
        );
        assertDoesNotThrow(() -> WorkExperienceValidator.validate(currentJob));
    }

    // --- Negative Tests ---
    
    // Company name validation
    @Test
    void validate_emptyCompanyName_throwsException() {
        assertThrows(InvalidResumeException.class, () -> 
            WorkExperienceValidator.validate(new WorkExperience(
                "",  // Empty
                "Developer",
                "2020-01",
                "2023-12",
                List.of("Valid achievement")
            ))
        );
    }

    @Test
    void validate_singleCharCompanyName_throwsException() {
        assertThrows(InvalidResumeException.class, () -> 
            WorkExperienceValidator.validate(new WorkExperience(
                "A",  // Too short
                "Developer",
                "2020-01",
                "2023-12",
                List.of("Valid achievement")
            ))
        );
    }

    @Test
    void validate_companyNameExceedsMaxLength_throwsException() {
        assertThrows(InvalidResumeException.class, () -> 
            WorkExperienceValidator.validate(new WorkExperience(
                "X".repeat(101),  // 101 chars (max is 100)
                "Developer",
                "2020-01",
                "2023-12",
                List.of("Valid achievement")
            ))
        );
    }
    // Job title validation
    @Test
    void validate_emptyJobTitle_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "",
            "2020-01",
            "2023-12",
            List.of("Did work")
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    // Date validation
    @Test
    void validate_invalidDateFormat_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "Developer",
            "January-2020",  // Invalid format
            "2023-12",
            List.of("Worked")
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    @Test
    void validate_endDateBeforeStartDate_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "Developer",
            "2023-01",  // Start
            "2020-12",  // End (before start)
            List.of("Worked")
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    // Achievements validation
    @Test
    void validate_emptyAchievements_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "Developer",
            "2020-01",
            "2023-12",
            List.of()  // Empty list
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    @Test
    void validate_achievementTooShort_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "Developer",
            "2020-01",
            "2023-12",
            List.of("Too short")  // < MIN_ACHIEVEMENT_LENGTH (10)
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    @Test
    void validate_tooManyAchievements_throwsException() {
        WorkExperience invalid = new WorkExperience(
            "Tech Corp",
            "Developer",
            "2020-01",
            "2023-12",
            List.of("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11")  // 11 > MAX_ACHIEVEMENTS (10)
        );
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(invalid));
    }

    // Null checks
    @Test
    void validate_nullWorkExperience_throwsException() {
        assertThrows(InvalidResumeException.class,
            () -> WorkExperienceValidator.validate(null));
    }
}