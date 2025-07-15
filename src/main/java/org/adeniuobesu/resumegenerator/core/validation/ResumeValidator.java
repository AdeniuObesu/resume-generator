package org.adeniuobesu.resumegenerator.core.validation;

import java.util.List;

import org.adeniuobesu.resumegenerator.core.models.Resume;

public final class ResumeValidator {
    // Configuration constants
    private static final int MAX_SUMMARY_LENGTH = 500;
    private static final int MIN_RESUME_ITEMS = 1;
    private static final int MIN_CONTACT_METHODS = 1;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_SUMMARY_LENGTH = 10;

    // Field name constants
    private static final String RESUME_FIELD = "Resume";
    private static final String FULL_NAME_FIELD = "Full name";
    private static final String PROFESSIONAL_TITLE_FIELD = "Professional title";
    private static final String PROFESSIONAL_SUMMARY_FIELD = "Professional summary";
    private static final String CONTACT_METHODS_FIELD = "Contact methods";
    private static final String WORK_EXPERIENCES_FIELD = "Work experiences";
    private static final String EDUCATION_HISTORY_FIELD = "Education history";
    private static final String LANGUAGES_FIELD = "Languages";

    public ResumeValidator() {}

    public void validate(Resume resume) {
        validateRequiredFields(resume);
        validateCollections(resume);
        validateNestedEntities(resume);
    }

    private static void validateRequiredFields(Resume resume) {
        ValidationUtils.requireNonNull(resume, RESUME_FIELD);
        
        ValidationUtils.validateString(
            resume.fullName(),
            FULL_NAME_FIELD,
            MIN_NAME_LENGTH,
            MAX_NAME_LENGTH
        );
        
        ValidationUtils.validateString(
            resume.professionalTitle(),
            PROFESSIONAL_TITLE_FIELD,
            MIN_TITLE_LENGTH,
            MAX_TITLE_LENGTH
        );
        
        if (resume.professionalSummary() != null) {
            ValidationUtils.validateString(
                resume.professionalSummary(),
                PROFESSIONAL_SUMMARY_FIELD,
                MIN_SUMMARY_LENGTH,
                MAX_SUMMARY_LENGTH
            );
        }
    }

    private static void validateCollections(Resume resume) {
        ValidationUtils.validateMinSize(
            resume.contactMethods(),
            MIN_CONTACT_METHODS,
            CONTACT_METHODS_FIELD
        );
        
        ValidationUtils.validateMinSize(
            resume.workExperiences(),
            MIN_RESUME_ITEMS,
            WORK_EXPERIENCES_FIELD
        );
        
        validateOptionalCollection(resume.educationHistory(), EDUCATION_HISTORY_FIELD);
        validateOptionalCollection(resume.languages(), LANGUAGES_FIELD);
    }

    private static void validateOptionalCollection(List<?> collection, String fieldName) {
        if (collection != null && !collection.isEmpty()) {
            ValidationUtils.validateMinSize(
                collection,
                MIN_RESUME_ITEMS,
                fieldName
            );
        }
    }

    private static void validateNestedEntities(Resume resume) {
        resume.contactMethods().forEach(ContactMethodValidator::validate);
        resume.workExperiences().forEach(WorkExperienceValidator::validate);
        
        if (resume.educationHistory() != null) {
            resume.educationHistory().forEach(EducationValidator::validate);
        }
        
        if (resume.skillCategories() != null) {
            resume.skillCategories().forEach(SkillCategoryValidator::validate);
        }
        
        if (resume.languages() != null) {
            resume.languages().forEach(LanguageValidator::validate);
        }
        
        if (resume.hobbies() != null) {
            resume.hobbies().forEach(HobbyValidator::validate);
        }
        
        if (resume.softSkills() != null) {
            SoftSkillsValidator.validate(resume.softSkills());
        }
    }
}