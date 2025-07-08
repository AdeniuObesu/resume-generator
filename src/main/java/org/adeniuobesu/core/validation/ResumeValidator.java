package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.models.Resume;

public final class ResumeValidator {
    // Configuration
    private static final int MAX_SUMMARY_LENGTH = 500;
    private static final int MIN_RESUME_ITEMS = 1;
    private static final int MIN_CONTACT_METHODS = 1;

    public static void validate(Resume resume) {
        ValidationUtils.requireNonNull(resume, "Resume");
        validateRequiredFields(resume);
        validateCollections(resume);

        // Deep validation (order matches model field order)
        resume.contactMethods().forEach(ContactMethodValidator::validate);
        resume.workExperiences().forEach(WorkExperienceValidator::validate);
        resume.educationHistory().forEach(EducationValidator::validate);
        resume.skillCategories().forEach(SkillCategoryValidator::validate);
        resume.languages().forEach(LanguageValidator::validate);
        resume.hobbies().forEach(HobbyValidator::validate);
        
        // Soft skills (explicit null check)
        if (resume.softSkills() != null && !resume.softSkills().isEmpty()) {
            SoftSkillsValidator.validate(resume.softSkills());
        }
    }

    private static void validateRequiredFields(Resume resume) {
        ValidationUtils.validateString(resume.fullName(), "Full name", 2, 100);
        ValidationUtils.validateString(resume.professionalTitle(), "Professional title", 5, 100);
        
        if (resume.professionalSummary() != null) {
            ValidationUtils.validateString(
                resume.professionalSummary(), 
                "Professional summary", 
                10, 
                MAX_SUMMARY_LENGTH
            );
        }
    }

    private static void validateCollections(Resume resume) {
        // Required collections
        ValidationUtils.validateMinSize(
            resume.contactMethods(), 
            MIN_CONTACT_METHODS,
            "Contact methods"
        );
        ValidationUtils.validateMinSize(
            resume.workExperiences(),
            MIN_RESUME_ITEMS,
            "Work experiences"
        );

        // Optional collections
        if (!resume.educationHistory().isEmpty()) {
            ValidationUtils.validateMinSize(
                resume.educationHistory(), 
                MIN_RESUME_ITEMS, 
                "Education history"
            );
        }
        if (!resume.languages().isEmpty()) {
            ValidationUtils.validateMinSize(
                resume.languages(), 
                MIN_RESUME_ITEMS, 
                "Languages"
            );
        }
    }
}