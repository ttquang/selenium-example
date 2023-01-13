package com.quangtt.webtest.template.model;

public class LoadPageTestStep extends Element {

    String url;

    public LoadPageTestStep(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public Element clone() {
        return null;
    }

}
