package com.example.qaffsamplejava.apps.demosaas.webdesktop.pages;

import java.time.Duration;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.example.qaffsamplejava.web.page.PageComponent;

/**
 * Reusable component scoped to a single product card
 * ({@code [data-testid='product-card']}). Demonstrates the {@link PageComponent}
 * pattern: child elements are resolved relative to the component's container,
 * so the same component works for any card on the page.
 */
public class ProductCardComponent extends PageComponent {

    private static final By NAME = By.cssSelector("[data-testid='product-name']");
    private static final By PRICE = By.cssSelector("[data-testid='product-price']");
    private static final By ADD_TO_CART = By.cssSelector("[data-testid='add-to-cart']");

    public ProductCardComponent(WebDriver driver, WebElement container, Duration timeout) {
        super(driver, container, timeout);
    }

    @Step("Read product name")
    public String productName() {
        return container.findElement(NAME).getText();
    }

    @Step("Read product price")
    public String priceText() {
        return container.findElement(PRICE).getText();
    }

    @Step("Check the add-to-cart button is available")
    public boolean canAddToCart() {
        return container.findElement(ADD_TO_CART).isDisplayed();
    }
}
