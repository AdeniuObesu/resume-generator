package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.adeniuobesu.core.models.SkillCategory;

import java.util.List;

public final class SkillCategoryValidator {
    // Validation constants
    private static final int MIN_CATEGORY_LENGTH = 2;
    private static final int MAX_CATEGORY_LENGTH = 30;
    private static final int MIN_SKILL_LENGTH = 2;
    private static final int MAX_SKILL_LENGTH = 30;
    private static final int MAX_SKILLS_PER_CATEGORY = 15;
    private static final String CATEGORY_FIELD = "Skill category";
    private static final String SKILLS_FIELD = "Skills list";
    private static final String SKILL_FIELD = "Technical skill";

    // Prevent instantiation
    private SkillCategoryValidator() {}

    public static void validate(SkillCategory category) {
        if (category == null) {
            throw new InvalidResumeException(CATEGORY_FIELD, "cannot be null");
        }

        validateCategoryName(category.categoryName());
        validateSkills(category.skills());
    }

    private static void validateCategoryName(String categoryName) {
        ValidationUtils.validateString(
            categoryName,
            CATEGORY_FIELD,
            MIN_CATEGORY_LENGTH,
            MAX_CATEGORY_LENGTH
        );
    }

    private static void validateSkills(List<String> skills) {
        validateSkillsListNotEmpty(skills);
        validateSkillsListSize(skills);
        validateEachSkill(skills);
        validateNoDuplicateSkills(skills);
    }

    private static void validateSkillsListNotEmpty(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            throw new InvalidResumeException(SKILLS_FIELD, "cannot be empty");
        }
    }

    private static void validateSkillsListSize(List<String> skills) {
        if (skills.size() > MAX_SKILLS_PER_CATEGORY) {
            throw new InvalidResumeException(
                SKILLS_FIELD,
                String.format("exceeds maximum of %d skills", MAX_SKILLS_PER_CATEGORY)
            );
        }
    }

    private static void validateEachSkill(List<String> skills) {
        skills.forEach(skill -> 
            ValidationUtils.validateString(
                skill,
                SKILL_FIELD,
                MIN_SKILL_LENGTH,
                MAX_SKILL_LENGTH
            )
        );
    }

    private static void validateNoDuplicateSkills(List<String> skills) {
        long uniqueSkills = skills.stream()
            .map(String::toLowerCase)
            .distinct()
            .count();
        
        if (uniqueSkills != skills.size()) {
            throw new InvalidResumeException(SKILLS_FIELD, "contains duplicate skills");
        }
    }
}