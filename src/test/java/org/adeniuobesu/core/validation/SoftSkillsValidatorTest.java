package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SoftSkillsValidatorTest {

    @Test
    void validate_shouldAcceptValidSkills() {
        List<String> validSkills = List.of("Communication", "Teamwork", "Leadership");
        assertDoesNotThrow(() -> SoftSkillsValidator.validate(validSkills));
    }

    @Test
    void validate_shouldAcceptNullInput() {
        assertDoesNotThrow(() -> SoftSkillsValidator.validate(null));
    }

    @Test
    void validate_shouldRejectTooManySkills() {
        List<String> manySkills = List.of("S1","S2","S3","S4","S5","S6","S7","S8","S9","S10","S11");
        assertThrows(InvalidResumeException.class,
            () -> SoftSkillsValidator.validate(manySkills));
    }

    @Test
    void validate_shouldRejectShortSkill() {
        List<String> invalidSkills = List.of("OK");
        assertThrows(InvalidResumeException.class,
            () -> SoftSkillsValidator.validate(invalidSkills));
    }

    @Test
    void validate_shouldRejectLongSkill() {
        List<String> invalidSkills = List.of("ThisIsAnExtremelyLongSoftSkillNameExceedingLimit");
        assertThrows(InvalidResumeException.class,
            () -> SoftSkillsValidator.validate(invalidSkills));
    }

    @Test
    void validate_shouldRejectDuplicates() {
        List<String> invalidSkills = List.of("Problem Solving", "problem solving");
        assertThrows(InvalidResumeException.class,
            () -> SoftSkillsValidator.validate(invalidSkills));
    }
}