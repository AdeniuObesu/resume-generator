package org.adeniuobesu.adapters.input;

import java.util.Scanner;

import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.ports.InputStrategy;

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
