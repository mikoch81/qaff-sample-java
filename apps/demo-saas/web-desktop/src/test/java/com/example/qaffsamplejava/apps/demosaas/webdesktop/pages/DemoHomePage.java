package com.example.qaffsamplejava.apps.demosaas.webdesktop.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.example.qaffsamplejava.web.config.SmokeExpectations;
import com.example.qaffsamplejava.web.page.BasePage;

/**
 * Home page of the smoke target. The elements it looks for come from
 * {@link SmokeExpectations} (config.properties {@code smoke.*} keys), so the
 * same page object works for the bundled fixture and for your real application.
 */
public class DemoHomePage extends BasePage {

    private final By heading;
    private final By item;

    public DemoHomePage(WebDriver driver, Duration timeout) {
        this(driver, timeout, SmokeExpectations.defaults());
    }

    public DemoHomePage(WebDriver driver, Duration timeout, SmokeExpectations smoke) {
        super(driver, timeout);
        this.heading = By.cssSelector(smoke.headingSelector());
        this.item = By.cssSelector(smoke.itemSelector());
    }

    public DemoHomePage open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    public String headingText() {
        return driver.findElement(heading).getText();
    }

    public int itemCount() {
        return driver.findElements(item).size();
    }
}
