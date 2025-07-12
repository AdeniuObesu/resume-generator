package org.adeniuobesu.infrastructure.factories;

import org.adeniuobesu.adapters.exceptions.AdapterException;
import org.adeniuobesu.adapters.models.OutputType;
import org.adeniuobesu.adapters.output.HtmlExporter;
import org.adeniuobesu.adapters.output.MarkdownExporter;
import org.adeniuobesu.adapters.output.PdfExporter;
import org.adeniuobesu.adapters.output.TextExporter;
import org.adeniuobesu.application.dtos.ResumeDto;
import org.adeniuobesu.application.exceptions.OutputProcessingException;
import org.adeniuobesu.application.ports.OutputStrategy;

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
