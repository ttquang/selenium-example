package com.quangtt.webtest.core.exception;

import com.quangtt.webtest.core.model.Step;

public class StepRuntimeException extends RuntimeException {

    public StepRuntimeException() {}

    public StepRuntimeException(Step testStep) {
        super(testStep.toString());
    }

}
