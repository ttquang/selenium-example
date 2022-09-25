package com.example.test;

public interface IWebDriverVisitor extends Visitor{
    void click(ElementStep step);

    void input(InputStep step);

    void select(InputStep step);

    String value(String selector);

}
