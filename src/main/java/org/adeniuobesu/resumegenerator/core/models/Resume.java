package org.adeniuobesu.resumegenerator.core.models;

import java.util.List;

public record Resume(
    String fullName,
    String professionalTitle,
    String professionalSummary,
    List<ContactMethod> contactMethods,
    List<String> softSkills,
    List<WorkExperience> workExperiences,
    List<Education> educationHistory,
    List<SkillCategory> skillCategories,
    List<Hobby> hobbies,
    List<Language> languages
) {}