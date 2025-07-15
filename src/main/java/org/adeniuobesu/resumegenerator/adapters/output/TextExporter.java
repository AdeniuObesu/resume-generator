package org.adeniuobesu.resumegenerator.adapters.output;

import org.adeniuobesu.resumegenerator.adapters.exceptions.AdapterException;
import org.adeniuobesu.resumegenerator.adapters.models.OutputType;
import org.adeniuobesu.resumegenerator.application.ports.OutputStrategy;
import org.adeniuobesu.resumegenerator.application.dtos.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class TextExporter implements OutputStrategy<ResumeDto> {

    private static final String SECTION_BREAK = "\n" + "-".repeat(80) + "\n";
    private static final String SUBSECTION_BREAK = "\n" + "~".repeat(60) + "\n";private static final OutputType OUTPUT_TYPE = OutputType.TEXT;
    
    @Override
    public void generate(ResumeDto resume, OutputStream outputStream) throws AdapterException {
        try {
            validateInput(resume, outputStream);
            String text = buildTextResume(resume);
            writeToStream(text, outputStream);
        } catch (IOException e) {
            throw new AdapterException(
                "Failed to generate TEXT output: " + e.getMessage(),
                e
            );
        }
    }

    private void validateInput(ResumeDto resume, OutputStream outputStream) {
        if (resume == null) {
            throw new IllegalArgumentException("Resume cannot be null");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream cannot be null");
        }
    }

    private void writeToStream(String text, OutputStream outputStream) throws IOException {
        outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    private String buildTextResume(ResumeDto resume) {
        return String.format("""
            %s
            %s
            %s%s
            CONTACT INFORMATION
            %s%s
            PROFESSIONAL EXPERIENCE
            %s%s
            EDUCATION
            %s%s
            TECHNICAL SKILLS
            %s%s
            LANGUAGES
            %s%s
            INTERESTS
            %s
            """,
            centerText(resume.fullName().toUpperCase()),
            centerText(resume.professionalTitle()),
            formatSummary(resume.professionalSummary()), SECTION_BREAK,
            formatContactMethods(resume.contactMethods()), SECTION_BREAK,
            formatWorkExperiences(resume.workExperiences()), SECTION_BREAK,
            formatEducation(resume.educationHistory()), SECTION_BREAK,
            formatSkills(resume.skillCategories()), SECTION_BREAK,
            formatLanguages(resume.languages()), SECTION_BREAK,
            formatHobbies(resume.hobbies())
        );
    }

    private String centerText(String text) {
        return String.format("%" + (40 + text.length()/2) + "s", text);
    }

    private String formatSummary(String summary) {
        return wrapText(summary != null ? summary : "", 80) + "\n";
    }

    private String formatContactMethods(List<ContactMethodDto> methods) {
        return methods.stream()
            .map(m -> String.format("%-10s: %s", m.type(), m.value()))
            .collect(Collectors.joining("\n"));
    }

    private String formatWorkExperiences(List<WorkExperienceDto> experiences) {
        return experiences.stream()
            .map(exp -> String.format("%s\n%s\n%s - %s\n\n%s",
                exp.companyName().toUpperCase(),
                exp.jobTitle(),
                exp.startDate(),
                exp.endDate(),
                formatAchievements(exp.keyAchievements())
            ))
            .collect(Collectors.joining(SUBSECTION_BREAK));
    }

    private String formatAchievements(List<String> achievements) {
        return wrapText(
            achievements.stream()
                .map(a -> "• " + a)
                .collect(Collectors.joining("\n")),
            80
        );
    }

    private String formatEducation(List<EducationDto> education) {
        return education.stream()
            .map(edu -> String.format("%s\n%s%s\n%s - %s",
                edu.institutionName().toUpperCase(),
                edu.degree(),
                edu.fieldOfStudy() != null ? " in " + edu.fieldOfStudy() : "",
                edu.startDate(),
                edu.endDate()
            ))
            .collect(Collectors.joining(SUBSECTION_BREAK));
    }

    private String formatSkills(List<SkillCategoryDto> skills) {
        return skills.stream()
            .map(skill -> String.format("%s:\n%s",
                skill.categoryName().toUpperCase(),
                wrapText(
                    String.join(" • ", skill.skills()),
                    80
                )
            ))
            .collect(Collectors.joining("\n\n"));
    }

    private String formatLanguages(List<LanguageDto> languages) {
        return languages.stream()
            .map(lang -> String.format("%-15s (%s)", 
                lang.language(), 
                lang.proficiency().toString().toLowerCase()))
            .collect(Collectors.joining("\n"));
    }

    private String formatHobbies(List<HobbyDto> hobbies) {
        return hobbies.stream()
            .map(hobby -> String.format("%s%s",
                hobby.name(),
                hobby.description() != null ? ": " + hobby.description() : ""
            ))
            .collect(Collectors.joining("\n"));
    }

    private String wrapText(String text, int lineLength) {
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (pos < text.length()) {
            int end = Math.min(pos + lineLength, text.length());
            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(' ', end);
                if (lastSpace > pos) end = lastSpace;
            }
            sb.append(text, pos, end).append("\n");
            pos = end + (end < text.length() && text.charAt(end) == ' ' ? 1 : 0);
        }
        return sb.toString();
    }
}