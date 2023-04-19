package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.model.Step;
import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import static com.quangtt.webtest.template.service.TemplateUtils.PARAMETER_PATTERN;

public class Element {

    String name;
    String type;
    Map<String, String> parameters = new HashMap<>();

    public Element(String name) {
        this.name = name;
    }

    public Element(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Element(String name, String type, Map<String, String> parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public boolean isTemplate() {
        return "Template".equals(type);
    }

    public final Element clone() {
        Map<String, String> cloneParameters = new HashMap<>();
        for (String key : this.parameters.keySet()) {
            cloneParameters.put(key, this.parameters.get(key));
        }
        return new Element(name, type, cloneParameters);
    }

    public void setProperty(String property, String value) {
        parameters.put(property, value);
    }

    public Step generateStep(String group, Map<String, String> inputParameters) {
        Map<String, String> cloneParameters = new HashMap<>();
        Set<String> placeHolders = new HashSet<>();
        for (String key: parameters.keySet()) {
            String rawValue = parameters.get(key);
            Matcher m = PARAMETER_PATTERN.matcher(rawValue);
            while (m.find()) {
                placeHolders.add(m.group(1));
                rawValue = rawValue.replace(m.group(), "PLACEHOLDER" + m.group(1));
                System.out.println(rawValue);
                m = PARAMETER_PATTERN.matcher(rawValue);
            }

            for (String index : placeHolders) {
                if (inputParameters.get(index) == null) {
                    System.out.println("sss");
                } else {
                    rawValue = rawValue.replace("PLACEHOLDER" + index, inputParameters.get(index));
                }
            }
            cloneParameters.put(key,rawValue);
        }
        return new Step(group + "." + name, type, cloneParameters);
    }

    public void processParameter(Map<String, String> parameters) {
        this.parameters = TemplateUtils.processParameter(this.parameters, parameters);
    }

    @Override
    public String toString() {
        return name + "-" + type + "-" + parameters;
    }

}
