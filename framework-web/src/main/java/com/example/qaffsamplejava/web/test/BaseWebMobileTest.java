package com.example.qaffsamplejava.web.test;

import java.time.Duration;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import com.example.qaffsamplejava.core.config.ConfigLoader;
import com.example.qaffsamplejava.core.util.ResourcePath;
import com.example.qaffsamplejava.web.config.SmokeExpectations;
import com.example.qaffsamplejava.web.config.WebConfig;
import com.example.qaffsamplejava.web.driver.WebDriverFactory;
import com.example.qaffsamplejava.web.extension.ScreenshotOnFailureExtension;

/**
 * Boots a Chrome/Chromium {@link WebDriver} under mobile-device emulation for
 * each test, using device metrics from {@code config.properties} on the test
 * classpath ({@code mobile.width}, {@code mobile.height},
 * {@code mobile.pixelRatio}, {@code mobile.userAgent}). No Appium server or
 * real device is required.
 *
 * <p>Failed tests automatically capture a screenshot via
 * {@link ScreenshotOnFailureExtension} — saved to
 * {@code target/screenshots/<ClassName>/<method>-<nano>.png}.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseWebMobileTest {

    protected WebDriver driver;
    protected Properties config;
    protected Duration timeout;
    protected WebConfig webConfig;
    protected SmokeExpectations smoke;

    @BeforeEach
    void startBrowser() {
        config = ConfigLoader.fromClasspath("config.properties");
        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "true"));
        long timeoutSeconds = Long.parseLong(config.getProperty("timeoutSeconds", "10"));
        timeout = Duration.ofSeconds(timeoutSeconds);
        webConfig = WebConfig.from(config);
        smoke = SmokeExpectations.from(config);

        int width = Integer.parseInt(config.getProperty("mobile.width", "390"));
        int height = Integer.parseInt(config.getProperty("mobile.height", "844"));
        double pixelRatio = Double.parseDouble(config.getProperty("mobile.pixelRatio", "3.0"));
        String userAgent = config.getProperty("mobile.userAgent", "");

        driver = WebDriverFactory.newChromeMobile(headless, width, height, pixelRatio, userAgent);
    }

    protected String resolveSmokeUrl(String fixtureClasspathName) {
        if (WebConfig.TARGET_FIXTURE.equals(webConfig.target())) {
            return ResourcePath.classpathFileUrl(fixtureClasspathName);
        }

        String baseUrl = webConfig.baseUrl();
        if (baseUrl == null
                || baseUrl.isBlank()
                || WebConfig.LOCAL_FIXTURE_SENTINEL.equalsIgnoreCase(baseUrl.trim())) {
            throw new IllegalStateException(
                    "External web target selected but baseUrl is not configured. "
                            + "Set -Dweb.baseUrl, QAFF_WEB_BASE_URL, or baseUrl in config.properties.");
        }

        return baseUrl.trim();
    }

    @AfterEach
    void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
