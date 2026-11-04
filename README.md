# Familiar - Native Android Parenting & Family Trust App

**Familiar** (`com.familiar.app`) is a native Android parenting application written in Java. Familiar redefines family safety by building mutual trust rather than unilateral control. In Familiar, parents and teens negotiate rules together, teens keep growing their independence, monitoring is transparent and high-level, and family arguments are prevented through structured check-ins and cooldown periods.

---

## 📱 Building on a Phone

Familiar is designed with a phone-friendly setup so developers or users can open, inspect, modify, and build the project directly from an Android mobile device.

### Method 1: Using Termux + Gradle Wrapper (On Phone)
1. Install **Termux** on your Android phone.
2. Install OpenJDK 17 and Git inside Termux:
   ```bash
   pkg update && pkg install openjdk-17 git
   ```
3. Unzip the project:
   ```bash
   unzip familiar-app.zip -d familiar
   cd familiar
   ```
4. Build the debug APK directly on your phone:
   ```bash
   ./gradlew assembleDebug
   ```
5. The output APK will be located at `app/build/outputs/apk/debug/app-debug.apk`. Tap to install!

### Method 2: Using Mobile IDEs (e.g. AIDE or Android Studio Remote)
1. Open your mobile IDE or file manager.
2. Select **Open Existing Project** and navigate to the extracted `familiar` root directory containing `settings.gradle` and `build.gradle`.
3. The IDE will index Java source files in `app/src/main/java` and layouts in `app/src/main/res/layout`.

### Method 3: Desktop Android Studio Setup
1. Extract `familiar-app.zip`.
2. Open Android Studio -> **Open an Existing Project** -> Select the project root folder.
3. Sync Gradle and run on an emulator or physical device running Android 8.0+ (API 26+).

---

## 🏗️ Project Structure

```
Familiar/
├── .gitignore
├── LICENSE (MIT)
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

## 🎯 How Each Problem is Solved

| # | Problem | Specific Feature & Solution | Primary Source File(s) |
|---|---|---|---|
| 1 | **Too Much Control** | Proposed and agreed rules model. Parent proposes or sets rules, teen accepts/proposes compromises. Explicit distinction between parent-set rules and teen-decided settings. | `Rule.java`<br>`RuleEngine.java`<br>`RuleManagerFragment.java` |
| 2 | **Loss of Privacy** | Default complete transparency. The teen screen displays the exact dashboard the parent sees. Category and screen time totals only ("2h 10m social media today"), zero message content, browsing history, or private messages monitored. | `TransparencyFragment.java`<br>`UsageSummary.java`<br>`PrivacyPolicyActivity.java` |
| 3 | **Limited Freedom** | Tiered trust levels (Level 1 to Level 4). Teens unlock higher levels by meeting agreed goals over time. Each level grants specific freedom parameters (bedtime flexibility, app choices, purchase approvals). | `TrustLevel.java`<br>`TrustManager.java`<br>`TrustFragment.java` |
| 4 | **Trust Issues** | Shared trust log. Both parent and teen log kept promises and missed promises. Trust progress score is visible to both and changes only through agreed milestones. | `TrustLogEntry.java`<br>`TrustDao.java`<br>`TrustFragment.java` |
| 5 | **Communication Problems** | "Talk before block" check-in feature. Before a restriction is enforced, the app prompts a conversation. Teen sends an explanation message shown to the parent before decisions are final. | `CheckIn.java`<br>`CheckInDao.java`<br>`CheckInFragment.java` |
| 6 | **Accidental Phone Lockouts** | Emergency override button active even when all restrictions are active. Protected allowlist for school apps, calls, maps, and emergency contacts that can NEVER be locked. 10m/2m warnings & 1-tap grace extension request. | `EmergencyOverrideManager.java`<br>`EmergencyOverrideActivity.java` |
| 7 | **Unfair App Restrictions** | App access request with reason. Parent gets a 24-hour decision window and receives an app usage summary (category, daily average) to make informed decisions. | `AppAccessRequest.java`<br>`AppAccessRequestDao.java`<br>`CheckInFragment.java` |
| 8 | **Technical Problems** | Reliable rule engine with preview mode, conflict detection (overlapping bedtimes/limits), plain-language activity log, and offline-safe Room SQLite local storage. | `RuleEngine.java`<br>`ActivityLogEntry.java`<br>`CloudSyncManager.java`<br>`ScheduledRuleCheckWorker.java` |
| 9 | **Less Independence** | Coaching mode where the teen sets their own weekly screen time goal and writes a short reflection. Parent sees summary view, and the app prompts the teen to propose solutions first. | `CoachingGoal.java`<br>`CoachingDao.java`<br>`CoachingFragment.java` |
| 10 | **Family Arguments** | Family meeting tool with structured agenda (wants, worries, proposed compromise). Agreements are saved as documents. Changes trigger a 48-hour cooldown period and require a written reason. | `FamilyAgreement.java`<br>`FamilyMeetingDao.java`<br>`CoachingFragment.java` |

---

## 🧪 Running Unit Tests

Run JUnit unit tests for the Rule Engine and Trust Level logic via Gradle:
```bash
./gradlew test
```
Test results will be available in `app/build/reports/tests/testDebugUnitTest/index.html`.

---

## 🔒 Privacy & Data Policy
Familiar stores 100% of rules, logs, and summaries locally using an embedded SQLite Room database. No private message content, keystrokes, or browsing histories are recorded. Cloud sync is disabled by default and can be optionally configured in `local.properties`.
