# How to run this framework from Azure Pipelines

This file documents the generated pipeline at
[`ci/azure-pipelines/azure-pipelines.yml`](../ci/azure-pipelines/azure-pipelines.yml).

## Parameters

- `APPLICATION`: `all`, `demo-saas`
- `LAYER`: `all`, `web-desktop`
- `ENVIRONMENT`: logical label only
- `SUITE`: optional JUnit tag expression
- `BROWSER`: browser id passed to system properties
- `HEADLESS`: `true` / `false`

When `APPLICATION` and `LAYER` are concrete values, the generated pipeline
runs `mvn -B -ntp -pl apps/<application>/<layer> -am verify`. Otherwise it
runs the full reactor `mvn -B -ntp verify`.

## Artifacts

- Surefire XML reports from `**/target/surefire-reports/*.xml`
- Allure raw results (published as build artifacts)
- QI reports are not generated in this project (`packs.qualityImpact: false`).

## Limitations

- No deployment and no cloud login steps.
- No Jira / Zephyr / Xray / TestRail integration.
- No inline secrets. The opt-in test-management sync steps map ZEPHYR_BASE_URL / ZEPHYR_TOKEN / TESTRAIL_URL / TESTRAIL_USER / TESTRAIL_API_KEY / JIRA_BASE_URL / JIRA_USER / JIRA_TOKEN
  through `env:`. The opt-in run notification step maps the secret pipeline
  variable(s) SLACK_WEBHOOK_URL / TEAMS_WEBHOOK_URL through `env:`.

## Notifications (opt-in)

`ci.azurePipelines.notify` is on: the pipeline ends with one step that runs
`qaff notify-run --mode live` for Slack / Teams and posts one message. Define
`SLACK_WEBHOOK_URL` and `TEAMS_WEBHOOK_URL` as a CI secret — the URL is never stored in
config, the lock or this repository. See [`notifications.md`](notifications.md).

## Test-management sync (opt-in)

`ci.azurePipelines.zephyrSync`, `ci.azurePipelines.testrailSync`, `ci.azurePipelines.jiraComment` are on: after the tests the pipeline runs the
Zephyr Scale / TestRail / Jira comment sync. Define `ZEPHYR_BASE_URL`, `ZEPHYR_TOKEN`, `TESTRAIL_URL`, `TESTRAIL_USER`, `TESTRAIL_API_KEY`, `JIRA_BASE_URL`, `JIRA_USER`, `JIRA_TOKEN` as CI
secret variables — never in config, the lock or this repository. See
[`test-management-sync.md`](test-management-sync.md).
