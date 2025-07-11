package org.adeniuobesu.core.output;

import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.OutputPort;

public class HtmlOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;

    public HtmlOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume obj) {
        // Generate a html file
    }
    
}
