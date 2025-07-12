package org.adeniuobesu.application.dtos;

public record EducationDto(
    String institutionName,
    String degree,
    String fieldOfStudy,
    String startDate,
    String endDate
) {}