package com.github.invictum.allure;

import io.qameta.allure.model.Label;
import io.qameta.allure.model.StatusDetails;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.stacktrace.FailureCause;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.qameta.allure.util.ResultsUtils.getHostName;
import static io.qameta.allure.util.ResultsUtils.getThreadName;

/**
 * Utils methods container
 */
public class Utils {

    /**
     * Wraps {@link FailureCause} to {@link StatusDetails}
     *
     * @param cause to wrap
     * @return Optional {@link StatusDetails}
     */
    public static Optional<StatusDetails> error(FailureCause cause) {
        if (cause != null) {
            Throwable exception = cause.toException();
            StringWriter writer = new StringWriter();
            cause.toException().printStackTrace(new PrintWriter(writer));
            String trace = writer.toString();
            return Optional.of(new StatusDetails().withMessage(exception.toString()).withTrace(trace));
        }
        return Optional.empty();
    }

    /**
     * Wraps file to attachment
     *
     * @param file to wrap
     * @return optional {@link Attach}
     */
    public static Optional<Attach> attachment(File file) {
        try {
            String mime = URLConnection.guessContentTypeFromName(file.getName());
            String extension = file.getName().split(".")[1];
            byte[] data = Files.readAllBytes(file.toPath());
            return Optional.of(new Attach(file.getName(), mime, extension, data));
        } catch (IOException e) {
            /* Failed to read file */
            return Optional.empty();
        }
    }

    /**
     * Calculates entity end time
     *
     * @param start    entity time
     * @param duration in milliseconds
     * @return evaluated finish time
     */
    public static long finishTime(ZonedDateTime start, long duration) {
        return start.plus(Duration.ofMillis(duration)).toInstant().toEpochMilli();
    }

    /**
     * Tags {@link TestOutcome} object with related labels
     *
     * @param testOutcome object to generates tags for
     * @return list of labels
     */
    public static List<Label> testLabels(TestOutcome testOutcome) {
        return new ArrayList<>(Arrays.asList(
//                new Label().withName("parentSuite").withValue("parent-my-suite"),
                new Label().withName("suite").withValue(testOutcome.getUserStory().getPath()),
                new Label().withName("subSuite").withValue(testOutcome.getUserStory().getName()),
                new Label().withName("host").withValue(getHostName()),
                new Label().withName("thread").withValue(getThreadName()),
                new Label().withName("package").withValue(testOutcome.getUserStory().getPath()),
                new Label().withName("testClass").withValue(testOutcome.getUserStory().getName()),
                new Label().withName("testMethod").withValue(testOutcome.getDescription())
        ));
    }
}
