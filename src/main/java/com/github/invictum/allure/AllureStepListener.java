package com.github.invictum.allure;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Stage;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AllureStepListener implements StepListener {

    private ThreadLocal<AllureLifecycle> lifecycle = ThreadLocal.withInitial(AllureLifecycle::new);
    private ThreadLocal<String> uid = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    @Override
    public void testSuiteStarted(final Class<?> storyClass) {
        // Is not used
    }

    @Override
    public void testSuiteStarted(Story story) {
        // Is not used
    }

    @Override
    public void testSuiteFinished() {
        // Is not used
    }

    @Override
    public void testStarted(String description) {
        lifecycle.get().scheduleTestCase(new TestResult().withName(description).withUuid(uid.get()));
        lifecycle.get().startTestCase(uid.get());
    }

    @Override
    public void testStarted(String description, String id) {
        testStarted(description);
    }

    @Override
    public void testFinished(TestOutcome testOutcome) {
        lifecycle.get().updateTestCase(uid.get(), test -> {
            test.withStatus(StatusMapping.discover(testOutcome.getResult()));
            test.withLabels(Utils.testLabels(testOutcome));
            proceedStepsTree(uid.get(), testOutcome.getTestSteps());
            test.withStop(Utils.finishTime(testOutcome.getStartTime(), testOutcome.getDuration()));
            Utils.error(testOutcome.getTestFailureCause()).ifPresent(test::withStatusDetails);
            test.withStage(Stage.FINISHED);
        });
        lifecycle.get().writeTestCase(uid.get());
        uid.remove();
    }

    private void proceedStepsTree(String uid, List<TestStep> steps) {
        for (TestStep step : steps) {
            /* Create a new step */
            String stepUid = UUID.randomUUID().toString();
            StepResult stepResult = new StepResult().withName(step.getDescription());
            lifecycle.get().startStep(uid, stepUid, stepResult);
            lifecycle.get().updateStep(stepUid, update -> {
                update.withStatus(StatusMapping.discover(step.getResult()));
                long start = step.getStartTime().toInstant().toEpochMilli();
                update.withStart(start);
            });
            /* Attach screenshots and sources */
            step.getScreenshots().forEach(source -> {
                Utils.attachment(source.getScreenshot()).ifPresent(attach -> lifecycle.get()
                        .addAttachment(attach.getName(), attach.getMime(), attach.getExtension(), attach.getBody()));
                if (source.getHtmlSource().isPresent()) {
                    Utils.attachment(source.getHtmlSource().get()).ifPresent(attach -> lifecycle.get()
                            .addAttachment(attach.getName(), attach.getMime(), attach.getExtension(), attach
                                    .getBody()));
                }
            });
            /* Proceed nested children */
            if (!step.getChildren().isEmpty()) {
                proceedStepsTree(stepUid, step.getChildren());
            }
            /* Finish current step */
            lifecycle.get().updateStep(stepUid, update -> {
                update.withStop(Utils.finishTime(step.getStartTime(), step.getDuration()));
                update.withStage(Stage.FINISHED);
            });
        }
    }

    @Override
    public void testRetried() {
        // Is not used
    }

    @Override
    public void stepStarted(ExecutedStepDescription description) {
        // Is not used
    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {
        // Is not used
    }

    @Override
    public void stepFailed(StepFailure stepFailure) {
        // Is not used
    }

    @Override
    public void lastStepFailed(StepFailure stepFailure) {
        // Is not used
    }

    @Override
    public void stepIgnored() {
        // Is not used
    }

    @Override
    public void stepPending() {
        // Is not used
    }

    @Override
    public void stepPending(String s) {
        // Is not used
    }

    @Override
    public void stepFinished() {
        // Is not used
    }

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable throwable) {
        // Is not used
    }

    @Override
    public void testIgnored() {
        // Is not used
    }

    @Override
    public void testSkipped() {
        // Is not used
    }

    @Override
    public void testPending() {
        // Is not used
    }

    @Override
    public void testIsManual() {
        // Is not used
    }

    @Override
    public void notifyScreenChange() {
        // Is not used
    }

    @Override
    public void useExamplesFrom(DataTable dataTable) {
        // Is not used
    }

    @Override
    public void addNewExamplesFrom(DataTable dataTable) {
        // Is not used
    }

    @Override
    public void exampleStarted(Map<String, String> map) {
        // Is not used
    }

    @Override
    public void exampleFinished() {
        // Is not used
    }

    @Override
    public void assumptionViolated(String s) {
        // Is not used
    }

    @Override
    public void testRunFinished() {
        // Is not used
    }
}
