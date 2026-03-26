# دليل التثبيت والإعداد | Setup & Installation Guide

**Termux Learning App - Sketchware Project**

---

## 📋 جدول المحتويات | Table of Contents

1. [المتطلبات](#المتطلبات--requirements)
2. [التثبيت](#التثبيت--installation)
3. [إعداد Firebase](#إعداد-firebase--firebase-setup)
4. [الاستيراد إلى Sketchware](#الاستيراد-إلى-sketchware--importing-to-sketchware)
5. [الاستيراد إلى Android Studio](#الاستيراد-إلى-android-studio--importing-to-android-studio)
6. [استكشاف الأخطاء](#استكشاف-الأخطاء--troubleshooting)

---

## المتطلبات | Requirements

### الحد الأدنى للمتطلبات | Minimum Requirements

| المتطلب | الإصدار | الملاحظات |
|--------|--------|---------|
| Android SDK | 21+ (Android 5.0) | الحد الأدنى |
| Java | 11 LTS | للتطوير |
| Gradle | 7.0+ | لبناء المشروع |
| Android Studio | 4.2+ | اختياري |
| Sketchware | آخر إصدار | لتحرير المشروع |

### البرامج المطلوبة | Required Software

```bash
# Windows / Mac / Linux
1. Android Studio (اختياري)
2. Java Development Kit (JDK 11+)
3. Sketchware App (من Google Play)
4. Git (اختياري)
```

### حسابات مطلوبة | Required Accounts

- **Firebase Account** - للمزامنة السحابية
- **Google Account** - لـ Firebase

---

## التثبيت | Installation

### الطريقة 1: استخدام Sketchware (الأسهل)

#### الخطوة 1: تحميل التطبيق
```bash
# من Google Play
1. افتح Google Play Store
2. ابحث عن "Sketchware"
3. اضغط تثبيت
```

#### الخطوة 2: استيراد المشروع
```
1. افتح Sketchware
2. اضغط "+"
3. اختر "استيراد"
4. اختر ملف project.json
5. اضغط "استيراد"
```

#### الخطوة 3: تحرير المشروع
```
1. افتح المشروع المستورد
2. أضف الملفات:
   - DatabaseHelper.java
   - FirebaseHelper.java
   - MainActivity.java
   - CategoryActivity.java
   - CommandDetailActivity.java
   - CategoryAdapter.java
3. أضف الموارد:
   - strings.xml
   - colors.xml
4. اضغط "بناء"
```

---

### الطريقة 2: استخدام Android Studio (الأفضل للمطورين)

#### الخطوة 1: تثبيت المتطلبات

**على Windows:**
```bash
# تحميل Java JDK 11
https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

# تحميل Android Studio
https://developer.android.com/studio

# تثبيت Git (اختياري)
https://git-scm.com/download/win
```

**على Mac:**
```bash
# استخدام Homebrew
brew install java11
brew install android-studio
brew install git
```

**على Linux:**
```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install openjdk-11-jdk
sudo apt-get install android-studio
sudo apt-get install git

# Fedora
sudo dnf install java-11-openjdk
sudo dnf install android-studio
```

#### الخطوة 2: استنساخ المشروع

```bash
# استنساخ من GitHub
git clone https://github.com/yourusername/termux-learning-app.git
cd sketchware-termux-app

# أو فك الضغط من الملف
unzip sketchware-termux-app.zip
cd sketchware-termux-app
```

#### الخطوة 3: فتح في Android Studio

```bash
# الطريقة 1: من سطر الأوامر
android-studio .

# الطريقة 2: من واجهة Android Studio
File → Open → اختر المجلد
```

#### الخطوة 4: مزامنة Gradle

```bash
# سيتم تلقائياً عند الفتح
# أو يدوياً:
File → Sync Now

# أو من سطر الأوامر
./gradlew sync
```

#### الخطوة 5: بناء المشروع

```bash
# من سطر الأوامر
./gradlew build

# أو من Android Studio
Build → Build Bundle(s) / APK(s)
```

#### الخطوة 6: تشغيل على جهاز أو محاكي

```bash
# من سطر الأوامر
./gradlew installDebug

# أو من Android Studio
Run → Run 'app'
```

---

## إعداد Firebase | Firebase Setup

### الخطوة 1: إنشاء مشروع Firebase

```
1. اذهب إلى https://console.firebase.google.com
2. اضغط "إنشاء مشروع"
3. أدخل اسم المشروع: "Termux Learning"
4. اتبع الخطوات
5. اضغط "إنشاء"
```

### الخطوة 2: إضافة تطبيق Android

```
1. في لوحة Firebase
2. اضغط "إضافة تطبيق"
3. اختر "Android"
4. أدخل معلومات التطبيق:
   - اسم الحزمة: com.termux.learning
   - الاسم المستعار: Termux Learning
5. اضغط "تسجيل التطبيق"
```

### الخطوة 3: تحميل ملف google-services.json

```
1. اضغط "تحميل google-services.json"
2. ضع الملف في: app/google-services.json
3. اضغط "التالي"
4. اتبع التعليمات
```

### الخطوة 4: إعداد Realtime Database

```
1. في Firebase Console
2. اذهب إلى "Realtime Database"
3. اضغط "إنشاء قاعدة بيانات"
4. اختر المنطقة (الأقرب إليك)
5. اختر "بدء في وضع الاختبار"
6. اضغط "تفعيل"
```

### الخطوة 5: قواعد الأمان

```json
{
  "rules": {
    "categories": {
      ".read": true,
      ".write": false
    },
    "commands": {
      ".read": true,
      ".write": false
    },
    "users": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}
```

---

## الاستيراد إلى Sketchware | Importing to Sketchware

### الطريقة الأولى: استيراد المشروع المباشر

```
1. افتح Sketchware
2. اضغط "+"
3. اختر "استيراد"
4. اختر project.json
5. اضغط "استيراد"
```

### الطريقة الثانية: استيراد الملفات يدويًا

```
1. أنشئ مشروع جديد
2. اذهب إلى "Java"
3. أضف الملفات:
   - DatabaseHelper.java
   - FirebaseHelper.java
   - MainActivity.java
   - CategoryActivity.java
   - CommandDetailActivity.java
   - CategoryAdapter.java
4. اذهب إلى "Resources"
5. أضف:
   - strings.xml
   - colors.xml
6. اضغط "بناء"
```

---

## الاستيراد إلى Android Studio | Importing to Android Studio

### الخطوة 1: فتح المشروع

```bash
# من سطر الأوامر
cd sketchware-termux-app
android-studio .

# أو من واجهة Android Studio
File → Open → اختر المجلد
```

### الخطوة 2: مزامنة Gradle

```
File → Sync Now
```

### الخطوة 3: إضافة ملفات Google Services

```
1. ضع google-services.json في: app/
2. مزامنة Gradle مرة أخرى
```

### الخطوة 4: بناء المشروع

```
Build → Build Bundle(s) / APK(s)
```

### الخطوة 5: التشغيل

```
Run → Run 'app'
```

---

## استكشاف الأخطاء | Troubleshooting

### المشكلة 1: خطأ "Cannot resolve symbol"

**الحل:**
```
1. File → Invalidate Caches / Restart
2. اختر "Invalidate and Restart"
3. انتظر إعادة التشغيل
4. File → Sync Now
```

### المشكلة 2: خطأ Gradle Build

**الحل:**
```bash
# حذف ملفات Gradle
rm -rf .gradle
rm -rf build

# إعادة المزامنة
./gradlew clean
./gradlew sync
```

### المشكلة 3: خطأ Firebase

**الحل:**
```
1. تأكد من google-services.json موجود
2. تأكد من اسم الحزمة صحيح
3. أعد تحميل ملف google-services.json
4. File → Sync Now
```

### المشكلة 4: خطأ في المحاكي

**الحل:**
```
1. أغلق المحاكي
2. AVD Manager → اختر المحاكي → Delete
3. أنشئ محاكي جديد
4. Run → Run 'app'
```

### المشكلة 5: خطأ الأذونات

**الحل:**
```
1. تأكد من الأذونات في AndroidManifest.xml
2. على Android 6+، اطلب الأذونات في الكود
3. اختبر على جهاز فعلي
```

---

## الخطوات التالية | Next Steps

### بعد التثبيت الناجح

```
1. ✅ اختبر التطبيق على محاكي أو جهاز
2. ✅ تحقق من قاعدة البيانات
3. ✅ اختبر جميع الشاشات
4. ✅ اختبر البحث والمفضلة
5. ✅ اختبر المزامنة مع Firebase
6. ✅ اختبر اللغات (عربي/إنجليزي)
```

### البناء للإصدار

```bash
# بناء APK للإصدار
./gradlew assembleRelease

# بناء Bundle للـ Google Play
./gradlew bundleRelease

# التوقيع على APK
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore my-release-key.jks app-release-unsigned.apk alias_name
```

---

## الدعم | Support

### المشاكل الشائعة

- **خطأ في البناء:** تحقق من إصدار Gradle و Java
- **خطأ في Firebase:** تحقق من google-services.json
- **خطأ في المحاكي:** أعد تشغيل المحاكي
- **خطأ في الأذونات:** تحقق من AndroidManifest.xml

### الموارد المفيدة

- [Android Developer Docs](https://developer.android.com/docs)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Sketchware Community](https://sketchware.io)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/android)

---

**آخر تحديث | Last Updated:** 2026-03-26
**الإصدار | Version:** 1.0.0
