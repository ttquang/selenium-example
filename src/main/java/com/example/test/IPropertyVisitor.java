package com.example.test;

public interface IPropertyVisitor extends Visitor {
    void put(SetPropertyStep step);
}
