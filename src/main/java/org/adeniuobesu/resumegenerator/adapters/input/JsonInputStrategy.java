package org.adeniuobesu.resumegenerator.adapters.input;

import org.adeniuobesu.resumegenerator.application.ports.InputStrategy;
import org.adeniuobesu.resumegenerator.adapters.exceptions.JsonInputException;
import org.adeniuobesu.resumegenerator.application.dtos.ResumeDto;
import org.adeniuobesu.resumegenerator.application.exceptions.InputProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class JsonInputStrategy implements InputStrategy<ResumeDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InputStream inputStream;

    public JsonInputStrategy(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public ResumeDto collectData() throws InputProcessingException {
        try {
            return objectMapper.readValue(inputStream, ResumeDto.class);
        } catch (IOException e) {
            throw new JsonInputException("Failed to parse JSON input", e);
        }
    }
}