# CI / CD strategy

This generated framework wires **GitHub Actions** (Phase 1.9) and **Azure Pipelines** (Phase 14) as runners.

## Generated runners

| Runner | Entry point | Notes |
| --- | --- | --- |
| GitHub Actions (Phase 1.9) | [`../.github/workflows/qaff-ci.yml`](../.github/workflows/qaff-ci.yml) | Runs on `pull_request` and `workflow_dispatch`. Selected app + layer via dispatch inputs. |
| Azure Pipelines (Phase 14) | [`../ci/azure-pipelines/azure-pipelines.yml`](../ci/azure-pipelines/azure-pipelines.yml) | Manual/PR pipeline with app+layer selection variables. |

## GitHub Actions workflow shape

A single workflow at `.github/workflows/qaff-ci.yml`:

- **on `pull_request`** — runs the full Maven reactor
  (`mvn -B verify`).
- **on `workflow_dispatch`** — accepts `application`, `layer`,
  `environment`, `suite`, `browser` and `headless` inputs. When
  both `application` and `layer` are concrete values (not `all`),
  the workflow narrows the build to
  `mvn -B -pl apps/<application>/<layer> -am verify`.

Surefire reports, Allure raw results and (under QI)
`framework-performance/reports/` are uploaded as workflow artifacts.

See [`github-actions.md`](github-actions.md) for prerequisites,
manual-dispatch instructions and current limitations.

## What feeds which gate

| Gate | Source | Where it lives | Status today |
| --- | --- | --- | --- |
| Functional smoke | JUnit 5 + Selenium (web-desktop) / RestAssured (api) | `apps/<id>/<layer>/target/surefire-reports/` | ✅ active |
| Allure trend | Allure raw results | `apps/<id>/<layer>/target/allure-results/` | ✅ archived; report rendering left to the Allure consumer |
| Dependency direction | `maven-enforcer-plugin` + qaff post-generation validator | every `mvn validate` / `verify` (incl. CI) and `qaff create` / `qaff verify` | ✅ active |
| Performance trend | Quality Impact Pack outputs | n/a | Pack not enabled in this project. Re-run `qaff create` with `packs.qualityImpact: true` to scaffold it. |

## Installing qaff in CI

`ci.qaffInstall` is set to `./qa-framework-factory-cli-0.4.0.tgz`. Every generated step that calls `qaff`
first puts `$HOME/.qaff/bin` on PATH and, when `qaff` is still missing, runs
`npm install -g --prefix "$HOME/.qaff" ./qa-framework-factory-cli-0.4.0.tgz`. When the image has no npm
(e.g. `maven:3.9.6-eclipse-temurin-21`) it first downloads Node.js
24.21.0 (linux-x64) into `$HOME/.qaff/node`. No root is needed.

- A relative tarball path (e.g. `./tools/qaff/qa-framework-factory-cli-<version>.tgz`)
  must be committed to this repository; a registry spec needs an `.npmrc`.
- ARM and Windows agents must provide Node.js themselves (the download is linux-x64 only).
- The steps download Node.js from nodejs.org and the package from the spec — the
  agent needs that network access. The Node.js tarball is fetched over HTTPS
  without a checksum check; bake Node.js into the image if that is not acceptable.

## Out of scope today

- **Test-management sync is opt-in only.** Zephyr Scale Cloud / TestRail / Jira comment steps are enabled only for explicitly configured CI providers and require user-managed credentials/environment variables. No test cases, runs or issues are created (see `test-management-sync.md`).
- **No CI-wired performance provider execution.** The `performance` layer module
  runs its JUnit 5 placeholder under default CI. The local Gatling reference
  smoke is explicit opt-in via `run-gatling-smoke` or direct Maven.
- **No Jenkins** in this project. Re-run `qaff create` with `ci.jenkins.enabled: true` to scaffold it.
- **No GitLab CI** in this project. Re-run `qaff create` with `ci.gitlabCi.enabled: true` to scaffold it.
- **No application deployment.** Generated runners run tests; the only
  deployment is the opt-in Pages publish of the Allure report (below).
- **No inline secrets.** Generated CI files contain no credential values.
  The opt-in sync steps read user-managed CI secrets / environment
  variables (`ZEPHYR_BASE_URL`, `ZEPHYR_TOKEN`, `TESTRAIL_URL`, `TESTRAIL_USER`, `TESTRAIL_API_KEY`, `JIRA_BASE_URL`, `JIRA_USER`, `JIRA_TOKEN`); nothing is stored in
  generated files. The opt-in Azure / GitLab / Bitbucket test-management sync
  steps read ZEPHYR_BASE_URL / ZEPHYR_TOKEN / TESTRAIL_URL / TESTRAIL_USER / TESTRAIL_API_KEY / JIRA_BASE_URL / JIRA_USER / JIRA_TOKEN the same way. The opt-in run notification step reads SLACK_WEBHOOK_URL / TEAMS_WEBHOOK_URL the same way.

## Where to find more

- [`github-actions.md`](github-actions.md) — prerequisites and manual-dispatch instructions for GitHub Actions.
- [`../.github/workflows/qaff-ci.yml`](../.github/workflows/qaff-ci.yml) — the workflow itself.

## Notifications (opt-in)

Run notifications are enabled for: `githubActions`, `azurePipelines`.
See [`notifications.md`](notifications.md) for the secrets and per-provider behaviour.

## Pages publishing (opt-in)

The Allure HTML report is published to Pages for: `githubActions` (GitHub Pages) — main /
default branch only, also after failed tests. Setup and URLs: [`github-actions.md`](github-actions.md).

## Test-management sync on Azure / GitLab / Bitbucket (opt-in)

Enabled for: `azurePipelines` (Zephyr Scale / TestRail / Jira comment). See [`test-management-sync.md`](test-management-sync.md).
