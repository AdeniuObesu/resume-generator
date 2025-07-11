package org.adeniuobesu.core.input;

import java.util.Scanner;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.InputPort;

public class CliInputAdapter implements InputPort<Resume>{
    private final Scanner scanner;

    public CliInputAdapter(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public Resume collectData() {
        return null;
    }
    
}
