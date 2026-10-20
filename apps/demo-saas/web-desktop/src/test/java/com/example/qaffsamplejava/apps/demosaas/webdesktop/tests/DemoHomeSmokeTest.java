package com.example.qaffsamplejava.apps.demosaas.webdesktop.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.junit.jupiter.api.DisplayName;

import com.example.qaffsamplejava.web.extension.SmokeTest;
import com.example.qaffsamplejava.web.test.BaseWebTest;
import com.example.qaffsamplejava.apps.demosaas.webdesktop.pages.DemoHomePage;

@Epic("Demo eCommerce")
@Feature("Home")
public class DemoHomeSmokeTest extends BaseWebTest {

    // @SmokeTest = @Test + @Tag("smoke"): this test is selected by
    // `mvn verify -Dgroups=smoke`.
    @SmokeTest
    @Story("Smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Home page renders the expected heading and at least one item")
    void homePageRenders() {
        String url = resolveSmokeUrl("fixtures/demo-saas.html");

        DemoHomePage page = new DemoHomePage(driver, timeout, smoke).open(url);

        assertEquals(smoke.headingText(), page.headingText(),
                "heading should match smoke.headingText");
        assertTrue(page.itemCount() >= 1,
                "expected at least one element matching smoke.itemSelector");
    }
}
