package org.adeniuobesu.infrastructure.factories;

import org.adeniuobesu.adapters.input.CliInputStrategy;
import org.adeniuobesu.adapters.input.JsonInputStrategy;
import org.adeniuobesu.adapters.models.InputType;
import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.exceptions.InputProcessingException;
import org.adeniuobesu.application.ports.InputStrategy;
import org.adeniuobesu.adapters.exceptions.FileInputException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class InputStrategyFactory {
    private final Scanner cliScanner;

    public InputStrategyFactory(Scanner cliScanner) {
        this.cliScanner = cliScanner;
    }

    public InputStrategy<ResumeDto> create(InputType type, String source) throws InputProcessingException {
        try {
            return switch (type) {
                case CLI -> new CliInputStrategy(cliScanner);
                case JSON -> createJsonInputStrategy(source);
                default -> throw new InputProcessingException("Unsupported input type: " + type);
            };
        } catch (FileInputException e) {
            throw new InputProcessingException(source, e);
        }
    }

    private InputStrategy<ResumeDto> createJsonInputStrategy(String source) throws FileInputException {
        try {
            InputStream stream = Files.newInputStream(Paths.get(source));
            return new JsonInputStrategy(stream);
        } catch (IOException e) {
            throw new FileInputException(source, e);
        }
    }
}
