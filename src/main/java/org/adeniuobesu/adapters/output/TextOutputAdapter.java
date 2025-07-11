package org.adeniuobesu.adapters.output;

import org.adeniuobesu.core.models.*;
import org.adeniuobesu.core.ports.OutputPort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class TextOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;
    private static final String SECTION_BREAK = "\n" + "-".repeat(80) + "\n";
    private static final String SUBSECTION_BREAK = "\n" + "~".repeat(60) + "\n";

    public TextOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume resume) {
        try {
            String text = buildTextResume(resume);
            Files.write(outputPath, text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate text file", e);
        }
    }

    private String buildTextResume(Resume resume) {
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

    private String formatContactMethods(List<ContactMethod> methods) {
        return methods.stream()
            .map(m -> String.format("%-10s: %s", m.type(), m.value()))
            .collect(Collectors.joining("\n"));
    }

    private String formatWorkExperiences(List<WorkExperience> experiences) {
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

    private String formatEducation(List<Education> education) {
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

    private String formatSkills(List<SkillCategory> skills) {
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

    private String formatLanguages(List<Language> languages) {
        return languages.stream()
            .map(lang -> String.format("%-15s (%s)", 
                lang.language(), 
                lang.proficiency().toString().toLowerCase()))
            .collect(Collectors.joining("\n"));
    }

    private String formatHobbies(List<Hobby> hobbies) {
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