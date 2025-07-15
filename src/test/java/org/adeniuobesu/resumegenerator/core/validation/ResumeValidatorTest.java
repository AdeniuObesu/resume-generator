package org.adeniuobesu.resumegenerator.core.validation;

import org.adeniuobesu.resumegenerator.core.exceptions.InvalidResumeException;
import org.adeniuobesu.resumegenerator.core.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResumeValidatorTest {

    private ResumeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ResumeValidator();
    }

    private Resume createValidResume() {
        return new Resume(
            "John Doe",
            "Senior Software Engineer",
            "Passionate software engineer with 10+ years of experience building scalable applications.",
            List.of(new ContactMethod(ContactType.EMAIL, "john@example.com")),
            List.of("Teamwork", "Adaptability"),
            List.of(new WorkExperience("TechCorp", "Lead Developer", "2015-01", "2020-12", List.of("Built microservices"))),
            List.of(new Education("MIT", "BSc", "Computer Science", "2010-09", "2014-06")),
            List.of(new SkillCategory("Programming", List.of("Java", "Python"))),
            List.of(new Hobby("Hiking", "Mountain hiking every weekend")),
            List.of(new Language("English", LanguageProficiency.NATIVE))
        );
    }

    @Test
    void testValidResumeDoesNotThrow() {
        Resume resume = createValidResume();
        assertDoesNotThrow(() -> validator.validate(resume));
    }

    @Test
    void testNullResumeThrows() {
        assertThrows(InvalidResumeException.class, () -> validator.validate(null));
    }

    @Test
    void testInvalidFullNameTooShortThrows() {
        Resume resume = createValidResume();
        Resume invalid = new Resume(
            "J",
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testFullNameTooLongThrows() {
        Resume resume = createValidResume();
        String tooLongName = "A".repeat(101);
        Resume invalid = new Resume(
            tooLongName,
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testProfessionalTitleTooShortThrows() {
        Resume resume = createValidResume();
        Resume invalid = new Resume(
            resume.fullName(),
            "Dev", // shorter than expected min length
            resume.professionalSummary(),
            resume.contactMethods(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testMissingContactMethodsThrows() {
        Resume resume = createValidResume();
        Resume invalid = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary(),
            Collections.emptyList(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testMissingWorkExperienceThrows() {
        Resume resume = createValidResume();
        Resume invalid = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods(),
            resume.softSkills(),
            Collections.emptyList(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testProfessionalSummaryTooShortThrows() {
        Resume resume = createValidResume();
        Resume invalid = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            "Short", // summary less than minimum length (ex: 10)
            resume.contactMethods(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertThrows(InvalidResumeException.class, () -> validator.validate(invalid));
    }

    @Test
    void testNullProfessionalSummaryIsAllowed() {
        Resume resume = createValidResume();
        Resume modified = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            null, // summary can be null
            resume.contactMethods(),
            resume.softSkills(),
            resume.workExperiences(),
            resume.educationHistory(),
            resume.skillCategories(),
            resume.hobbies(),
            resume.languages()
        );
        assertDoesNotThrow(() -> validator.validate(modified));
    }

    @Test
    void testOptionalCollectionsCanBeNullOrEmpty() {
        Resume resume = createValidResume();
        Resume modified = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods(),
            null, // softSkills null
            resume.workExperiences(),
            null, // educationHistory null
            null, // skillCategories null
            null, // hobbies null
            null  // languages null
        );
        assertDoesNotThrow(() -> validator.validate(modified));
    }

    @Test
    void testOptionalCollectionWithEmptyListIsAllowed() {
        Resume resume = createValidResume();
        Resume modified = new Resume(
            resume.fullName(),
            resume.professionalTitle(),
            resume.professionalSummary(),
            resume.contactMethods(),
            List.of(), // empty softSkills
            resume.workExperiences(),
            List.of(), // empty educationHistory
            List.of(), // empty skillCategories
            List.of(), // empty hobbies
            List.of()  // empty languages
        );
        assertDoesNotThrow(() -> validator.validate(modified));
    }
}
