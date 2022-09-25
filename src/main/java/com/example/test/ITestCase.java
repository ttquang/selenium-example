package com.example.test;

public interface ITestCase {

    void run();

    void visit(ClickStep step);
    void visit(DelayStep step);
    void visit(InputStep step);
    void visit(InputSelectStep step);
    void visit(SetPropertyStep step);
    void visit(TransferPropertyStep step);

    void visit(SwitchFrameStep step);

}
