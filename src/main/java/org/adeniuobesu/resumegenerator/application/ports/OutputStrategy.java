package org.adeniuobesu.resumegenerator.application.ports;

import java.io.OutputStream;
import org.adeniuobesu.resumegenerator.application.exceptions.OutputProcessingException;

public interface OutputStrategy<T> {
    void generate(T data, OutputStream os) throws OutputProcessingException;
}