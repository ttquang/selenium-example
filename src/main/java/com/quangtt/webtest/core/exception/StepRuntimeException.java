package com.quangtt.webtest.core.exception;

import com.quangtt.webtest.core.model.TestStep;

public class StepRuntimeException extends RuntimeException {

    public StepRuntimeException() {}

    public StepRuntimeException(TestStep testStep) {
        super(testStep.toString());
    }

}
