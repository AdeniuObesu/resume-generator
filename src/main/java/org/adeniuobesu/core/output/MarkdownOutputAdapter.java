package org.adeniuobesu.core.output;

import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.OutputPort;

public class MarkdownOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;

    public MarkdownOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume obj) {
        System.out.println("Done!");
    }
    
}
