package com.quangtt.webtest.template.model;

public class LoadPageTestStep extends TestStep {

    String url;

    public LoadPageTestStep(String name, String url, long delayPeriod) {
        super(name, delayPeriod);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
