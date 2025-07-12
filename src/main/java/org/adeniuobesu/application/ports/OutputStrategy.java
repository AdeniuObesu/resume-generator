package org.adeniuobesu.application.ports;

import java.io.OutputStream;
import org.adeniuobesu.application.exceptions.OutputProcessingException;

public interface OutputStrategy<T> {
    void generate(T data, OutputStream os) throws OutputProcessingException;
}