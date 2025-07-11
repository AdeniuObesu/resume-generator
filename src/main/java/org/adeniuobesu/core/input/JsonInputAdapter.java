package org.adeniuobesu.core.input;

import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.InputPort;

public class JsonInputAdapter implements InputPort<Resume> {
    private final Path filePath;

    public JsonInputAdapter(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Resume collectData() {
        // JSON parsing logic
        return null;
    }
    
}
