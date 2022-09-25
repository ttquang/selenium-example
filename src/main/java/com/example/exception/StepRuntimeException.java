package com.example.exception;

public class StepRuntimeException extends RuntimeException {

    private String stepName;

    public StepRuntimeException() {}

    public StepRuntimeException(String stepName, String message) {
        super(message);
        this.stepName = stepName;
    }
}
