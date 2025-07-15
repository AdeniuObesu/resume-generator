package org.adeniuobesu.resumegenerator.core.models;

import java.util.List;

public record WorkExperience(
    String companyName,
    String jobTitle,
    String startDate,
    String endDate,
    List<String> keyAchievements
) {}