package org.adeniuobesu.core.output;

import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.OutputPort;

public class TextOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;

    public TextOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume obj) {
        // generate a text file
    }
    
}
