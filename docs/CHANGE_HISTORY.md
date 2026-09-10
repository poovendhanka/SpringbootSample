# Change History

This history summarizes the changes present on the `Stage` branch.

| Date | Commit | Change |
|---|---|---|
| 2026-09-03 | `081c92f` | Created the repository. |
| 2026-09-03 | `7875fd7` | Added the Spring Boot sample application, order controller, and in-memory order service. |
| 2026-09-03 | `1f2142d` | Added `POST /orders/{id}/canceled` to retrieve an order through the cancellation-check route. |
| 2026-09-03 | `db52adf` | Added `GET /orders/{id}/summary`, currently backed by the same complete order response as the standard get endpoint. |
| 2026-09-10 | `ff24d99` | Added the Stage-branch workflow for generating and committing documentation. |
| 2026-09-10 | `605a717` | Added controller logging when creating an order. |
| 2026-09-10 | `3fab46f` | Revised the documentation workflow to use the GitHub Copilot CLI. |
| 2026-09-10 | `1d3b140` | Fixed the documentation workflow's missing trailing newline. |
| 2026-09-10 | `12c359f` | Added controller logging when retrieving an order. |
| 2026-09-10 | `410c04f` | Updated the documentation workflow's Copilot invocation and permissions. |

## Current state

The latest Stage commit is `fa83170`, which merges the workflow update. The application remains an in-memory order service with the endpoints documented in [API.md](API.md). Documentation generation is automated by `.github/workflows/update-documentation.yml`; the workflow ignores documentation-only pushes to avoid recursive runs.
