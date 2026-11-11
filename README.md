# Narendra AppLock

Android native Kotlin starter project. Default first-run PIN: **8546**.

## Build APK using a phone
1. Create a GitHub repository and upload the contents of this ZIP (including the hidden `.github` folder).
2. Open the repository's **Actions** tab.
3. Run **Build Narendra AppLock APK** (or push a commit).
4. Open the completed workflow run, open **Artifacts**, and download `Narendra-AppLock-debug`.

## First launch
- Enter PIN `8546`.
- Enable Accessibility for Narendra AppLock in Android Accessibility settings.
- Allow display over other apps.
- Enable usage/access monitoring if Android asks.
- Select supported apps in the dashboard.

## Important limitations
- This is a starter implementation, not a Play-Store-ready security product. Android/OEM background restrictions can affect monitoring.
- App-lock coverage is currently limited to the package list shown in the app. Extend the list to support more apps.
- The private vault copies selected media into the app's internal private files directory. It does not yet encrypt files or automatically delete the originals. Delete originals yourself only after confirming the import.
- The initial PIN is intentionally `8546`; change it immediately after setup. A production release should use a secure PIN hash, encrypted vault, recovery flow, accessibility-service disclosure, and extensive device testing.
- Accessibility and overlay permissions are powerful. The service only watches window changes to show a lock screen for selected packages; it does not read screen contents.
