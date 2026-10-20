package com.example.qaffsamplejava.web.page;

import java.time.Duration;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object base class with common explicit-wait helpers.
 * Each interaction method is annotated with {@link Step} so it appears as a
 * named step in the Allure report.
 * Subclasses get a pre-configured {@link WebDriverWait} and driver reference.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    @Step("Wait for visible: {locator}")
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Click: {locator}")
    protected void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Type '{text}' into {locator}")
    protected void waitAndType(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    @Step("Scroll into view")
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Step("Select '{text}' in {locator}")
    protected void selectByVisibleText(By locator, String text) {
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    @Step("Hover over {locator}")
    protected void hover(By locator) {
        new Actions(driver).moveToElement(waitForVisible(locator)).perform();
    }

    @Step("Wait for clickable: {locator}")
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Wait for invisible: {locator}")
    protected boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @Step("Get text of {locator}")
    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    /**
     * Execute arbitrary JavaScript in the current page context.
     * Convenience wrapper around {@link JavascriptExecutor}.
     */
    protected Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /** Return the current page title. */
    protected String getTitle() {
        return driver.getTitle();
    }

    /** Return the current page URL. */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
