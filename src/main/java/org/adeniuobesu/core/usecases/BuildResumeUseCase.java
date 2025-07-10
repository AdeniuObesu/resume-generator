package org.adeniuobesu.core.usecases;

import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.ports.InputPort;
import org.adeniuobesu.core.ports.OutputPort;
import org.adeniuobesu.core.validation.ResumeValidator;

public class BuildResumeUseCase {
    private final InputPort<Resume> input;
    private final OutputPort<Resume> output;

    public BuildResumeUseCase(InputPort<Resume> input, OutputPort<Resume> output) {
        this.input = input;
        this.output = output;
    }

    public void execute() {
        Resume resume = input.collectData();
        ResumeValidator.validate(resume);
        output.generate(resume);
    }
}