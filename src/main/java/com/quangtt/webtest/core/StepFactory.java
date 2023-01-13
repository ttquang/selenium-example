package com.quangtt.webtest.core;

import com.quangtt.webtest.core.model.*;

public class StepFactory {

    public static ClickStep createClickStep(String name, XpathAware elementAware) {
        return new ClickStep(name, elementAware.getSelector());
    }

    public static TextInputStep createTextInputStep(String name, XpathAware elementAware, InputAware inputAware) {
        return new TextInputStep(name, elementAware.getSelector(), inputAware.getValue());
    }

    public static SelectInputStep createSelectInputStep(String name, XpathAware elementAware, InputAware inputAware) {
        return new SelectInputStep(name, elementAware.getSelector(), inputAware.getValue());
    }

    public static SwitchFrameStep createSwitchFrameStep(String name, XpathAware elementAware) {
        return new SwitchFrameStep(name, elementAware.getSelector());
    }

}
