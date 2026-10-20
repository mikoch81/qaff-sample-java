package com.example.qaffsamplejava.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Example Page Object demonstrating best practices.
 * 
 * This is a template class marked @Disabled. Use it as a reference when
 * writing your own page objects. Do not run it directly.
 * 
 * Best practices demonstrated:
 * - Locators defined as private static final By fields
 * - Explicit waits before element interactions
 * - Single responsibility (one logical page or component)
 * - Fluent interface returning page objects
 * - Clear, intention-revealing method names
 */
@Deprecated(since = "generated", forRemoval = false)
public class ExamplePageObject {
  private WebDriver driver;
  private WebDriverWait wait;

  // Locators — keep them together for easy updates
  private static final By USERNAME_INPUT = By.id("username");
  private static final By PASSWORD_INPUT = By.id("password");
  private static final By LOGIN_BUTTON = By.xpath("//button[contains(text(), 'Login')]");
  private static final By ERROR_MESSAGE = By.className("error-alert");
  private static final By DASHBOARD_HEADING = By.tagName("h1");

  /**
   * Constructor. Initialize WebDriver and WebDriverWait.
   */
  public ExamplePageObject(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  /**
   * Navigate to this page. In real code, pass the base URL.
   */
  public ExamplePageObject navigateTo(String url) {
    driver.get(url);
    return this;
  }

  /**
   * Enter username. Wait for element presence first.
   */
  public ExamplePageObject enterUsername(String username) {
    wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_INPUT));
    driver.findElement(USERNAME_INPUT).clear();
    driver.findElement(USERNAME_INPUT).sendKeys(username);
    return this;
  }

  /**
   * Enter password. Wait before interaction.
   */
  public ExamplePageObject enterPassword(String password) {
    wait.until(ExpectedConditions.presenceOfElementLocated(PASSWORD_INPUT));
    driver.findElement(PASSWORD_INPUT).clear();
    driver.findElement(PASSWORD_INPUT).sendKeys(password);
    return this;
  }

  /**
   * Click login button and return the next page object.
   */
  public ExamplePageObject clickLogin() {
    wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    driver.findElement(LOGIN_BUTTON).click();
    return this;
  }

  /**
   * Check if error message is displayed. Useful in negative test cases.
   */
  public boolean isErrorDisplayed() {
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(ERROR_MESSAGE));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Get error message text.
   */
  public String getErrorMessage() {
    return driver.findElement(ERROR_MESSAGE).getText();
  }

  /**
   * Check if dashboard is loaded (example of assertion in page object).
   */
  public boolean isDashboardLoaded() {
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(DASHBOARD_HEADING));
      return driver.findElement(DASHBOARD_HEADING).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}
