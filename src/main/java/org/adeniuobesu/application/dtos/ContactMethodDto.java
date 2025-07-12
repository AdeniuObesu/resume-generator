package org.adeniuobesu.application.dtos;

public record ContactMethodDto(
    ContactTypeDto type,
    String value
) {
}