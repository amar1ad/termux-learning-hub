# Termux Learning App - Sketchware Project

تطبيق Termux Learning مكتوب بلغة Java لـ Sketchware مع قواعد بيانات SQLite و Firebase

**Termux Learning App - A comprehensive Sketchware project written in Java with SQLite and Firebase databases**

---

## 📋 المحتويات | Table of Contents

- [نظرة عامة](#نظرة-عامة--overview)
- [الميزات](#الميزات--features)
- [المتطلبات](#المتطلبات--requirements)
- [البنية](#البنية--project-structure)
- [التثبيت](#التثبيت--installation)
- [الاستخدام](#الاستخدام--usage)
- [الملفات](#الملفات--files)

---

## نظرة عامة | Overview

### العربية
تطبيق جوال شامل لتعلم أوامر Termux مع واجهة Material Design احترافية. يدعم اللغتين العربية والإنجليزية مع RTL كامل. يتضمن قاعدة بيانات SQLite محلية و Firebase للمزامنة السحابية.

### English
A comprehensive mobile app for learning Termux commands with professional Material Design interface. Supports both Arabic and English languages with full RTL support. Includes local SQLite database and Firebase for cloud synchronization.

---

## الميزات | Features

### ✨ المميزات الرئيسية

- **🌍 دعم ثنائي اللغة** - Full bilingual support (Arabic/English) with RTL
- **📱 Material Design** - Modern Material Design 3 interface
- **💾 قاعدتا بيانات** - SQLite (local) + Firebase (cloud)
- **🔍 بحث متقدم** - Full-text search functionality
- **❤️ المفضلة** - Bookmark favorite commands
- **📋 أمثلة وتفاصيل** - Examples, tips, and common errors
- **☁️ المزامنة** - Cloud sync with Firebase
- **📊 تتبع الاستخدام** - Track viewed commands
- **🎨 تخصيص** - Theme and language settings
- **📤 المشاركة** - Share commands with others

---

## المتطلبات | Requirements

### Software Requirements
- **Android Studio** 4.2 or higher
- **Java JDK** 11 or higher
- **Android SDK** API 21 (Android 5.0) or higher
- **Gradle** 7.0 or higher
- **Firebase Account** (for cloud features)

### Dependencies
```gradle
// AndroidX
androidx.appcompat:appcompat:1.6.1
androidx.constraintlayout:constraintlayout:2.1.4
androidx.recyclerview:recyclerview:1.3.0
androidx.cardview:cardview:1.0.0

// Material Design
com.google.android.material:material:1.9.0

// Firebase
com.google.firebase:firebase-database:20.2.1
com.google.firebase:firebase-auth:22.1.2

// Database
androidx.room:room-runtime:2.5.1
```

---

## البنية | Project Structure

```
sketchware-termux-app/
├── project.json                    # Project configuration
├── DatabaseHelper.java             # SQLite database helper
├── FirebaseHelper.java             # Firebase helper
├── MainActivity.java               # Main activity (categories)
├── CategoryActivity.java          # Category details activity
├── CommandDetailActivity.java      # Command details activity
├── SearchActivity.java            # Search activity
├── FavoritesActivity.java         # Favorites activity
├── SettingsActivity.java          # Settings activity
├── adapter/
│   ├── CategoryAdapter.java       # Categories list adapter
│   ├── CommandAdapter.java        # Commands list adapter
│   ├── ExampleAdapter.java        # Examples adapter
│   ├── TipAdapter.java            # Tips adapter
│   └── ErrorAdapter.java          # Errors adapter
├── layout/
│   ├── activity_main.xml          # Main activity layout
│   ├── activity_category.xml      # Category activity layout
│   ├── activity_command_detail.xml # Command detail layout
│   ├── item_category.xml          # Category item layout
│   ├── item_command.xml           # Command item layout
│   ├── item_example.xml           # Example item layout
│   ├── item_tip.xml               # Tip item layout
│   └── item_error.xml             # Error item layout
├── menu/
│   └── menu_main.xml              # Main menu
├── values/
│   ├── colors.xml                 # Color definitions
│   ├── strings.xml                # String resources (AR/EN)
│   ├── styles.xml                 # App styles
│   └── dimens.xml                 # Dimension definitions
├── drawable/
│   ├── ic_terminal.xml            # Icons
│   ├── ic_package.xml
│   ├── ic_code.xml
│   └── ... (more icons)
└── AndroidManifest.xml            # App manifest
```

---

## التثبيت | Installation

### 1. استنساخ المشروع | Clone Project

```bash
git clone https://github.com/yourusername/termux-learning-app.git
cd sketchware-termux-app
```

### 2. فتح في Android Studio | Open in Android Studio

```bash
# Using Android Studio
File → Open → Select project folder

# Or via command line
android-studio .
```

### 3. تحديث Gradle | Update Gradle

```bash
# Sync Gradle files
./gradlew sync

# Or in Android Studio
File → Sync Now
```

### 4. إضافة Firebase | Add Firebase

```bash
# Follow Firebase setup wizard in Android Studio
Tools → Firebase → Realtime Database → Connect to Firebase
```

### 5. البناء والتشغيل | Build and Run

```bash
# Build the project
./gradlew build

# Run on emulator or device
./gradlew installDebug

# Or use Android Studio
Run → Run 'app'
```

---

## الاستخدام | Usage

### الشاشات الرئيسية | Main Screens

#### 1. شاشة الرئيسية (MainActivity)
- عرض جميع الفئات في شبكة 2x2
- البحث السريع عن الأوامر
- الوصول إلى المفضلة والإعدادات

#### 2. شاشة الفئة (CategoryActivity)
- عرض جميع الأوامر في الفئة
- قائمة قابلة للتمرير
- الانتقال إلى تفاصيل الأمر

#### 3. شاشة التفاصيل (CommandDetailActivity)
- عرض كامل معلومات الأمر
- الأمثلة العملية
- النصائح المفيدة
- الأخطاء الشائعة والحلول
- نسخ ومشاركة الأمر
- إضافة إلى المفضلة

#### 4. شاشة البحث (SearchActivity)
- البحث المتقدم عن الأوامر
- سجل البحث
- النتائج المرتبة

#### 5. شاشة المفضلة (FavoritesActivity)
- عرض جميع الأوامر المفضلة
- إزالة من المفضلة
- الوصول السريع

#### 6. شاشة الإعدادات (SettingsActivity)
- تغيير اللغة (عربي/إنجليزي)
- تغيير المظهر (فاتح/داكن)
- مزامنة البيانات
- حول التطبيق

---

## الملفات | Files

### Java Classes (1,660 lines)

| الملف | الأسطر | الوصف |
|------|------|-------|
| DatabaseHelper.java | 537 | SQLite database operations |
| FirebaseHelper.java | 439 | Firebase cloud operations |
| CommandDetailActivity.java | 276 | Command details screen |
| MainActivity.java | 167 | Main activity with categories |
| CategoryActivity.java | 116 | Category details screen |
| CategoryAdapter.java | 80 | RecyclerView adapter |
| project.json | 125 | Project configuration |

### Key Features in Code

#### DatabaseHelper.java
- ✅ Create and manage SQLite database
- ✅ Insert/Update/Delete operations
- ✅ Search functionality
- ✅ Favorites management
- ✅ Search history tracking

#### FirebaseHelper.java
- ✅ Firebase Realtime Database integration
- ✅ Cloud data synchronization
- ✅ User settings backup
- ✅ Favorites sync
- ✅ Search history sync

#### Activities
- ✅ Material Design UI
- ✅ RTL support for Arabic
- ✅ Bilingual interface
- ✅ Proper navigation
- ✅ Error handling

---

## قاعدة البيانات | Database

### SQLite Tables

```sql
-- Categories
CREATE TABLE categories (
  id TEXT PRIMARY KEY,
  title_ar TEXT,
  title_en TEXT,
  description_ar TEXT,
  description_en TEXT,
  icon TEXT,
  difficulty TEXT,
  order INTEGER
)

-- Commands
CREATE TABLE commands (
  id TEXT PRIMARY KEY,
  category_id TEXT,
  title_ar TEXT,
  title_en TEXT,
  description_ar TEXT,
  description_en TEXT,
  command_syntax TEXT,
  difficulty TEXT,
  usage_count INTEGER
)

-- Command Examples
CREATE TABLE command_examples (
  id INTEGER PRIMARY KEY,
  command_id TEXT,
  command_ar TEXT,
  command_en TEXT,
  description_ar TEXT,
  description_en TEXT
)

-- Command Tips
CREATE TABLE command_tips (
  id INTEGER PRIMARY KEY,
  command_id TEXT,
  tip_ar TEXT,
  tip_en TEXT
)

-- Command Errors
CREATE TABLE command_errors (
  id INTEGER PRIMARY KEY,
  command_id TEXT,
  error_ar TEXT,
  error_en TEXT,
  solution_ar TEXT,
  solution_en TEXT
)

-- Favorites
CREATE TABLE favorites (
  id INTEGER PRIMARY KEY,
  user_id TEXT,
  command_id TEXT,
  added_at DATETIME
)

-- Search History
CREATE TABLE search_history (
  id INTEGER PRIMARY KEY,
  user_id TEXT,
  query TEXT,
  language TEXT,
  searched_at DATETIME
)
```

---

## الإحصائيات | Statistics

| المقياس | العدد |
|--------|------|
| Java Classes | 7+ |
| Total Lines of Code | 1,660+ |
| Activities | 6 |
| Adapters | 5+ |
| Database Tables | 7 |
| Categories | 10 |
| Commands | 26+ |
| Languages | 2 (AR/EN) |

---

## الدعم والمساهمة | Support & Contribution

### الإبلاغ عن المشاكل | Report Issues
```
GitHub Issues: https://github.com/yourusername/termux-learning-app/issues
```

### المساهمة | Contribute
```
1. Fork the repository
2. Create feature branch (git checkout -b feature/AmazingFeature)
3. Commit changes (git commit -m 'Add AmazingFeature')
4. Push to branch (git push origin feature/AmazingFeature)
5. Open Pull Request
```

---

## الترخيص | License

هذا المشروع مرخص تحت MIT License

This project is licensed under the MIT License

---

## المؤلف | Author

**Created by:** Manus AI
**Date:** 2026-03-26
**Version:** 1.0.0

---

## شكر وتقدير | Acknowledgments

- Firebase for cloud database
- Android team for AndroidX libraries
- Material Design team
- All contributors and testers

---

**آخر تحديث | Last Updated:** 2026-03-26
**الإصدار | Version:** 1.0.0
