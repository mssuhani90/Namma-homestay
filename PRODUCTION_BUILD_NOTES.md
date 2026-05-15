# Production build notes (Namma-HomeStay)

## What is already changed
- `app/build.gradle`: `release` now has `minifyEnabled true` and `shrinkResources true`
- `app/proguard-rules.pro`: added keep/dontwarn rules for Compose/Firebase/Coroutines
- `app/src/main/AndroidManifest.xml`: set `android:usesCleartextTraffic="false"`

## Why build verification is not completed here
This agent environment cannot reliably execute `gradlew` commands (command parsing / wrapper resolution issues). Therefore I could not run `assembleRelease` to confirm compilation + R8.

## Run locally to verify
From `Namma-HomeStay/`:

1) Clean + release build
- Windows cmd/powershell:
  - `gradlew.bat :app:assembleRelease`

2) If success, also build bundle for store
- `gradlew.bat :app:bundleRelease`

## What I will do after successful build
- Navigation Compose + NavHost refactor
- ViewModel + StateFlow architecture
- Firebase end-to-end implementations (profile/menu/image upload)
- FirebaseAuth host gate
- UX: loading/error states and button disabling

