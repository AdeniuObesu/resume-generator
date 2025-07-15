package org.adeniuobesu.resumegenerator.application.ports;

import org.adeniuobesu.resumegenerator.application.exceptions.InputProcessingException;

public interface InputStrategy<T> {
    T collectData() throws InputProcessingException;
}