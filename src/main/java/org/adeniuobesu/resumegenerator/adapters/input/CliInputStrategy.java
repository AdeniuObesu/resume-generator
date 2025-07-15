package org.adeniuobesu.resumegenerator.adapters.input;

import java.util.Scanner;

import org.adeniuobesu.resumegenerator.application.dtos.ResumeDto;
import org.adeniuobesu.resumegenerator.application.ports.InputStrategy;

public class CliInputStrategy implements InputStrategy<ResumeDto>{
    private final Scanner scanner;

    public CliInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public ResumeDto collectData() {
        return null;
    }
    
}
