package org.adeniuobesu.resumegenerator.application.dtos;

import java.util.List;

public record SkillCategoryDto(
    String categoryName,  // e.g., "Languages", "Cloud", "DevOps"
    List<String> skills   // e.g., ["Java", "Python"]
) {}