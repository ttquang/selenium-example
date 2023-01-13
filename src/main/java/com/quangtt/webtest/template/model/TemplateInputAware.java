package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.model.InputAware;

import java.util.List;

public interface TemplateInputAware extends InputAware {
    void processValue(List<String> parameters);
}
