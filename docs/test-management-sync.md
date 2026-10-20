# Test-management sync (opt-in)

Generated because `integrations.xray` / `integrations.testrail` / `integrations.jira`
and the matching `ci.<jenkins|githubActions>.<xraySync|testrailSync|jiraComment>`
flags are on. After the tests, each step runs `qaff sync-test-results --mode live`
for the selected app (TypeScript: every app with web-desktop Allure results).

- **GitHub Actions** — Jira comment.

## What is sent

- **Jira** — one comment (`POST /rest/api/3/issue/DEMO-1/comment`) with the totals and a link to the CI run.

Case keys come from the Allure `tms` link, a `tms` / `testCaseKey` label or the
mapping file. No test cases, runs or issues are created remotely.

## Secrets

- `JIRA_BASE_URL`
- `JIRA_USER`
- `JIRA_TOKEN`

Read only from the CI environment (GitHub repository secrets, Jenkins agent
environment) — never from the qaff config, `qa-factory.lock.json` or generated files.

## Local preview

```bash
qaff sync-test-results --provider jira-comment --project . --app <app-id> --issue-key DEMO-1
```

Without `--mode live` nothing is sent: the request is written to
`.qaff/test-results/<provider>.<app>.request.json` (use `--force` to overwrite).
