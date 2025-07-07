package org.adeniuobesu.core.models;

public record ContactMethod(
    ContactType type,
    String value
) {}