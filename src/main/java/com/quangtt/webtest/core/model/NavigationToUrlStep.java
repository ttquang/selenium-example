package com.quangtt.webtest.core.model;

public class NavigationToUrlStep extends NavigationStep {

    String url;

    public NavigationToUrlStep(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
