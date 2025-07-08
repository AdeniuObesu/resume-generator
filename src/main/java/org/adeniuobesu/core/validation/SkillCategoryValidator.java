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

    public static void validate(SkillCategory category) {
        ValidationUtils.requireNonNull(category, "Skill category");

        // Category name validation
        ValidationUtils.validateString(
            category.categoryName(),
            "Skill category name",
            MIN_CATEGORY_LENGTH,
            MAX_CATEGORY_LENGTH
        );

        // Skills validation
        validateSkills(category.skills());
    }

    private static void validateSkills(List<String> skills) {
        ValidationUtils.validateNonEmpty(skills, "Skills list");

        if (skills.size() > MAX_SKILLS_PER_CATEGORY) {
            throw new InvalidResumeException(
                String.format("Maximum %d skills per category allowed (found %d)", 
                    MAX_SKILLS_PER_CATEGORY, 
                    skills.size())
            );
        }

        skills.forEach(skill -> 
            ValidationUtils.validateString(
                skill,
                "Technical skill",
                MIN_SKILL_LENGTH,
                MAX_SKILL_LENGTH
            )
        );

        // Check for duplicates (case-insensitive)
        long uniqueSkills = skills.stream()
            .map(String::toLowerCase)
            .distinct()
            .count();
        
        if (uniqueSkills != skills.size()) {
            throw new InvalidResumeException("Duplicate skills detected in category");
        }
    }
}