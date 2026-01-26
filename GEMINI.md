# Gemini Code Assistant Guide

## Feature Development Workflow

This document outlines the standard workflow for developing and verifying new features in this project. Adhering to this process ensures consistency and code quality.

### 1. Understand the Feature Request

- **Analyze the requirements:** Carefully review the feature request to understand its purpose, scope, and acceptance criteria.
- **Explore the existing codebase:** Use `glob` and `search_file_content` to identify relevant files, modules, and existing patterns. Pay close attention to:
    - **Project structure:** Where should the new code live?
    - **Code style:** Match existing formatting, naming conventions, and architectural patterns.
    - **Dependencies:** Are there existing libraries or utilities that should be used?

### 2. Implementation

- **Write the code:** Implement the feature, following the established conventions of the project.
- **Add unit tests:** Create new unit tests that cover the new functionality. Ensure that all new code paths are tested.
- **Update documentation:** If the feature changes any public-facing APIs or user-visible behavior, update the relevant documentation:
    - **`README.md`**: Update overview, features, or setup instructions.
    - **`docs/api.http`**: Add/Update endpoint test cases for the new feature.
    - **`docs/queries.sql`**: Update with relevant queries for any new tables or data logic.
    - **`docs/arch_designs.md`**: Update Mermaid diagrams for changes in architecture or workflows.

### 3. Verification

- **Run all tests:** Execute the full test suite to ensure that your changes have not introduced any regressions.
- **Run the linter:** Run the project's linter to check for style issues.
- **Build the project:** Ensure that the project builds successfully.
- **Manual verification:** If applicable, manually test the feature to ensure it behaves as expected.

### 4. Commit and Push

- **Commit your changes:** Write a clear and concise commit message that explains the "what" and "why" of your changes.
- **Push your changes:** Push your feature branch to the remote repository.
