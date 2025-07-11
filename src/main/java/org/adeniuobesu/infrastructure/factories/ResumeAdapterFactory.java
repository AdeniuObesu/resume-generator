package org.adeniuobesu.infrastructure.factories;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.adeniuobesu.core.input.CliInputAdapter;
import org.adeniuobesu.core.input.JsonInputAdapter;
import org.adeniuobesu.core.models.InputType;
import org.adeniuobesu.core.models.OutputType;
import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.output.HtmlOutputAdapter;
import org.adeniuobesu.core.output.MarkdownOutputAdapter;
import org.adeniuobesu.core.output.PdfOutputAdapter;
import org.adeniuobesu.core.output.TextOutputAdapter;
import org.adeniuobesu.core.ports.InputPort;
import org.adeniuobesu.core.ports.OutputPort;

public class ResumeAdapterFactory {
    private final Scanner scanner;

    public ResumeAdapterFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public InputPort<Resume> createInputAdapter(InputType type, String source) {
        InputPort <Resume> inputPort = null;

        switch(type) {
            case CLI -> inputPort = new CliInputAdapter(scanner);
            case JSON -> inputPort = new JsonInputAdapter(Paths.get(source));
            default -> throw new IllegalArgumentException("Unknown input type: " + type);
        }

        return inputPort;
    }

    public OutputPort<Resume> createOutputAdapter(OutputType type, String outputPath) {
        Path path = outputPath != null ? Paths.get(outputPath) : Paths.get("resume." + type.name().toLowerCase());

        OutputPort<Resume> outputPort = null;
        switch (type) {
            case PDF -> outputPort = new PdfOutputAdapter(path);
            case HTML -> outputPort = new HtmlOutputAdapter(path);
            case MARKDOWN -> outputPort = new MarkdownOutputAdapter(path);
            case TEXT -> outputPort = new TextOutputAdapter(path);
            default -> throw new IllegalArgumentException("Unknown output type: " + type);
        };

        return outputPort;
    }
    
}
