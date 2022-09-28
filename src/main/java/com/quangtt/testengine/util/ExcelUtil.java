package com.quangtt.testengine.util;

import com.quangtt.testengine.model.testelement.TestCase;
import com.quangtt.testengine.model.testelement.TestStep;
import com.quangtt.testengine.model.testelement.TestSuite;
import com.quangtt.testengine.model.teststep.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ExcelUtil {

    public Function<Cell, String> VALUE_EXTRACTION = cell -> {
        if (Objects.isNull(cell)) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                return cell.getStringCellValue();
            default:
                return "";
        }
    };

    public List<TestStep> process(File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        List<TestStep> result = new ArrayList<>();

        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            String type = VALUE_EXTRACTION.apply(row.getCell(1));
            TestStep testStep = null;

            switch (type) {
                case "Input":
                    testStep = new InputTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "Click":
                    testStep = new ClickTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;
                case "Delay":
                    testStep = new DelayTestStep(VALUE_EXTRACTION.apply(row.getCell(0)));
                    break;
                case "InputSelect":
                    testStep = new InputSelectTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "TransferProperty":
                    testStep = new TransferPropertyTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(3)), VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;
                case "SetProperty":
                    testStep = new SetPropertyTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "SwitchFrame":
                    testStep = new SwitchFrameTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;
                case "LoadPage":
                    testStep = new LoadPageTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;

            }

            result.add(testStep);
        }

        return result;
    }

    public TestSuite processTestSuit(File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Iterator<Sheet> iterator = workbook.iterator();
        TestSuite testSuite = constructTestSuite(iterator.next());

        while (iterator.hasNext()) {
            TestCase testCase = constructTestCase(iterator.next());
            testSuite.addTestCase(testCase);
        }

        return testSuite;
    }

    public TestCase constructTestCase(Sheet testCaseSheet) {
        TestCase testCase = new TestCase(testCaseSheet.getSheetName());
//        IPropertyHandler propertyHandler = new PropertyHandler("TestCase");

        boolean inProcessingProperty = false;
        boolean inProcessingTestStep = false;

        for (int i = 2; i < testCaseSheet.getLastRowNum(); i++) {
            Row row = testCaseSheet.getRow(i);

            if ("Property".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                inProcessingProperty = true;
            } else if ("TestStep".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                inProcessingProperty = false;
                inProcessingTestStep = true;
            } else if ("END".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                break;
            } else {
                if (inProcessingProperty) {
                    testCase.getPropertyHandler().put(VALUE_EXTRACTION.apply(row.getCell(1)), VALUE_EXTRACTION.apply(row.getCell(3)));
                } else if (inProcessingTestStep) {
                    String type = VALUE_EXTRACTION.apply(row.getCell(1));
                    TestStep testStep = null;

                    switch (type) {
                        case "Input":
                            testStep = new InputTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                            break;
                        case "Click":
                            testStep = new ClickTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)));
                            break;
                        case "Delay":
                            testStep = new DelayTestStep(VALUE_EXTRACTION.apply(row.getCell(0)));
                            break;
                        case "InputSelect":
                            testStep = new InputSelectTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                            break;
                        case "TransferProperty":
                            testStep = new TransferPropertyTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(3)), VALUE_EXTRACTION.apply(row.getCell(2)));
                            break;
                        case "SetProperty":
                            testStep = new SetPropertyTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                            break;
                        case "SwitchFrame":
                            testStep = new SwitchFrameTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(4)), VALUE_EXTRACTION.apply(row.getCell(2)));
                            break;
                        case "LoadPage":
                            testStep = new LoadPageTestStep(VALUE_EXTRACTION.apply(row.getCell(0)), VALUE_EXTRACTION.apply(row.getCell(2)));
                            break;
                    }

                    if (Objects.nonNull(testStep)) {
                        testCase.addTestStep(testStep);
                    }

                }
            }

        }

        return testCase;
    }

    public TestSuite constructTestSuite(Sheet testSuiteSheet) {
        Row nameRow = testSuiteSheet.getRow(0);
        TestSuite testSuite = new TestSuite(VALUE_EXTRACTION.apply(nameRow.getCell(1)));

        boolean inProcessingProperty = false;
        for (int i = 1; i < testSuiteSheet.getLastRowNum(); i++) {
            Row row = testSuiteSheet.getRow(i);
            if (inProcessingProperty) {
                testSuite.getPropertyHandler().put(VALUE_EXTRACTION.apply(row.getCell(1)), VALUE_EXTRACTION.apply(row.getCell(2)));
            } else {
                if ("Property".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                    inProcessingProperty = true;
                } else if ("END".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                    break;
                }
            }
        }

        return testSuite;
    }
}
