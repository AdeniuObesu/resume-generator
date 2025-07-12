package org.adeniuobesu.application.dtos;

public record LanguageDto(
    String language,
    LanguageProficiencyDto proficiency
) {}