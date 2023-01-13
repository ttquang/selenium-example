package com.quangtt.webtest.core.model;

public class LoadPageStep extends Step {

    String url;

    public LoadPageStep(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
