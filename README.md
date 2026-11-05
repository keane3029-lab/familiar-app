# Familiar - Native Android Parenting & Family Trust App

**Familiar** (`com.familiar.app`) is a native Android parenting app written in Java. Familiar is built on mutual trust rather than unilateral control. Parents and teens negotiate rules together, teens keep earning independence, monitoring is transparent and high-level, and family arguments are cooled down with structured check-ins.

Familiar was started on a phone in Termux and is compiled automatically by GitHub Actions.

---

## Status

Early build. The core structure, data models and screens are in place, but many features are still being wired together, so expect rough edges and crashes. Each debug build is produced by the CI workflow in `.github/workflows/build-apk.yml`.

---

## Download

Grab the latest APK from the Releases page:
https://github.com/keane3029-lab/familiar-app/releases

1. Open the latest release.
2. Download `app-debug.apk` from the **Assets** section.
3. Install it on your Android device. You may need to allow installs from your browser or file manager.

Each build from the CI workflow is also available under the **Actions** tab as the `familiar-debug-apk` artifact.

---

## Building on a Phone

Familiar is designed so it can be opened, inspected, modified and built directly from an Android phone.

### Method 1: Termux (on phone)
1. Install **Termux** on your Android phone.
2. Install OpenJDK 17, Git and Gradle inside Termux:
   ```bash
   pkg update && pkg install openjdk-17 git gradle
   ```
3. Clone the repo:
   ```bash
   git clone https://github.com/keane3029-lab/familiar-app.git
   cd familiar-app
   ```
4. Build the debug APK. The repo does not include a Gradle wrapper yet, so use the installed Gradle:
   ```bash
   gradle assembleDebug
   ```
5. The APK ends up in `app/build/outputs/apk/debug/app-debug.apk`. Tap it to install.

### Method 2: Mobile IDE (e.g. AIDE)
1. Open your mobile IDE.
2. Select **Open Existing Project** and choose the project root, the folder that contains `settings.gradle` and `build.gradle`.
3. The IDE will index Java sources in `app/src/main/java` and layouts in `app/src/main/res/layout`.

### Method 3: Desktop Android Studio
1. Clone or extract the project.
2. Open Android Studio, choose **Open** and select the project root.
3. Sync Gradle and run on an emulator or a physical device running Android 8.0 or newer (API 26+).

---

## Features (planned and in progress)

Familiar aims to solve ten common parent-teen problems:

| # | Problem | Planned feature | Primary source file(s) |
|---|---|---|---|
| 1 | Too much control | Proposed and agreed rules. Parents propose, teens accept or counter-propose. Parent-set rules and teen-decided settings are clearly separated. | `Rule.java`, `RuleEngine.java`, `RuleManagerFragment.java` |
| 2 | Loss of privacy | Transparency by default. The teen sees the same dashboard the parent sees. Only category and screen time totals are shown, with no message content, browsing history or private messages. | `TransparencyFragment.java`, `UsageSummary.java`, `PrivacyPolicyActivity.java` |
| 3 | Limited freedom | Tiered trust levels (Level 1 to Level 4). Teens unlock higher levels by meeting agreed goals. Each level grants specific freedoms such as bedtime flexibility, app choices and purchase approvals. | `TrustLevel.java`, `TrustManager.java`, `TrustFragment.java` |
| 4 | Trust issues | Shared trust log. Both sides record kept and missed promises. The trust score only changes through agreed milestones and is visible to both. | `TrustLogEntry.java`, `TrustDao.java`, `TrustFragment.java` |
| 5 | Communication problems | "Talk before block" check-ins. Before a restriction is enforced, the app prompts a conversation, and the teen can send an explanation the parent sees before decisions are final. | `CheckIn.java`, `CheckInDao.java`, `CheckInFragment.java` |
| 6 | Accidental lockouts | Emergency override that works even when restrictions are active. A protected allowlist (school apps, calls, maps, emergency contacts) can never be locked. Warnings at 10 and 2 minutes, plus a one-tap grace extension request. | `EmergencyOverrideManager.java`, `EmergencyOverrideActivity.java` |
| 7 | Unfair app restrictions | App access requests with a reason. The parent gets a 24-hour decision window and a usage summary to decide with. | `AppAccessRequest.java`, `AppAccessRequestDao.java`, `CheckInFragment.java` |
| 8 | Technical problems | Rule engine with preview mode, conflict detection (overlapping bedtimes and limits), a plain-language activity log and offline-safe Room SQLite storage. | `RuleEngine.java`, `ActivityLogEntry.java`, `CloudSyncManager.java`, `ScheduledRuleCheckWorker.java` |
| 9 | Less independence | Coaching mode. The teen sets their own weekly screen time goal, writes a short reflection and proposes solutions first. The parent sees a summary. | `CoachingGoal.java`, `CoachingDao.java`, `CoachingFragment.java` |
| 10 | Family arguments | Family meeting tool with a structured agenda (wants, worries, proposed compromise). Agreements are saved as documents. Changes trigger a 48-hour cooldown and require a written reason. | `FamilyAgreement.java`, `FamilyMeetingDao.java`, `CoachingFragment.java` |

---

## Project Structure

```
familiar-app/
├── .github/workflows/build-apk.yml
├── .gitignore
├── LICENSE
├── CONTRIBUTING.md
├── README.md
├── build.gradle
├── gradle.properties
├── settings.gradle
├── local.properties.example
└── app/
    ├── build.gradle
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── res/
        │   │   ├── values/ (strings.xml, colors.xml, themes.xml)
        │   │   ├── menu/ (bottom_nav_menu.xml)
        │   │   └── layout/ (activity_main.xml, fragment_dashboard.xml, etc.)
        │   └── java/com/familiar/app/
        │       ├── FamiliarApp.java
        │       ├── data/
        │       │   ├── model/ (Rule.java, TrustLevel.java, CheckIn.java, etc.)
        │       │   └── db/ (AppDatabase.java, RuleDao.java, TrustDao.java, etc.)
        │       ├── engine/ (RuleEngine.java, TrustManager.java)
        │       ├── service/ (EmergencyOverrideManager.java)
        │       ├── sync/ (CloudSyncManager.java)
        │       ├── worker/ (ScheduledRuleCheckWorker.java)
        │       └── ui/
        │           ├── MainActivity.java
        │           ├── adapter/ (RuleAdapter.java, TrustLogAdapter.java, etc.)
        │           ├── dashboard/ (DashboardFragment.java)
        │           ├── rules/ (RuleManagerFragment.java)
        │           ├── privacy/ (TransparencyFragment.java, PrivacyPolicyActivity.java)
        │           ├── trust/ (TrustFragment.java)
        │           ├── checkin/ (CheckInFragment.java)
        │           ├── emergency/ (EmergencyOverrideActivity.java)
        │           └── coaching/ (CoachingFragment.java)
        └── test/java/com/familiar/app/
            ├── RuleEngineTest.java
            └── TrustManagerTest.java
```

---

## Running Unit Tests

Run the JUnit tests for the rule engine and trust logic:
```bash
gradle test
```
Results appear in `app/build/reports/tests/testDebugUnitTest/index.html`.

---

## Privacy & Data

Familiar stores rules, logs and summaries locally in an embedded SQLite Room database. No private message content, keystrokes or browsing history are recorded. Cloud sync is off by default and can be configured in `local.properties`.

---

## License

MIT. See `LICENSE`.

## Contributing

See `CONTRIBUTING.md`.
