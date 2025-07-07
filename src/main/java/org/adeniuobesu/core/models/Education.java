package org.adeniuobesu.core.models;

public record Education(
    String institutionName,
    String degree,
    String fieldOfStudy,
    String startDate,
    String endDate
) {}