package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.model.XpathAware;

import java.util.List;

public interface TemplateXpathAware extends XpathAware {
    void processSelector(List<String> parameters);
}
