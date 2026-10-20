# How to add a page object

## Overview

Page Object Model (POM) encapsulates page structure and locators. This reduces duplication and makes tests more maintainable.

## Structure

Create a class for each logical page or component:

```java
public class LoginPage {
  private WebDriver driver;
  private WebDriverWait wait;

  // Locators
  private By emailInput = By.id("email");
  private By passwordInput = By.id("password");
  private By submitButton = By.xpath("//button[text()='Login']");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Page actions
  public void enterEmail(String email) {
    wait.until(ExpectedConditions.presenceOfElementLocated(emailInput));
    driver.findElement(emailInput).sendKeys(email);
  }

  public void enterPassword(String password) {
    driver.findElement(passwordInput).sendKeys(password);
  }

  public void clickSubmit() {
    driver.findElement(submitButton).click();
  }
}
```

## Best Practices

1. **One responsibility per class** — Each page object handles one logical page or component.
2. **Locators at the top** — Group all `By` definitions together.
3. **Waits before interactions** — Always wait for element presence before clicking or typing.
4. **Return page objects** — Navigation methods should return the next page object for fluent chains.

## Example

See `ExamplePageObject.java` in `framework-web/src/test/java` for a complete template.
