package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.adeniuobesu.resumegenerator.core.models.Education;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EducationValidatorTest {

    @Test
    @DisplayName("Valid education should pass validation")
    void testValidEducationShouldPass() {
        Education education = new Education(
            "MIT",
            "Bachelor",
            "Computer Science",
            "2015-09",
            "2019-06"
        );

        assertDoesNotThrow(() -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Null education should throw exception")
    void testNullEducationShouldThrow() {
        InvalidResumeException ex = assertThrows(
            InvalidResumeException.class,
            () -> EducationValidator.validate(null)
        );
        assertTrue(ex.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Institution too short should throw exception")
    void testShortInstitutionShouldThrow() {
        Education education = new Education(
            "A",  // too short
            "Bachelor",
            "Computer Science",
            "2015-09",
            "2019-06"
        );

        assertThrows(InvalidResumeException.class, () -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Degree too long should throw exception")
    void testLongDegreeShouldThrow() {
        String longDegree = "A".repeat(60);
        Education education = new Education(
            "MIT",
            longDegree,
            "Computer Science",
            "2015-09",
            "2019-06"
        );

        assertThrows(InvalidResumeException.class, () -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Field of study too short should throw exception")
    void testShortFieldOfStudyShouldThrow() {
        Education education = new Education(
            "MIT",
            "Bachelor",
            "A",  // too short
            "2015-09",
            "2019-06"
        );

        assertThrows(InvalidResumeException.class, () -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Invalid date format should throw exception")
    void testInvalidDateFormatShouldThrow() {
        Education education = new Education(
            "MIT",
            "Bachelor",
            "Computer Science",
            "2015",  // invalid
            "2019-06"
        );

        assertThrows(InvalidResumeException.class, () -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("End date before start date should throw exception")
    void testEndDateBeforeStartDateShouldThrow() {
        Education education = new Education(
            "MIT",
            "Bachelor",
            "Computer Science",
            "2019-06",
            "2015-09"
        );

        assertThrows(InvalidResumeException.class, () -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Null end date should pass")
    void testNullEndDateShouldPass() {
        Education education = new Education(
            "MIT",
            "Bachelor",
            "Computer Science",
            "2015-09",
            null  // still studying or ongoing
        );

        assertDoesNotThrow(() -> EducationValidator.validate(education));
    }

    @Test
    @DisplayName("Null field of study should pass")
    void testNullFieldOfStudyShouldPass() {
        Education education = new Education(
            "Harvard",
            "Master",
            null,
            "2018-01",
            "2020-12"
        );

        assertDoesNotThrow(() -> EducationValidator.validate(education));
    }
}
