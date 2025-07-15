package org.adeniuobesu.resumegenerator.infrastructure.factories;

import org.adeniuobesu.resumegenerator.adapters.exceptions.AdapterException;
import org.adeniuobesu.resumegenerator.adapters.models.OutputType;
import org.adeniuobesu.resumegenerator.adapters.output.HtmlExporter;
import org.adeniuobesu.resumegenerator.adapters.output.MarkdownExporter;
import org.adeniuobesu.resumegenerator.adapters.output.PdfExporter;
import org.adeniuobesu.resumegenerator.adapters.output.TextExporter;
import org.adeniuobesu.resumegenerator.application.dtos.ResumeDto;
import org.adeniuobesu.resumegenerator.application.exceptions.OutputProcessingException;
import org.adeniuobesu.resumegenerator.application.ports.OutputStrategy;

public class OutputStrategyFactory {
    public OutputStrategy<ResumeDto> create(OutputType type) throws OutputProcessingException {
        try {
            return switch (type) {
                case PDF -> new PdfExporter();
                case HTML -> new HtmlExporter();
                case MARKDOWN -> new MarkdownExporter();
                case TEXT -> new TextExporter();
                default -> throw new OutputProcessingException("Unsupported output type: " + type);
            };
        } catch (AdapterException e) {
            throw new OutputProcessingException(type.toString(), e);
        }
    }
}
