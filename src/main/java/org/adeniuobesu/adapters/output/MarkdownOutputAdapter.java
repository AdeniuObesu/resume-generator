package org.adeniuobesu.adapters.output;

import org.adeniuobesu.core.models.*;
import org.adeniuobesu.core.ports.OutputPort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class MarkdownOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;

    public MarkdownOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume resume) {
        try {
            String markdown = buildMarkdown(resume);
            Files.write(outputPath, markdown.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Markdown file", e);
        }
    }

    private String buildMarkdown(Resume resume) {
        return String.format("""
            # %s
            ## %s
            
            %s
            
            ## Contact Information
            %s
            
            ## Professional Experience
            %s
            
            ## Education
            %s
            
            ## Technical Skills
            %s
            
            ## Languages
            %s
            
            ## Interests
            %s
            """,
            resume.fullName(),
            resume.professionalTitle(),
            formatSummary(resume.professionalSummary()),
            formatContactMethods(resume.contactMethods()),
            formatWorkExperiences(resume.workExperiences()),
            formatEducation(resume.educationHistory()),
            formatSkills(resume.skillCategories()),
            formatLanguages(resume.languages()),
            formatHobbies(resume.hobbies())
        );
    }

    private String formatSummary(String summary) {
        return summary != null ? summary + "\n" : "";
    }

    private String formatContactMethods(List<ContactMethod> methods) {
        return methods.stream()
            .map(m -> "- **" + m.type() + "**: " + m.value())
            .collect(Collectors.joining("\n"));
    }

    private String formatWorkExperiences(List<WorkExperience> experiences) {
        return experiences.stream()
            .map(exp -> String.format("""
                ### %s
                **%s** | %s - %s
                
                %s
                """,
                exp.companyName(),
                exp.jobTitle(),
                exp.startDate(),
                exp.endDate(),
                formatAchievements(exp.keyAchievements())
            ))
            .collect(Collectors.joining("\n"));
    }

    private String formatAchievements(List<String> achievements) {
        return achievements.stream()
            .map(a -> "- " + a)
            .collect(Collectors.joining("\n"));
    }

    private String formatEducation(List<Education> education) {
        return education.stream()
            .map(edu -> String.format("""
                ### %s
                **%s in %s** | %s - %s
                """,
                edu.institutionName(),
                edu.degree(),
                edu.fieldOfStudy() != null ? edu.fieldOfStudy() : "",
                edu.startDate(),
                edu.endDate()
            ))
            .collect(Collectors.joining("\n"));
    }

    private String formatSkills(List<SkillCategory> skills) {
        return skills.stream()
            .map(skill -> String.format("""
                ### %s
                %s
                """,
                skill.categoryName(),
                skill.skills().stream()
                    .map(s -> "- " + s)
                    .collect(Collectors.joining("\n"))
            ))
            .collect(Collectors.joining("\n"));
    }

    private String formatLanguages(List<Language> languages) {
        return languages.stream()
            .map(lang -> "- " + lang.language() + " (" + lang.proficiency() + ")")
            .collect(Collectors.joining("\n"));
    }

    private String formatHobbies(List<Hobby> hobbies) {
        return hobbies.stream()
            .map(hobby -> String.format("""
                - **%s**: %s
                """,
                hobby.name(),
                hobby.description() != null ? hobby.description() : ""
            ))
            .collect(Collectors.joining("\n"));
    }
}