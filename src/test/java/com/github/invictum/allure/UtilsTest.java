package com.github.invictum.allure;

import io.qameta.allure.model.StatusDetails;
import net.thucydides.core.model.stacktrace.FailureCause;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void imageAttachmentTest() throws IOException {
        File attach = folder.newFile("image.png");
        Optional<Attach> actual = Utils.attachment(attach);
        Optional<Attach> expected = Optional.of(new Attach("image.png", "image/png", "png", new byte[0]));
        Assert.assertEquals("Image attachment is wrong", expected, actual);
    }

    @Test
    public void sourcesAttachmentTest() throws IOException {
        File attach = folder.newFile("source.txt");
        Optional<Attach> actual = Utils.attachment(attach);
        Optional<Attach> expected = Optional.of(new Attach("source.txt", "text/plain", "txt", new byte[0]));
        Assert.assertEquals("Source attachment is wrong", expected, actual);
    }

    @Test
    public void readFailErrorTest() {
        File fileMock = Mockito.mock(File.class, invocation -> {
            String method = invocation.getMethod().getName();
            if (method.contains("getName")) {
                return "path.txt";
            }
            if (method.contains("toPath")) {
                return Paths.get("/invalid/path.txt");
            }
            return invocation.callRealMethod();
        });
        Assert.assertEquals(Optional.empty(), Utils.attachment(fileMock));
    }

    @Test
    public void errorMessageTest() {
        FailureCause cause = new FailureCause(new RuntimeException("Error"));
        Optional<StatusDetails> statusDetails = Utils.error(cause);
        String actual = statusDetails.isPresent() ? statusDetails.get().getMessage() : "none";
        Assert.assertEquals("Error cause proceed wrong", "java.lang.RuntimeException: Error", actual);
    }

    @Test
    public void emptyCauseTest() {
        Assert.assertEquals("Empty cause proceed wrong", Optional.empty(), Utils.error(null));
    }
}
