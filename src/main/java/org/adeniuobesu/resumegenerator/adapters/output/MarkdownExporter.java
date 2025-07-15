package org.adeniuobesu.resumegenerator.adapters.output;

import org.adeniuobesu.resumegenerator.adapters.exceptions.AdapterException;
import org.adeniuobesu.resumegenerator.adapters.models.OutputType;
import org.adeniuobesu.resumegenerator.application.dtos.ContactMethodDto;
import org.adeniuobesu.resumegenerator.application.dtos.EducationDto;
import org.adeniuobesu.resumegenerator.application.dtos.HobbyDto;
import org.adeniuobesu.resumegenerator.application.dtos.LanguageDto;
import org.adeniuobesu.resumegenerator.application.dtos.ResumeDto;
import org.adeniuobesu.resumegenerator.application.dtos.SkillCategoryDto;
import org.adeniuobesu.resumegenerator.application.dtos.WorkExperienceDto;
import org.adeniuobesu.resumegenerator.application.ports.OutputStrategy;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class MarkdownExporter implements OutputStrategy<ResumeDto> {
    private static final OutputType OUTPUT_TYPE = OutputType.MARKDOWN;

    @Override
    public void generate(ResumeDto resume, OutputStream outputStream) throws AdapterException {
        try {
            validateInput(resume, outputStream);
            String markdown = buildMarkdown(resume);
            writeToStream(markdown, outputStream);
        } catch (IllegalArgumentException e) {
            throw new AdapterException("Invalid input parameters: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new AdapterException(
                "Failed to generate MARKDOWN output to stream: " + e.getMessage(), 
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

    private void writeToStream(String markdown, OutputStream outputStream) throws IOException {
        outputStream.write(markdown.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    private String buildMarkdown(ResumeDto resume) {
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

    private String formatContactMethods(List<ContactMethodDto> methods) {
        return methods.stream()
            .map(m -> "- **" + m.type() + "**: " + m.value())
            .collect(Collectors.joining("\n"));
    }

    private String formatWorkExperiences(List<WorkExperienceDto> experiences) {
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

    private String formatEducation(List<EducationDto> education) {
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

    private String formatSkills(List<SkillCategoryDto> skills) {
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

    private String formatLanguages(List<LanguageDto> languages) {
        return languages.stream()
            .map(lang -> "- " + lang.language() + " (" + lang.proficiency() + ")")
            .collect(Collectors.joining("\n"));
    }

    private String formatHobbies(List<HobbyDto> hobbies) {
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