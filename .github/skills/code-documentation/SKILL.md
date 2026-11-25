---
name: code-documentation
description: Analyze a repository's codebase and recent git history to create or update docs/ARCHITECTURE.md, docs/API.md, and docs/CHANGE_HISTORY.md across any language or framework (Java/Spring Boot, Python, Go, JavaScript/TypeScript, React, etc.) without modifying application source code.
---

# Code Documentation Skill

You are responsible for maintaining accurate, up-to-date technical documentation under `docs/` based strictly on the actual repository contents and git history.

## Rules

1. **Never modify application code, tests, or build/config files.** Only create or update files inside `docs/`:
   - `docs/ARCHITECTURE.md`
   - `docs/API.md`
   - `docs/CHANGE_HISTORY.md`
2. **Verify before writing.** Only document files, symbols, endpoints, components, dependencies, and behaviors you directly observe in the repository or `git` output. Do not invent or guess undocumented features.
3. **Preserve existing history.** In `docs/CHANGE_HISTORY.md`, keep previously recorded entries and prepend/append new commits without duplicating already-documented SHAs.
4. **Be language- and framework-agnostic.** Adapt your analysis to whatever stack the repository uses.

## Workflow

### Step 1: Inspect Repository & Recent Changes
Run git and file inspection commands to understand the project:
- Check recent commits and changed files:
  - `git log -n 15 --oneline`
  - `git show --stat HEAD` (or `git diff HEAD~1..HEAD` when applicable)
- Identify the project's language(s), framework(s), and build/package manifests (e.g., `pom.xml`/`build.gradle`, `pyproject.toml`/`requirements.txt`, `go.mod`, `package.json`, `Dockerfile`).
- Read existing `docs/ARCHITECTURE.md`, `docs/API.md`, and `docs/CHANGE_HISTORY.md` if they exist.

### Step 2: Update `docs/ARCHITECTURE.md`
Include:
- **Overview**: What the application/library does.
- **Tech Stack**: Languages, frameworks, runtime versions, and key dependencies observed in manifests.
- **Repository Structure**: High-level directory and module layout.
- **Architecture & Data Flow**: Key layers/components and how data or requests flow through them (include a concise Mermaid diagram if helpful).
- **Configuration & Entrypoints**: Main entrypoints, key config files, and environment variables referenced in code/config.

### Step 3: Update `docs/API.md`
Adapt this file to the repository type:
- **Backend / Web Services** (e.g., Java Spring Boot, Python FastAPI/Flask/Django, Go net/http/Gin, Node Express/NestJS):
  - List HTTP/REST/GraphQL/gRPC endpoints with method, path, controller/handler location, request params/body, response structure, and auth requirements if any.
- **Frontend / UI Apps** (e.g., React, Next.js, Vue):
  - Document routes/pages, key exported components and their props, custom hooks/state stores, and external API calls/integrations.
- **Libraries / CLI Tools / Workers**:
  - Document public modules, classes/functions, CLI commands/flags, or scheduled/polling jobs.
- If the repo combines frontend + backend, include sections for both.

### Step 4: Update `docs/CHANGE_HISTORY.md`
- Record notable recent commits from `git log` (and PR numbers/titles if present in commit messages or context).
- For each entry include: **Date**, **Commit SHA (short)**, **Summary**, and **Key Files/Areas Changed**.
- Avoid duplicating commit SHAs already present in `docs/CHANGE_HISTORY.md`.
