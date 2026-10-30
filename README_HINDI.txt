NARENDRA APP LOCK — Native Android प्रोजेक्ट

इस ZIP में Kotlin आधारित Native Android Studio प्रोजेक्ट है। यह WebView ऐप नहीं है।

मुख्य सुविधाएँ:
- 4 या 6 अंकों का PIN बनाना और बदलना (बदलने से पहले पुराना PIN आवश्यक)
- EncryptedSharedPreferences में PIN hash सुरक्षित रखना
- Fingerprint/strong biometric unlock और PIN fallback
- फोन में launcher apps की सूची, search और हर ऐप के लिए Lock ON/OFF
- Accessibility Service से foreground app पहचानकर Lock Screen दिखाना
- सही PIN/biometric के बाद 30 सेकंड का temporary unlock

APK बनाने के चरण:
1. Android Studio, Android SDK Platform 35, Build Tools और JDK 17 इंस्टॉल करें।
2. Android Studio में इस ZIP से निकला NarendraAppLock फ़ोल्डर Open करें।
3. Gradle Sync पूरा होने दें। Plugin: Android Gradle Plugin 8.6.1 और Kotlin 2.0.21।
4. Build > Build Bundle(s) / APK(s) > Build APK(s) चुनें।
5. APK यहाँ बनेगा: app/build/outputs/apk/debug/app-debug.apk

फोन में पहली बार सेटअप:
1. APK इंस्टॉल करें और ऐप खोलें।
2. 4 या 6 अंकों का PIN बनाएँ।
3. Select apps to lock में जिन ऐप्स को लॉक करना है, उन्हें ON करें।
4. Enable Accessibility service दबाएँ। Android Accessibility Settings में Narendra App Lock को ON करें और सिस्टम चेतावनी स्वीकार करें।
5. Fingerprint के लिए फोन में compatible fingerprint/biometric पहले से enroll होना चाहिए। PIN हमेशा fallback रहेगा।
6. पहले किसी सामान्य टेस्ट ऐप पर जाँच करें।

महत्वपूर्ण सीमाएँ:
- Android अनुमति के बिना Accessibility Service अपने आप चालू नहीं की जा सकती; उपयोगकर्ता को स्वयं ON करना होगा।
- Android Accessibility आधारित app lock best-effort सुरक्षा है, पूर्ण system-level सुरक्षा नहीं। Force stop, Safe Mode, ऐप uninstall, कुछ OEM battery restrictions या system screens से इसे bypass किया जा सकता है। बैंकिंग/पेमेंट ऐप की एकमात्र सुरक्षा के रूप में इस पर निर्भर न रहें।
- हर फोन पर background operation की गारंटी नहीं है। Android/OEM सेवा को बंद कर सकते हैं।
- यह संस्करण uninstall रोकने का दावा नहीं करता।
- Google Play पर प्रकाशित करने के लिए QUERY_ALL_PACKAGES अनुमति की Play policy समीक्षा लागू हो सकती है।
- Release APK को अपनी निजी release keystore से sign करें। Keystore और password सुरक्षित रखें।

डेवलपर: Narendra Maurya
