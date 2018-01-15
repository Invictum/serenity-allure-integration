package com.github.invictum.allure;

import io.qameta.allure.model.Status;
import net.thucydides.core.model.TestResult;

import java.util.EnumMap;

/**
 * Defines mapping between {@link TestResult} and {@link Status}
 */
public class StatusMapping {

    private static final EnumMap<TestResult, Status> mapping = new EnumMap<>(TestResult.class);

    static {
        mapping.put(TestResult.SUCCESS, Status.PASSED);
        mapping.put(TestResult.SKIPPED, Status.SKIPPED);
        mapping.put(TestResult.PENDING, Status.SKIPPED);
        mapping.put(TestResult.IGNORED, Status.SKIPPED);
        mapping.put(TestResult.ERROR, Status.FAILED);
        mapping.put(TestResult.FAILURE, Status.FAILED);
        mapping.put(TestResult.UNDEFINED, Status.BROKEN);
        mapping.put(TestResult.COMPROMISED, Status.BROKEN);
    }

    public static Status discover(TestResult testResult) {
        return mapping.get(testResult);
    }
}
