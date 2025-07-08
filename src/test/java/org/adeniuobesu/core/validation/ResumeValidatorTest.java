package org.adeniuobesu.core.validation;

import org.adeniuobesu.core.models.*;
import org.adeniuobesu.core.exceptions.InvalidResumeException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ResumeValidatorTest {

    private Resume createValidResume() {
        return new Resume(
            "John Doe", 
            "Software Engineer",
            "Experienced Java developer with 5+ years",
            List.of(new ContactMethod(ContactType.EMAIL, "john@example.com")),
            List.of("Communication", "Teamwork"),
            List.of(createValidWorkExperience()),
            List.of(
                new Education(
                    "Stanford",
                    "BS",
                    "Computer Science",
                    "2016-09",
                    "2020-06"
                )
            ),
            List.of(new SkillCategory("Programming", List.of("Java", "Python"))),
            List.of(new Hobby("Reading", "Technical books")),
            List.of(new Language("English", LanguageProficiency.FLUENT))
        );
    }

    private WorkExperience createValidWorkExperience() {
        return new WorkExperience(
            "Tech Company",
            "Senior Developer",
            "2022-01",
            "2023-12",
            List.of(
                "Led a team of 5 developers to deliver major features",
                "Improved system performance by 40% through optimization"
            )
        );
    }

    @Test
    void validate_shouldAcceptValidResume() {
        assertDoesNotThrow(() -> ResumeValidator.validate(createValidResume()));
    }

    @Test
    void validate_shouldRejectNullResume() {
        assertThrows(InvalidResumeException.class, 
            () -> ResumeValidator.validate(null));
    }

    @Test
    void validate_shouldRejectInvalidContactMethod() {
        Resume invalid = new Resume(
            "John Doe", "Title", "Summary",
            List.of(new ContactMethod(null, "value")),
            List.of(), 
            List.of(createValidWorkExperience()),
            List.of(), List.of(), List.of(), List.of()
        );
        assertThrows(InvalidResumeException.class,
            () -> ResumeValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectMissingWorkExperience() {
        Resume invalid = new Resume(
            "Name", "Title", "Summary",
            List.of(new ContactMethod(ContactType.EMAIL, "test@example.com")),
            List.of(), 
            List.of(), // Missing required work experience
            List.of(), List.of(), List.of(), List.of()
        );
        assertThrows(InvalidResumeException.class,
            () -> ResumeValidator.validate(invalid));
    }

    @Test
    void validate_shouldAcceptEmptyOptionalCollections() {
        Resume valid = new Resume(
            "Name", "Title", null,
            List.of(new ContactMethod(ContactType.EMAIL, "test@example.com")),
            null, 
            List.of(new WorkExperience(
                "Company", 
                "Developer",
                "2020-01", 
                "2023-12",
                List.of("Implemented key features for the core product"))),
            List.of(),
            List.of(),
            List.of(),
            List.of()
        );
        assertDoesNotThrow(() -> ResumeValidator.validate(valid));
    }

    @Test
    void validate_shouldRejectInvalidEducationDates() {
        Resume invalid = new Resume(
            "Name", "Title", "Summary",
            List.of(new ContactMethod(ContactType.EMAIL, "test@example.com")),
            List.of(),
            List.of(createValidWorkExperience()),
            List.of(new Education("Uni", "Degree", "Field", "invalid", "date")),
            List.of(), List.of(), List.of()
        );
        assertThrows(InvalidResumeException.class,
            () -> ResumeValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectEmptyAchievements() {
        Resume invalid = new Resume(
            "Name", "Title", "Summary",
            List.of(new ContactMethod(ContactType.EMAIL, "test@example.com")),
            List.of(),
            List.of(new WorkExperience(
                "Company", 
                "Developer",
                "2020-01", 
                "2023-12",
                List.of())), // Empty achievements
            List.of(), List.of(), List.of(), List.of()
        );
        assertThrows(InvalidResumeException.class,
            () -> ResumeValidator.validate(invalid));
    }

    @Test
    void validate_shouldRejectShortAchievements() {
        Resume invalid = new Resume(
            "Name", "Title", "Summary",
            List.of(new ContactMethod(ContactType.EMAIL, "test@example.com")),
            List.of(),
            List.of(new WorkExperience(
                "Company", 
                "Developer",
                "2020-01", 
                "2023-12",
                List.of("Too short"))), // Achievement too short
            List.of(), List.of(), List.of(), List.of()
        );
        assertThrows(InvalidResumeException.class,
            () -> ResumeValidator.validate(invalid));
    }
}