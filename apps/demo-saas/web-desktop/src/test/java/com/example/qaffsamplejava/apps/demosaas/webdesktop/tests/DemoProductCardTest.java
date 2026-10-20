package com.example.qaffsamplejava.apps.demosaas.webdesktop.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.example.qaffsamplejava.core.util.SoftAssertionHelper;
import com.example.qaffsamplejava.web.config.WebConfig;
import com.example.qaffsamplejava.web.extension.RegressionTest;
import com.example.qaffsamplejava.web.test.BaseWebTest;
import com.example.qaffsamplejava.apps.demosaas.webdesktop.pages.DemoHomePage;
import com.example.qaffsamplejava.apps.demosaas.webdesktop.pages.ProductCardComponent;

/**
 * Reference example that goes beyond the one-line smoke test. It shows four
 * patterns you will reuse constantly:
 *
 * <ul>
 *   <li>composing a reusable {@link ProductCardComponent} ({@code PageComponent});</li>
 *   <li>batching independent checks with {@link SoftAssertionHelper} so one
 *       failure does not hide the others;</li>
 *   <li>a data-driven {@code @ParameterizedTest};</li>
 *   <li>suite tagging with {@code @RegressionTest} (= {@code @Test} +
 *       {@code @Tag("regression")}), selected by
 *       {@code mvn verify -Dgroups=regression}.</li>
 * </ul>
 *
 * <p>Everything runs offline against the bundled fixture. Copy this shape when
 * authoring your own richer tests.
 */
@Epic("Demo eCommerce")
@Feature("Product card")
public class DemoProductCardTest extends BaseWebTest {

    private static final By PRODUCT_CARD = By.cssSelector("[data-testid='product-card']");

    // This example asserts the fixture's own product data; on a real target
    // (target=external) it is skipped rather than failing on unrelated markup.
    @BeforeEach
    void fixtureOnly() {
        Assumptions.assumeTrue(WebConfig.TARGET_FIXTURE.equals(webConfig.target()),
                "fixture-only reference example");
    }

    @RegressionTest
    @Story("Reference example")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Product card exposes name, price and an add-to-cart action (soft assertions)")
    void productCardFieldsAreConsistent() {
        String url = resolveSmokeUrl("fixtures/demo-saas.html");
        new DemoHomePage(driver, timeout).open(url);

        WebElement card = driver.findElement(PRODUCT_CARD);
        ProductCardComponent product = new ProductCardComponent(driver, card, timeout);

        SoftAssertionHelper soft = new SoftAssertionHelper();
        soft.check("product name", () ->
                assertTrue(product.productName().contains("Widget"),
                        "product name should mention the product"));
        soft.check("product price", () ->
                assertTrue(product.priceText().contains("EUR"),
                        "price should be shown with a currency"));
        soft.check("add to cart", () ->
                assertTrue(product.canAddToCart(),
                        "add-to-cart button should be visible"));
        soft.assertAll();
    }

    @ParameterizedTest
    @CsvSource({
        "product-name, Sample Widget",
        "product-price, 19.99 EUR"
    })
    @Story("Reference example")
    @DisplayName("Fixture fields render their expected text")
    void fixtureFieldsRenderExpectedText(String testId, String expected) {
        String url = resolveSmokeUrl("fixtures/demo-saas.html");
        new DemoHomePage(driver, timeout).open(url);

        String actual = driver.findElement(
                By.cssSelector("[data-testid='" + testId + "']")).getText();
        assertEquals(expected, actual, testId + " should render its expected text");
    }
}
