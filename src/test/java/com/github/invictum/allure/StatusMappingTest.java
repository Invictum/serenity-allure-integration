package com.github.invictum.allure;

import io.qameta.allure.model.Status;
import net.thucydides.core.model.TestResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class StatusMappingTest {

    @Parameterized.Parameter()
    public TestResult testResult;

    @Parameterized.Parameter(1)
    public Status status;

    @Test
    public void mappingTest() {
        Assert.assertEquals("Mapping is wrong.", StatusMapping.discover(testResult), status);
    }

    @Parameterized.Parameters(name = "{index}: {0} - {1}")
    public static Object[][] data() {
        return new Object[][]{
                {TestResult.SUCCESS, Status.PASSED},
                {TestResult.SKIPPED, Status.SKIPPED},
                {TestResult.PENDING, Status.SKIPPED},
                {TestResult.ERROR, Status.FAILED},
                {TestResult.FAILURE, Status.FAILED},
                {TestResult.UNDEFINED, Status.BROKEN},
                {TestResult.COMPROMISED, Status.BROKEN}
        };
    }
}

