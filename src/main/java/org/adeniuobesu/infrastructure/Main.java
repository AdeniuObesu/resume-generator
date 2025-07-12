package org.adeniuobesu.infrastructure;

import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.exceptions.ResumeGenerationException;
import org.adeniuobesu.application.ports.InputStrategy;
import org.adeniuobesu.application.ports.OutputStrategy;
import org.adeniuobesu.application.usecases.BuildResumeUseCase;
import org.adeniuobesu.adapters.exceptions.AdapterException;
import org.adeniuobesu.adapters.models.InputType;
import org.adeniuobesu.adapters.models.OutputType;
import org.adeniuobesu.core.validation.ResumeValidator;
import org.adeniuobesu.infrastructure.factories.InputStrategyFactory;
import org.adeniuobesu.infrastructure.factories.OutputStrategyFactory;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main implements Runnable {
    @Option(names = {"-j", "--json"}) 
    private String jsonFile;
    
    @Option(names = {"-f", "--format"}, defaultValue = "TEXT") 
    private OutputType outputFormat;

    @Option(names = {"-d", "--output-dir"}, defaultValue = ".")
    private String outputDir;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            Path outputPath = resolveOutputPath();
            ensureOutputDirectoryExists(outputPath.getParent());

            InputStrategy<ResumeDto> inputStrategy = createInputStrategy();
            OutputStrategy<ResumeDto> outputStrategy = createOutputStrategy();
            
            executeUseCase(inputStrategy, outputStrategy, outputPath);
            
            System.out.println("✓ Resume successfully generated at: " + outputPath);
            
        } catch (ResumeGenerationException e) {
            handleError("Resume generation failed", e);
        } catch (AdapterException e) {
            handleError("System configuration error", e);
        } catch (IOException e) {
            handleError("File system error", e);
        } catch (Exception e) {
            handleError("Unexpected error", e);
        }
    }

    private Path resolveOutputPath() {
        return Paths.get(outputDir).resolve("Resume." + outputFormat.name().toLowerCase());
    }

    private void ensureOutputDirectoryExists(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    private InputStrategy<ResumeDto> createInputStrategy() throws AdapterException {
        InputStrategyFactory factory = new InputStrategyFactory(new Scanner(System.in));
        InputType inputType = jsonFile != null ? InputType.JSON : InputType.CLI;
        return factory.create(inputType, jsonFile);
    }

    private OutputStrategy<ResumeDto> createOutputStrategy() throws AdapterException {
        return new OutputStrategyFactory().create(outputFormat);
    }

    private void executeUseCase(InputStrategy<ResumeDto> inputStrategy,
                              OutputStrategy<ResumeDto> outputStrategy,
                              Path outputPath) throws ResumeGenerationException, IOException {
        try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
            new BuildResumeUseCase(
                inputStrategy,
                outputStrategy,
                new ResumeValidator()
            ).execute(outputStream);
        }
    }

    private void handleError(String context, Exception e) {
        System.err.println("╔═══════════════════════════════════════════╗");
        System.err.println("║ " + context);
        System.err.println("╠═══════════════════════════════════════════╣");
        System.err.println("║ Reason: " + e.getMessage());
        
        if (e.getCause() != null) {
            System.err.println("║ Root cause: " + e.getCause().getMessage());
        }
        
        System.err.println("╚═══════════════════════════════════════════╝");
        throw new CommandLine.ExecutionException(new CommandLine(this), context, e);
    }
}