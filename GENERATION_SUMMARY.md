# Generation Summary

`qaff create` produced a real, buildable Java/Maven/Selenium/JUnit5/Allure
framework from the inputs below.

## Inputs

- **Config:** `qaff-sample-java.yaml`
- **Project:** `qaff-sample-java`
- **Java target:** `21`
- **Blueprint:** `java-maven-selenium-junit5-allure` v0.1.0

## Selected blueprint

- **id:** `java-maven-selenium-junit5-allure`
- **version:** 0.1.0
- **name:** Java + Maven + Selenium 4 + JUnit 5 + Allure
- **maturity:** stable
- **description:** Default web-automation blueprint. Generates a Maven multi-module Java framework with Selenium 4, JUnit 5 and Allure 2 reporting. Targets Java 21 (default) with Java 17 as a compatibility option.
- **language:** java
- **buildTool:** maven
- **testRunner:** junit5
- **automationTools:** selenium-4, webdrivermanager
- **reportingTools:** allure-2
- **supportedChannels:** web-desktop
- **supportedIntegrations:** github-actions, jenkins
- **supportedQualityPacks:** integration, api, performance
- **outputStructure:** pom.xml, framework-core/, framework-web/, apps/, qa-factory.lock.json
- **manifest path:** `java-maven-selenium-junit5-allure/blueprint.yaml`

## Applications

- `apps/demo-saas` — Demo SaaS  (layers: web-desktop)

## Files written

```
.github/workflows/qaff-ci.yml
.gitignore
apps/demo-saas/web-desktop/pom.xml
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/ExampleTestData.java
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/ExampleWebTest.java
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/pages/DemoHomePage.java
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/pages/ProductCardComponent.java
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/tests/DemoHomeSmokeTest.java
apps/demo-saas/web-desktop/src/test/java/com/example/qaffsamplejava/apps/demosaas/webdesktop/tests/DemoProductCardTest.java
apps/demo-saas/web-desktop/src/test/resources/allure.properties
apps/demo-saas/web-desktop/src/test/resources/config.properties
apps/demo-saas/web-desktop/src/test/resources/fixtures/demo-saas.html
ci/azure-pipelines/azure-pipelines.yml
config/testrail-mapping.json
docs/agent-validation-checklist.md
docs/ai-api-test-authoring.md
docs/ai-authoring-readiness.md
docs/ai-web-test-authoring.md
docs/allure-step-mapping-contract.md
docs/allure-step-policy.md
docs/azure-pipelines.md
docs/ci-cd.md
docs/DEVELOPER_GUIDE.md
docs/GENERATED_FILE_OWNERSHIP.md
docs/generated-architecture.md
docs/generated-test-review-contract.md
docs/github-actions.md
docs/HOW_TO_ADD_PAGE_OBJECT.md
docs/HOW_TO_ADD_WEB_TEST.md
docs/HOW_TO_INTERPRET_VERIFY.md
docs/HOW_TO_KEEP_DEPENDENCY_DIRECTION_VALID.md
docs/HOW_TO_RUN_TESTS_LOCALLY.md
docs/HOW_TO_TARGET_YOUR_APP.md
docs/HOW_TO_USE_ALLURE.md
docs/HOW_TO_USE_GENERATED_SCRIPTS.md
docs/HOW_TO_USE_JUNIT5.md
docs/how-to-run.md
docs/local-fixture.md
docs/local-scripts.md
docs/locator-evidence-contract.md
docs/locator-policy.md
docs/notifications.md
docs/scenario-contract.md
docs/test-authoring-request.md
docs/test-management-sync.md
framework-core/pom.xml
framework-core/src/main/java/com/example/qaffsamplejava/core/config/ConfigLoader.java
framework-core/src/main/java/com/example/qaffsamplejava/core/util/ResourcePath.java
framework-core/src/main/java/com/example/qaffsamplejava/core/util/Resources.java
framework-core/src/main/java/com/example/qaffsamplejava/core/util/Retry.java
framework-core/src/main/java/com/example/qaffsamplejava/core/util/SoftAssertionHelper.java
framework-core/src/main/java/com/example/qaffsamplejava/core/util/TestDataBuilder.java
framework-web/pom.xml
framework-web/src/main/java/com/example/qaffsamplejava/web/config/SmokeExpectations.java
framework-web/src/main/java/com/example/qaffsamplejava/web/config/WebConfig.java
framework-web/src/main/java/com/example/qaffsamplejava/web/driver/WebDriverFactory.java
framework-web/src/main/java/com/example/qaffsamplejava/web/extension/RegressionTest.java
framework-web/src/main/java/com/example/qaffsamplejava/web/extension/RetryExtension.java
framework-web/src/main/java/com/example/qaffsamplejava/web/extension/RetryingTest.java
framework-web/src/main/java/com/example/qaffsamplejava/web/extension/ScreenshotOnFailureExtension.java
framework-web/src/main/java/com/example/qaffsamplejava/web/extension/SmokeTest.java
framework-web/src/main/java/com/example/qaffsamplejava/web/page/BasePage.java
framework-web/src/main/java/com/example/qaffsamplejava/web/page/PageComponent.java
framework-web/src/main/java/com/example/qaffsamplejava/web/test/BaseWebMobileTest.java
framework-web/src/main/java/com/example/qaffsamplejava/web/test/BaseWebTest.java
framework-web/src/test/java/com/example/qaffsamplejava/web/page/ExamplePageObject.java
pom.xml
qa-factory.lock.json
README.md
scripts/allure-report.ps1
scripts/allure-report.sh
scripts/run-all-tests.ps1
scripts/run-all-tests.sh
scripts/run-android-smoke.ps1
scripts/run-android-smoke.sh
scripts/run-api-smoke.ps1
scripts/run-api-smoke.sh
scripts/run-gatling-smoke.ps1
scripts/run-gatling-smoke.sh
scripts/run-integration-smoke.ps1
scripts/run-integration-smoke.sh
scripts/run-ios-smoke.ps1
scripts/run-ios-smoke.sh
scripts/run-jmeter-import-smoke.ps1
scripts/run-jmeter-import-smoke.sh
scripts/run-k6-import-smoke.ps1
scripts/run-k6-import-smoke.sh
scripts/run-mobile-appium-smoke.ps1
scripts/run-mobile-appium-smoke.sh
scripts/run-performance-smoke.ps1
scripts/run-performance-smoke.sh
scripts/run-web-mobile-smoke.ps1
scripts/run-web-mobile-smoke.sh
scripts/run-web-smoke.ps1
scripts/run-web-smoke.sh
scripts/verify-framework.ps1
scripts/verify-framework.sh
GENERATION_SUMMARY.md
```

## Post-generation validation

- **Dependency direction:** PASSED
- **Modules checked:** 3
- **Rule:** `apps/* -> framework-*` allowed; `framework-* -> apps/*` forbidden.

## Build it

```bash
mvn verify
```
