package com.quangtt.webtest.template;

import com.quangtt.webtest.core.model.*;

public class TemplateStepFactory {

    public static ClickStep createClickStep(String name, XpathAware elementAware) {
        return new ClickStep(name, elementAware.getSelector());
    }

    public static TextInputStep createTextInputStep(String name, XpathAware elementAware, InputAware inputAware) {
        return new TextInputStep(name, elementAware.getSelector(), inputAware.getValue());
    }

    public static SelectInputStep createSelectInputStep(String name, XpathAware elementAware, InputAware inputAware) {
        return new SelectInputStep(name, elementAware.getSelector(), inputAware.getValue());
    }

    public static SwitchToFrameByXpathStep createSwitchFrameStep(String name, XpathAware elementAware) {
        return new SwitchToFrameByXpathStep(name, elementAware.getSelector());
    }

}
