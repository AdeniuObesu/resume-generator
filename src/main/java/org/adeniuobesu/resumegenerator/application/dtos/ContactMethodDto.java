package org.adeniuobesu.resumegenerator.application.dtos;

public record ContactMethodDto(
    ContactTypeDto type,
    String value
) {
}