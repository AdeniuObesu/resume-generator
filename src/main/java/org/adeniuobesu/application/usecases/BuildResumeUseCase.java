package org.adeniuobesu.application.usecases;

import java.io.OutputStream;
import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.exceptions.InputProcessingException;
import org.adeniuobesu.application.exceptions.OutputProcessingException;
import org.adeniuobesu.application.exceptions.ResumeGenerationException;
import org.adeniuobesu.application.mappers.ResumeMapper;
import org.adeniuobesu.application.ports.InputStrategy;
import org.adeniuobesu.application.ports.OutputStrategy;
import org.adeniuobesu.core.models.Resume;
import org.adeniuobesu.core.validation.ResumeValidator;
import org.adeniuobesu.core.exceptions.InvalidResumeException;

public class BuildResumeUseCase {
    private final InputStrategy<ResumeDto> input;
    private final OutputStrategy<ResumeDto> output;
    private final ResumeValidator validator;

    public BuildResumeUseCase(
        InputStrategy<ResumeDto> input,
        OutputStrategy<ResumeDto> output,
        ResumeValidator validator) {
        this.input = input;
        this.output = output;
        this.validator = validator;
    }

    public void execute(OutputStream os) throws ResumeGenerationException {
        try {
            // 1. Input (DTO depuis l'adapter)
            ResumeDto resumeDto = input.collectData();

            // 2. Mapping vers domaine
            Resume resume = ResumeMapper.toDomain(resumeDto);

            // 3. Validation métier
            validator.validate(resume);

            // 4. Mapping inverse vers DTO
            ResumeDto validatedDto = ResumeMapper.toDto(resume);

            // 5. Output (DTO vers l'adapter)
            output.generate(validatedDto, os);

        } catch (InvalidResumeException e) {
            throw new ResumeGenerationException("validation: " + e.getMessage(), e);
        } catch (InputProcessingException e) {
            throw new ResumeGenerationException("input collection", e);
        } catch (OutputProcessingException e) {
            throw new ResumeGenerationException("output generation", e);
        }
    }
}
