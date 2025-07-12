package org.adeniuobesu.application.dtos;

import java.util.List;

public record ResumeDto(
    String fullName,
    String professionalTitle,
    String professionalSummary,
    List<ContactMethodDto> contactMethods,
    List<String> softSkills,
    List<WorkExperienceDto> workExperiences,
    List<EducationDto> educationHistory,
    List<SkillCategoryDto> skillCategories,
    List<HobbyDto> hobbies,
    List<LanguageDto> languages
) {}