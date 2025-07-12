package org.adeniuobesu.adapters.input;

import org.adeniuobesu.application.ports.InputStrategy;
import org.adeniuobesu.adapters.exceptions.JsonInputException;
import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.exceptions.InputProcessingException;
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