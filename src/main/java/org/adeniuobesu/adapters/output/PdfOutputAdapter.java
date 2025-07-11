package org.adeniuobesu.adapters.output;

import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.OutputPort;

public class PdfOutputAdapter implements OutputPort<Resume> {
    private final Path outputPath;

    public PdfOutputAdapter(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void generate(Resume obj) {
        // Generate the pdf file with Apache PDFBox
    }
    
}
