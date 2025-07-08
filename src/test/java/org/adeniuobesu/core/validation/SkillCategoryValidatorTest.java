package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.adeniuobesu.core.models.SkillCategory;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SkillCategoryValidatorTest {

    @Test
    void validate_shouldAcceptValidCategory() {
        SkillCategory valid = new SkillCategory("Programming", List.of("Java", "Python"));
        assertDoesNotThrow(() -> SkillCategoryValidator.validate(valid));
    }

    @Test
    void validate_shouldRejectNullCategory() {
        assertThrows(InvalidResumeException.class, 
            () -> SkillCategoryValidator.validate(null));
    }

    @Test
    void validate_shouldRejectShortCategoryName() {
        SkillCategory invalid = new SkillCategory("A", List.of("Java"));
        assertThrows(InvalidResumeException.class,
            () -> SkillCategoryValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectLongCategoryName() {
        SkillCategory invalid = new SkillCategory("ExtremelyLongCategoryNameExceedingLimit", List.of("Java"));
        assertThrows(InvalidResumeException.class,
            () -> SkillCategoryValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectEmptySkillsList() {
        SkillCategory invalid = new SkillCategory("Languages", List.of());
        assertThrows(InvalidResumeException.class,
            () -> SkillCategoryValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectTooManySkills() {
        List<String> manySkills = List.of("S1","S2","S3","S4","S5","S6","S7","S8",
                                         "S9","S10","S11","S12","S13","S14","S15","S16");
        SkillCategory invalid = new SkillCategory("Tools", manySkills);
        assertThrows(InvalidResumeException.class,
            () -> SkillCategoryValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectDuplicateSkills() {
        SkillCategory invalid = new SkillCategory("DB", List.of("MySQL", "mysql"));
        assertThrows(InvalidResumeException.class,
            () -> SkillCategoryValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectInvalidSkillNames() {
        SkillCategory invalidShort = new SkillCategory("Cloud", List.of("A"));
        SkillCategory invalidLong = new SkillCategory("Cloud", List.of("ExtremelyLongSkillNameExceedingLimit"));

        assertAll(
            () -> assertThrows(InvalidResumeException.class,
                () -> SkillCategoryValidator.validate(invalidShort)),
            () -> assertThrows(InvalidResumeException.class,
                () -> SkillCategoryValidator.validate(invalidLong))
        );
    }
}