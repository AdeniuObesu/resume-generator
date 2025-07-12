package org.adeniuobesu.application.ports;

import org.adeniuobesu.application.exceptions.InputProcessingException;

public interface InputStrategy<T> {
    T collectData() throws InputProcessingException;
}