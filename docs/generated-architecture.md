# Generated framework — architecture

## Modules

| Module | Purpose | Allowed dependencies |
| --- | --- | --- |
| `framework-core` | Config + classpath helpers. No Selenium, no JUnit runtime. | (none, leaf) |
| `framework-web` | Selenium WebDriver, base page, base web test. | `framework-core` |
| `apps/<id>/web-desktop` | App-specific page objects + smoke tests. | `framework-web` (test scope) |

## Dependency direction

```
apps/*  ──►  framework-web  ──►  framework-core
```

Reverse arrows are forbidden by design. Apps may not depend on each
other. The post-generation validator that ships with `qaff create`
asserts these rules against the actual POMs and fails generation if any
rule is violated.

## Base package

Everything lives under `com.example.qaffsamplejava`. Each app gets its own
sub-package: `com.example.qaffsamplejava.apps.<sanitized-app-id>.<sanitized-layer>`.

## Generation manifest

`qa-factory.lock.json` records the deterministic generation contract:
generator and blueprint versions, Java version, effective app/layer list,
enabled packs, CI flags, strict quality gate flags, and the Phase 5.0B
`generatedFiles` managed-file snapshot. The snapshot stores SHA-256 hashes
for generated files (excluding the lock itself) so `qaff update --dry-run`
can classify changed files as `rendererDrifted`, `locallyModified`, or
`conflictCandidates`, and report `obsolete` files (recorded in the
snapshot but no longer generated for the current configuration) —
`qaff update --apply` and the remove commands delete those only when they
are unmodified since generation. Current lifecycle commands also maintain
`.qaff/managed-base/`, a content-addressed text base store used only by
`qaff update --apply --merge-conflicts`. This metadata does not make the
generated project depend on QA Framework Factory at runtime.
