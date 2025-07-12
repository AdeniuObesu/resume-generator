package org.adeniuobesu.adapters.output;

import org.adeniuobesu.adapters.exceptions.AdapterException;
import org.adeniuobesu.adapters.models.OutputType;
import org.adeniuobesu.application.dtos.*;
import org.adeniuobesu.application.ports.OutputStrategy;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class HtmlExporter implements OutputStrategy<ResumeDto> {
    
    private static final OutputType OUTPUT_TYPE = OutputType.HTML;
    private final String cssFilePath;
    
    public HtmlExporter() {
        this(null);
    }

    public HtmlExporter(String cssFilePath) {
        this.cssFilePath = cssFilePath;
    }

    @Override
    public void generate(ResumeDto resume, OutputStream outputStream) throws AdapterException {
        try {
            validateInput(resume, outputStream);
            String html = buildHtml(resume);
            writeToStream(html, outputStream);
        } catch (IOException e) {
            throw new AdapterException(
                "Failed to generate HTML output: " + e.getMessage(),
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

    private void writeToStream(String html, OutputStream outputStream) throws IOException {
        outputStream.write(html.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    private String buildHtml(ResumeDto resume) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>%s - Resume</title>
                %s
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="resume-container">
                    <header class="resume-header">
                        <h1>%s</h1>
                        <h2>%s</h2>
                        <p class="summary">%s</p>
                    </header>
                    
                    <div class="resume-body">
                        <section class="contact-section">
                            <h3>Contact Information</h3>
                            <ul class="contact-list">
                                %s
                            </ul>
                        </section>
                        
                        <section class="experience-section">
                            <h3>Professional Experience</h3>
                            %s
                        </section>
                        
                        <section class="education-section">
                            <h3>Education</h3>
                            %s
                        </section>
                        
                        <section class="skills-section">
                            <h3>Technical Skills</h3>
                            %s
                        </section>
                        
                        <section class="languages-section">
                            <h3>Languages</h3>
                            %s
                        </section>
                        
                        <section class="interests-section">
                            <h3>Interests</h3>
                            %s
                        </section>
                    </div>
                </div>
                
                <script>
                    function toggleTheme() {
                        document.body.classList.toggle('dark-mode');
                        localStorage.setItem('theme', 
                            document.body.classList.contains('dark-mode') ? 'dark' : 'light');
                    }
                    
                    // Load saved theme
                    if (localStorage.getItem('theme') === 'dark') {
                        document.body.classList.add('dark-mode');
                    }
                </script>
            </body>
            </html>
            """,
            resume.fullName(),
            getCssLink(),
            getDefaultCss(),
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary() != null ? resume.professionalSummary() : "",
            formatContactMethods(resume.contactMethods()),
            formatWorkExperiences(resume.workExperiences()),
            formatEducation(resume.educationHistory()),
            formatSkills(resume.skillCategories()),
            formatLanguages(resume.languages()),
            formatHobbies(resume.hobbies())
        );
    }

    private String getCssLink() {
        return cssFilePath != null ? 
            String.format("<link rel=\"stylesheet\" href=\"%s\">", cssFilePath) : "";
    }

    private String getDefaultCss() {
        return """
            :root {
                --primary-color: #2c3e50;
                --secondary-color: #3498db;
                --text-color: #333;
                --bg-color: #f9f9f9;
                --section-bg: #fff;
                --border-color: #e0e0e0;
            }
            
            .dark-mode {
                --primary-color: #3498db;
                --secondary-color: #2c3e50;
                --text-color: #f0f0f0;
                --bg-color: #121212;
                --section-bg: #1e1e1e;
                --border-color: #444;
            }
            
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                line-height: 1.6;
                color: var(--text-color);
                background-color: var(--bg-color);
                margin: 0;
                padding: 20px;
                transition: all 0.3s ease;
            }
            
            .resume-container {
                max-width: 800px;
                margin: 0 auto;
                background: var(--section-bg);
                padding: 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
                border-radius: 8px;
            }
            
            .resume-header {
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 2px solid var(--secondary-color);
                padding-bottom: 20px;
            }
            
            h1 {
                color: var(--primary-color);
                margin-bottom: 5px;
            }
            
            h2 {
                color: var(--secondary-color);
                font-weight: normal;
                margin-top: 0;
            }
            
            h3 {
                color: var(--primary-color);
                border-bottom: 1px solid var(--border-color);
                padding-bottom: 5px;
            }
            
            section {
                margin-bottom: 25px;
            }
            
            .contact-list {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                list-style: none;
                padding: 0;
            }
            
            .contact-list li {
                background: var(--secondary-color);
                color: white;
                padding: 5px 10px;
                border-radius: 4px;
                font-size: 0.9em;
            }
            
            .job {
                margin-bottom: 20px;
            }
            
            .job-header {
                display: flex;
                justify-content: space-between;
                margin-bottom: 5px;
            }
            
            .job-title {
                font-weight: bold;
                color: var(--primary-color);
            }
            
            .job-date {
                color: #666;
            }
            
            .achievements {
                padding-left: 20px;
            }
            
            .achievements li {
                margin-bottom: 5px;
            }
            
            .skills-container {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                gap: 15px;
            }
            
            .skill-category {
                margin-bottom: 15px;
            }
            
            .skill-items {
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
            }
            
            .skill-item {
                background: var(--secondary-color);
                color: white;
                padding: 3px 8px;
                border-radius: 3px;
                font-size: 0.85em;
            }
            
            .theme-toggle {
                position: fixed;
                top: 20px;
                right: 20px;
                background: var(--secondary-color);
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 4px;
                cursor: pointer;
            }
            """;
    }

    private String formatContactMethods(List<ContactMethodDto> methods) {
        return methods.stream()
            .map(m -> String.format("<li><strong>%s:</strong> %s</li>", 
                m.type(), 
                m.type() == ContactTypeDto.EMAIL ? 
                    String.format("<a href=\"mailto:%s\">%s</a>", m.value(), m.value()) :
                    m.type() == ContactTypeDto.LINKEDIN || m.type() == ContactTypeDto.GITHUB ?
                    String.format("<a href=\"%s\" target=\"_blank\">%s</a>", m.value(), m.value()) :
                    m.value()))
            .collect(Collectors.joining("\n"));
    }

    private String formatWorkExperiences(List<WorkExperienceDto> experiences) {
        return experiences.stream()
            .map(exp -> String.format("""
                <div class="job">
                    <div class="job-header">
                        <span class="job-title">%s</span>
                        <span class="job-date">%s - %s</span>
                    </div>
                    <div class="job-company">%s</div>
                    <ul class="achievements">
                        %s
                    </ul>
                </div>
                """,
                exp.jobTitle(),
                exp.startDate(),
                exp.endDate(),
                exp.companyName(),
                exp.keyAchievements().stream()
                    .map(a -> "<li>" + a + "</li>")
                    .collect(Collectors.joining("\n"))
            ))
            .collect(Collectors.joining("\n"));
    }

    private String formatEducation(List<EducationDto> education) {
        return education.stream()
            .map(edu -> String.format("""
                <div class="education">
                    <div class="education-header">
                        <span class="degree">%s%s</span>
                        <span class="education-date">%s - %s</span>
                    </div>
                    <div class="institution">%s</div>
                </div>
                """,
                edu.degree(),
                edu.fieldOfStudy() != null ? " in " + edu.fieldOfStudy() : "",
                edu.startDate(),
                edu.endDate(),
                edu.institutionName()
            ))
            .collect(Collectors.joining("\n"));
    }

    private String formatSkills(List<SkillCategoryDto> skills) {
        return String.format("""
            <div class="skills-container">
                %s
            </div>
            """,
            skills.stream()
                .map(skill -> String.format("""
                    <div class="skill-category">
                        <h4>%s</h4>
                        <div class="skill-items">
                            %s
                        </div>
                    </div>
                    """,
                    skill.categoryName(),
                    skill.skills().stream()
                        .map(s -> String.format("<span class=\"skill-item\">%s</span>", s))
                        .collect(Collectors.joining("\n"))
                ))
                .collect(Collectors.joining("\n"))
        );
    }

    private String formatLanguages(List<LanguageDto> languages) {
        return String.format("""
            <div class="languages-container">
                %s
            </div>
            """,
            languages.stream()
                .map(lang -> String.format("""
                    <div class="language">
                        <span class="language-name">%s</span>
                        <span class="language-proficiency">(%s)</span>
                    </div>
                    """,
                    lang.language(),
                    lang.proficiency().toString().toLowerCase()
                ))
                .collect(Collectors.joining("\n"))
        );
    }

    private String formatHobbies(List<HobbyDto> hobbies) {
        return hobbies.stream()
            .map(hobby -> String.format("""
                <div class="hobby">
                    <strong>%s</strong>%s
                </div>
                """,
                hobby.name(),
                hobby.description() != null ? ": " + hobby.description() : ""
            ))
            .collect(Collectors.joining("\n"));
    }
}