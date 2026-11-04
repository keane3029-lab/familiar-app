# Contributing to Familiar

Thank you for your interest in contributing to **Familiar**! Our goal is to build family tools that foster mutual trust, protect privacy, and respect teen autonomy.

## Guidelines

1. **Privacy-First Design**: Never introduce features that capture message contents, browser history, or keystrokes. Monitoring must remain high-level (categories and duration only).
2. **Mutual Trust**: Always ensure features maintain transparency—what parents can see, teens can also see on their own screen.
3. **No Dark Patterns**: Avoid guilt notifications, shame mechanics, or streak punishments.
4. **Code Quality**:
   - Language: Java 17+ for Android
   - Maintain unit tests for core logic (`RuleEngineTest`, `TrustManagerTest`)
   - Ensure all secrets are kept in `local.properties` or `.env` and never committed.

## Mobile & On-Device Development
Familiar is designed to be buildable and testable directly on Android devices (e.g., using AIDE, Termux, or Mobile Gradle wrapper). Please ensure all new scripts and build tasks remain mobile-friendly.

## Submission Process
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/mutual-trust-improvement`).
3. Run test suites (`./gradlew test`).
4. Submit a Pull Request with a clear description of the problem solved.
