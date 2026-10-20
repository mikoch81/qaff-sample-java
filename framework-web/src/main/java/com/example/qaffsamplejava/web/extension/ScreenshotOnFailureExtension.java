package com.example.qaffsamplejava.web.extension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * JUnit 5 extension that captures a screenshot when a test fails.
 * Saved to {@code target/screenshots/<ClassName>/<methodName>-<nanoTime>.png}
 * and attached to the Allure report.
 * Wire it into a test class with {@code @ExtendWith(ScreenshotOnFailureExtension.class)}.
 *
 * <p>Implemented as a {@code TestExecutionExceptionHandler} on purpose: it
 * runs at the moment the test method throws, while the browser session is
 * still alive. A {@code TestWatcher#testFailed} callback would fire only
 * after {@code @AfterEach} has quit the driver, so it can never capture
 * anything. Do not "simplify" this class back to a TestWatcher.
 */
public class ScreenshotOnFailureExtension implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable cause) throws Throwable {
        // Aborted tests (assumptions, retry attempts) are not failures.
        if (!(cause instanceof TestAbortedException)) {
            try {
                Object instance = context.getRequiredTestInstance();
                WebDriver driver = resolveDriver(instance);
                if (driver instanceof TakesScreenshot ts) {
                    saveScreenshot(ts, context);
                }
            } catch (RuntimeException e) {
                // Capture must never mask the original test failure.
                System.err.println("[ScreenshotOnFailureExtension] Could not capture screenshot: " + e.getMessage());
            }
        }
        throw cause;
    }

    private static WebDriver resolveDriver(Object instance) {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (WebDriver.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    try {
                        return (WebDriver) field.get(instance);
                    } catch (IllegalAccessException e) {
                        // field inaccessible — try next
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static void saveScreenshot(TakesScreenshot ts, ExtensionContext context) {
        try {
            byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
            String className = context.getTestClass()
                    .map(Class::getSimpleName).orElse("Unknown");
            String method = context.getTestMethod()
                    .map(java.lang.reflect.Method::getName).orElse("unknown");
            Path dir = Paths.get("target", "screenshots", className);
            Files.createDirectories(dir);
            Path file = dir.resolve(method + "-" + System.nanoTime() + ".png");
            Files.write(file, bytes);
            Allure.addAttachment("Screenshot on failure", "image/png",
                    new ByteArrayInputStream(bytes), ".png");
        } catch (IOException e) {
            System.err.println("[ScreenshotOnFailureExtension] Could not save screenshot: " + e.getMessage());
        }
    }
}
