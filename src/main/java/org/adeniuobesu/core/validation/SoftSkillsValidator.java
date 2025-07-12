package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.exceptions.InvalidResumeException;

import java.util.List;

public final class SoftSkillsValidator {
    // Validation constants
    private static final int MIN_SKILL_LENGTH = 3;
    private static final int MAX_SKILL_LENGTH = 30;
    private static final int MAX_SKILLS = 10;
    private static final String SOFT_SKILLS_FIELD = "Soft skills";
    private static final String SOFT_SKILL_FIELD = "Soft skill";

    // Prevent instantiation
    private SoftSkillsValidator() {}

    public static void validate(List<String> softSkills) {
        if (softSkills == null || softSkills.isEmpty()) {
            return;  // Optional field
        }

        validateSkillsListSize(softSkills);
        validateEachSkill(softSkills);
        validateNoDuplicates(softSkills);
    }

    private static void validateSkillsListSize(List<String> skills) {
        if (skills.size() > MAX_SKILLS) {
            throw new InvalidResumeException(
                SOFT_SKILLS_FIELD,
                String.format("exceeds maximum of %d skills", MAX_SKILLS)
            );
        }
    }

    private static void validateEachSkill(List<String> skills) {
        skills.forEach(skill -> 
            ValidationUtils.validateString(
                skill,
                SOFT_SKILL_FIELD,
                MIN_SKILL_LENGTH,
                MAX_SKILL_LENGTH
            )
        );
    }

    private static void validateNoDuplicates(List<String> skills) {
        long uniqueSkills = skills.stream()
            .map(String::toLowerCase)
            .distinct()
            .count();
        
        if (uniqueSkills != skills.size()) {
            throw new InvalidResumeException(
                SOFT_SKILLS_FIELD,
                "contains duplicate skills"
            );
        }
    }
}