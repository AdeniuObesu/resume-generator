package org.adeniuobesu.resumegenerator.core.models;

import java.util.List;

public record SkillCategory(
    String categoryName,  // e.g., "Languages", "Cloud", "DevOps"
    List<String> skills   // e.g., ["Java", "Python"]
) {}