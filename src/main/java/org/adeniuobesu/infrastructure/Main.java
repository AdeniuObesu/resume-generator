package org.adeniuobesu.infrastructure;

import org.adeniuobesu.core.models.InputType;
import org.adeniuobesu.core.models.OutputType;
import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.InputPort;
import org.adeniuobesu.core.ports.OutputPort;
import org.adeniuobesu.core.usecases.BuildResumeUseCase;
import org.adeniuobesu.infrastructure.factories.ResumeAdapterFactory;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Main.java (infrastructure layer)
public class Main implements Runnable {
    @Option(names = {"-j", "--json"}) 
    private String jsonFile;
    
    @Option(names = {"-f", "--format"}, defaultValue = "PDF") 
    private OutputType outputFormat;

    @Option(names = {"-d", "--output-dir"}, description = "Output directory path", defaultValue = ".")
    private String outputDir;


    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }

    @Override
    public void run() {
        ResumeAdapterFactory factory = new ResumeAdapterFactory(new Scanner(System.in));
        
        InputPort<Resume> inputAdapter = jsonFile != null
            ? factory.createInputAdapter(InputType.JSON, jsonFile)
            : factory.createInputAdapter(InputType.CLI, null);

        // Définir un nom de fichier par défaut en fonction du format de sortie
        String defaultFilename = switch (outputFormat) {
            case PDF -> "resume.pdf";
            case HTML -> "resume.html";
            case MARKDOWN -> "resume.md";
            case TEXT -> "resume.text";
            default -> "resume.out";
        };
        Path outputPath = Paths.get(outputDir).resolve(defaultFilename);
        
        OutputPort<Resume> outputAdapter = factory.createOutputAdapter(outputFormat, outputPath.toString());
        
        new BuildResumeUseCase(inputAdapter, outputAdapter).execute();
    }
}