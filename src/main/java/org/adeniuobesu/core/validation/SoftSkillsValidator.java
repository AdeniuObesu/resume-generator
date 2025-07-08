package org.adeniuobesu.core.validation;

import java.util.List;

import org.adeniuobesu.core.exceptions.InvalidResumeException;

public final class SoftSkillsValidator {
    // Validation constants
    private static final int MIN_SKILL_LENGTH = 3;
    private static final int MAX_SKILL_LENGTH = 30;
    private static final int MAX_SKILLS = 10;
    private static final List<String> BANNED_WORDS = List.of(
        "ninja", 
        "rockstar", 
        "guru",
        "wizard",
        "passionate about synergies"  // Reject buzzword overload (unprofessional terms)
    );

    public static void validate(List<String> softSkills) {
        if (softSkills == null) return;  // Optional field

        // Size validation
        if (softSkills.size() > MAX_SKILLS) {
            throw new InvalidResumeException(
                String.format("Maximum %d soft skills allowed (found %d)", 
                    MAX_SKILLS, 
                    softSkills.size())
            );
        }

        // Individual skill validation
        softSkills.forEach(skill -> {
            ValidationUtils.validateString(
                skill,
                "Soft skill",
                MIN_SKILL_LENGTH,
                MAX_SKILL_LENGTH
            );
            
            validateProfessionalTerm(skill);
        });

        // Duplicate check (case-insensitive)
        long uniqueSkills = softSkills.stream()
            .map(String::toLowerCase)
            .distinct()
            .count();
        
        if (uniqueSkills != softSkills.size()) {
            throw new InvalidResumeException("Duplicate soft skills detected");
        }
    }

    private static void validateProfessionalTerm(String skill) {
        BANNED_WORDS.forEach(word -> {
            if (skill.toLowerCase().contains(word)) {
                throw new InvalidResumeException(
                    String.format("Unprofessional term '%s' in skill: %s", 
                        word, 
                        skill)
                );
            }
        });
    }
}