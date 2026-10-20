package com.example.qaffsamplejava.web.page;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for reusable page components (modals, navigation bars, sidebars, etc.)
 * that are scoped to a container element found by the caller.
 *
 * <pre>{@code
 * public class LoginModal extends PageComponent {
 *     private static final By SUBMIT_BTN = By.id("submit");
 *
 *     public LoginModal(WebDriver driver, WebElement container, Duration timeout) {
 *         super(driver, container, timeout);
 *     }
 *
 *     @Step("Submit login form")
 *     public void submitLogin(String username, String password) {
 *         waitForVisible(SUBMIT_BTN).click();
 *     }
 * }
 * // Usage in a page (pass the same timeout your BaseWebTest / pages use):
 * WebElement modal = driver.findElement(By.id("login-modal"));
 * new LoginModal(driver, modal, Duration.ofSeconds(timeoutSeconds)).submitLogin("user", "pass");
 * }</pre>
 */
public abstract class PageComponent {

    /** Fallback wait used by the two-argument compatibility constructor. */
    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    protected final WebElement container;
    private final WebDriverWait wait;

    /**
     * Preferred constructor — pass the same timeout the rest of the framework
     * uses (the configured {@code timeoutSeconds}), so components never wait
     * with a different budget than pages.
     */
    protected PageComponent(WebDriver driver, WebElement container, Duration timeout) {
        this.driver = driver;
        this.container = container;
        this.wait = new WebDriverWait(driver, timeout);
    }

    /** Compatibility constructor using {@link #DEFAULT_TIMEOUT}. */
    protected PageComponent(WebDriver driver, WebElement container) {
        this(driver, container, DEFAULT_TIMEOUT);
    }

    /**
     * Waits until an element matching {@code locator} <em>inside this
     * component's container</em> is visible, and returns it.
     *
     * <p>The lookup is scoped to {@link #container} — a match elsewhere on the
     * page (e.g. the first of several cards) is never returned. Do not replace
     * this with a driver-level ExpectedConditions visibility wait, which
     * resolves against the whole page instead of this component.
     */
    protected WebElement waitForVisible(By locator) {
        return wait.until(d -> {
            try {
                WebElement element = container.findElement(locator);
                return element.isDisplayed() ? element : null;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return null;
            }
        });
    }

    protected boolean isDisplayed() {
        try {
            return container.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
