package com.quangtt.webtest.core.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectStep extends Step implements XpathAware, InputAware {
    static Pattern pattern = Pattern.compile("(Index|Label|Value)#(.*)");
    String selector;
    String selectBy;
    String value;

    public SelectStep(String name, String selector, String value) {
        super(name);
        this.selector = selector;
        Matcher m = pattern.matcher(value);
        if (m.find()) {
            this.selectBy = m.group(1);
            this.value = m.group(2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getSelectBy() {
        return selectBy;
    }
}
