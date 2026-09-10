name: Update Code Documentation

on:
  workflow_dispatch:

  pull_request:
    types: [closed]
    branches:
      - main

permissions:
  contents: write

jobs:
  update-documentation:
    if: >
      github.event_name == 'workflow_dispatch' ||
      github.event.pull_request.merged == true

    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v6
        with:
          ref: main
          fetch-depth: 0

      - name: Setup Node.js
        uses: actions/setup-node@v7

      - name: Install GitHub Copilot CLI
        run: npm install -g @github/copilot

      - name: Generate documentation
        env:
          COPILOT_GITHUB_TOKEN: ${{ github.token }}
          PR_NUMBER: ${{ github.event.pull_request.number }}
          PR_TITLE: ${{ github.event.pull_request.title }}
          MERGE_SHA: ${{ github.event.pull_request.merge_commit_sha }}
        run: |
          if [ -z "$PR_NUMBER" ]; then
            PROMPT="Use the /code-documentation skill. Analyze the complete current repository and initialize or refresh the architecture, API and change-history documentation."
          else
            PROMPT="Use the /code-documentation skill. Analyze merged PR #$PR_NUMBER titled '$PR_TITLE'. Inspect merge commit $MERGE_SHA and its changed code, understand the surrounding implementation, and update the architecture, API and change-history documentation."
          fi

          copilot -p "$PROMPT" \
            --allow-tool='shell(git:*)' \
            --allow-tool=write \
            --no-ask-user

      - name: Commit documentation
        run: |
          git config user.name "github-actions[bot]"
          git config user.email "41898282+github-actions[bot]@users.noreply.github.com"

          git add docs/

          if git diff --cached --quiet; then
            echo "No documentation changes."
          else
            git commit -m "docs: update generated code documentation"
            git push
          fi
