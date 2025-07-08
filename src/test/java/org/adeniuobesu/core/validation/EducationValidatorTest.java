package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.adeniuobesu.core.models.Education;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EducationValidatorTest {

    // --- Positive Tests ---
    @Test
    void validate_completeEducation_doesNotThrow() {
        Education valid = new Education(
            "Massachusetts Institute of Technology",
            "PhD",
            "Computer Science",
            "2015-09",
            "2020-06"
        );
        assertDoesNotThrow(() -> EducationValidator.validate(valid));
    }

    @Test
    void validate_currentEducationWithNullEndDate_doesNotThrow() {
        Education current = new Education(
            "Stanford University",
            "MSc",
            "Artificial Intelligence",
            "2022-09",
            null
        );
        assertDoesNotThrow(() -> EducationValidator.validate(current));
    }

    @Test
    void validate_optionalFieldOfStudyNull_doesNotThrow() {
        Education valid = new Education(
            "Harvard University",
            "BA",
            null,  // Null field of study
            "2010-09",
            "2014-06"
        );
        assertDoesNotThrow(() -> EducationValidator.validate(valid));
    }

    // --- Negative Tests ---
    
    // Institution validation
    @Test
    void validate_emptyInstitution_throwsException() {
        Education invalid = new Education(
            "",
            "BSc",
            "Computer Science",
            "2010-09",
            "2014-06"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }
    @Test
    void validate_singleCharInstitution_throwsException() {
        Education invalid = new Education(
            "A",
            "BSc",
            "Computer Science",
            "2010-09",
            "2014-06"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }
    @Test
    void validate_institutionExceedsMaxLength_throwsException() {
        Education invalid = new Education(
            "X".repeat(101),
            "BSc",
            "Computer Science",
            "2010-09",
            "2014-06"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    // Degree validation
    @Test
    void validate_emptyDegree_throwsException() {
        Education invalid = new Education(
            "MIT",
            "",  // Empty degree
            "Physics",
            "2010-09",
            "2014-06"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    // Field of study validation
    @Test
    void validate_invalidFieldOfStudyLength_throwsException() {
        Education invalid = new Education(
            "University of Tokyo",
            "PhD",
            "X",  // Too short
            "2018-04",
            "2022-03"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    // Date validation
    @Test
    void validate_invalidDateFormat_throwsException() {
        Education invalid = new Education(
            "ETH Zurich",
            "MSc",
            "Chemistry",
            "September 2020",  // Invalid format
            "2022-08"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    @Test
    void validate_endDateBeforeStartDate_throwsException() {
        Education invalid = new Education(
            "Sorbonne University",
            "MA",
            "Philosophy",
            "2019-09",  // Start
            "2018-06"   // End before start
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    // Null checks
    @Test
    void validate_nullEducation_throwsException() {
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(null));
    }

    @Test
    void validate_nullInstitution_throwsException() {
        Education invalid = new Education(
            null,
            "BSc",
            "Mathematics",
            "2015-09",
            "2019-06"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    @Test
    void validate_nullDegree_throwsException() {
        Education invalid = new Education(
            "University of Cambridge",
            null,
            "History",
            "2011-10",
            "2015-07"
        );
        assertThrows(InvalidResumeException.class,
            () -> EducationValidator.validate(invalid));
    }

    @Test
    void validate_nullStartDate_throwsException() {
        Education invalid = new Education(
            "TU Munich",
            "Diploma",
            "Engineering",
            null,
            "2021-03"
        );
        assertThrows(NullPointerException.class,
            () -> EducationValidator.validate(invalid));
    }
}