package org.adeniuobesu.application.dtos;

import java.util.List;

public record WorkExperienceDto(
    String companyName,
    String jobTitle,
    String startDate,
    String endDate,
    List<String> keyAchievements
) {}