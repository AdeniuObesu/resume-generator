package org.adeniuobesu.resumegenerator.application.mappers;

import org.adeniuobesu.resumegenerator.core.models.*;
import org.adeniuobesu.resumegenerator.application.dtos.*;

public class ResumeMapper {

    public static Resume toDomain(ResumeDto dto) {
        return new Resume(
            dto.fullName(),
            dto.professionalTitle(),
            dto.professionalSummary(),
            dto.contactMethods().stream()
                .map(c -> new ContactMethod(ContactType.valueOf(c.type().name()), c.value()))
                .toList(),
            dto.softSkills(),
            dto.workExperiences().stream()
                .map(w -> new WorkExperience(
                    w.companyName(),
                    w.jobTitle(),
                    w.startDate(),
                    w.endDate(),
                    w.keyAchievements()
                )).toList(),
            dto.educationHistory().stream()
                .map(e -> new Education(
                    e.institutionName(),
                    e.degree(),
                    e.fieldOfStudy(),
                    e.startDate(),
                    e.endDate()
                )).toList(),
            dto.skillCategories().stream()
                .map(s -> new SkillCategory(s.categoryName(), s.skills()))
                .toList(),
            dto.hobbies().stream()
                .map(h -> new Hobby(h.name(), h.description()))
                .toList(),
            dto.languages().stream()
                .map(l -> new Language(l.language(), LanguageProficiency.valueOf(l.proficiency().name())))
                .toList()
        );
    }

    public static ResumeDto toDto(Resume resume) {
        return new ResumeDto(
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods().stream()
                .map(c -> new ContactMethodDto(ContactTypeDto.valueOf(c.type().name()), c.value()))
                .toList(),
            resume.softSkills(),
            resume.workExperiences().stream()
                .map(w -> new WorkExperienceDto(
                    w.companyName(),
                    w.jobTitle(),
                    w.startDate(),
                    w.endDate(),
                    w.keyAchievements()
                )).toList(),
            resume.educationHistory().stream()
                .map(e -> new EducationDto(
                    e.institutionName(),
                    e.degree(),
                    e.fieldOfStudy(),
                    e.startDate(),
                    e.endDate()
                )).toList(),
            resume.skillCategories().stream()
                .map(s -> new SkillCategoryDto(s.categoryName(), s.skills()))
                .toList(),
            resume.hobbies().stream()
                .map(h -> new HobbyDto(h.name(), h.description()))
                .toList(),
            resume.languages().stream()
                .map(l -> new LanguageDto(l.language(), LanguageProficiencyDto.valueOf(l.proficiency().name())))
                .toList()
        );
    }
}
