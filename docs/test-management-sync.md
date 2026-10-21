# Test-management sync (opt-in)

Generated because `integrations.xray` / `integrations.testrail` / `integrations.jira` / `integrations.zephyr`
and the matching `ci.<provider>.<xraySync|testrailSync|jiraComment|zephyrSync>`
flags are on. After the tests, each step runs `qaff sync-test-results --mode live` (Zephyr:
`qaff sync-zephyr-results`)
for the selected app (TypeScript: every app with web-desktop Allure results).

- **GitHub Actions** — TestRail / Jira comment.
- **Azure Pipelines** — Zephyr Scale / TestRail / Jira comment.

Azure Pipelines maps the secret pipeline variables through the step `env:` (an
undefined secret variable reaches the step as the literal `$(NAME)` text, so the
sync fails with an auth error rather than a missing-variable message);
GitLab CI runs one `.post` job per connector (`when: always`, reading the
test job's Allure artifacts and masked CI/CD variables); Bitbucket Pipelines
runs an `after-script` on the test step (secured repository variables — a
failed sync there never changes the step result). On Azure and Bitbucket the
sync steps run before the run notification; on GitLab they share the `.post`
stage with the notification jobs, so their order is not defined.

The steps run on every pipeline run, including pull-request / merge-request
pipelines (no branch gating, as on Jenkins / GitHub Actions since Phase 105):
add a branch condition or keep `mode: dry-run` if you want main-only sync.

## What is sent

- **Zephyr Scale Cloud** — `qaff sync-zephyr-results --mode dry-run` (Azure / GitLab / Bitbucket steps; the `ZEPHYR_PROJECT_KEY` / `ZEPHYR_CYCLE_KEY` CI variables override the config keys).
- **TestRail** — one `POST index.php?/api/v2/add_results_for_cases/1` to the existing run; skipped tests are left out (TestRail rejects Untested on add_result).
- **Jira** — one comment (`POST /rest/api/3/issue/DEMO-1/comment`) with the totals and a link to the CI run.

Case keys come from the Allure `tms` link, a `tms` / `testCaseKey` label or the
mapping file. No test cases, runs or issues are created remotely.
Zephyr posts test executions into the existing cycle (`ZEPHYR_CYCLE_KEY` /
`integrations.zephyr.cycleKey`); it creates no test cases or cycles.

## Secrets

- `ZEPHYR_BASE_URL`
- `ZEPHYR_TOKEN`
- `TESTRAIL_URL`
- `TESTRAIL_USER`
- `TESTRAIL_API_KEY`
- `JIRA_BASE_URL`
- `JIRA_USER`
- `JIRA_TOKEN`

Read only from the CI environment (GitHub repository secrets, Jenkins agent
environment, Azure secret pipeline variables, GitLab masked CI/CD variables,
Bitbucket secured repository variables) — never from the qaff config, `qa-factory.lock.json` or generated files.

## Local preview

```bash
qaff sync-test-results --provider testrail --project . --app <app-id> --run-id 1 --mapping config/testrail-mapping.json
```

Without `--mode live` nothing is sent: the request is written to
`.qaff/test-results/<provider>.<app>.request.json` (use `--force` to overwrite).
