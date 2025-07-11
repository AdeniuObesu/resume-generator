package org.adeniuobesu.adapters.input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.InputPort;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonInputAdapter implements InputPort<Resume> {
    private final Path filePath;

    public JsonInputAdapter(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Resume collectData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath.toString()), Resume.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON file: " + e.getMessage());
        }
    }
    
}
