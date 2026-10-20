package com.example.qaffsamplejava.web.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates browser {@link WebDriver} instances. Relies on Selenium Manager
 * (Selenium 4) to provision matching drivers — no driver binary path is
 * hard-coded.
 *
 * Browser is selected at runtime via the {@code QAFF_BROWSER} environment
 * variable or the {@code browser} system property / config.properties key
 * (default: {@code chrome}). Supported values: {@code chrome},
 * {@code firefox}, {@code edge}.
 *
 * For Chrome/Chromium: override the binary with the {@code BROWSER_BINARY}
 * environment variable (e.g. {@code /usr/bin/chromium}).
 */
public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    /**
     * Creates a driver for the named browser.
     *
     * @param browser  one of "chrome", "firefox", "edge" (case-insensitive)
     * @param headless run in headless mode
     * @throws IllegalArgumentException for unsupported browser names
     */
    public static WebDriver newDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase(java.util.Locale.ROOT)) {
            case "chrome" -> newChrome(headless);
            case "firefox" -> newFirefox(headless);
            case "edge" -> newEdge(headless);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: \"" + browser + "\". Use chrome, firefox, or edge.");
        };
    }

    public static WebDriver newChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1280,800");

        applyChromeBinaryOverride(options);
        return new ChromeDriver(options);
    }

    public static WebDriver newFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    public static WebDriver newEdge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1280,800");
        return new EdgeDriver(options);
    }

    /**
     * Creates a Chrome/Chromium {@link WebDriver} running under built-in mobile
     * device emulation. Uses explicit device metrics (width / height / pixel
     * ratio) plus a user-agent so behaviour is deterministic and independent of
     * the Chromedriver version's named-device list. No real device, Appium
     * server or network is required — this is a desktop Chrome running a mobile
     * viewport.
     */
    public static WebDriver newChromeMobile(
            boolean headless, int width, int height, double pixelRatio, String userAgent) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu");

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", width);
        deviceMetrics.put("height", height);
        deviceMetrics.put("pixelRatio", pixelRatio);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        if (userAgent != null && !userAgent.isBlank()) {
            mobileEmulation.put("userAgent", userAgent);
        }
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        applyChromeBinaryOverride(options);
        return new ChromeDriver(options);
    }

    private static void applyChromeBinaryOverride(ChromeOptions options) {
        String binary = System.getenv("BROWSER_BINARY");
        if (binary != null && !binary.isBlank()) {
            options.setBinary(binary);
        }
    }
}
