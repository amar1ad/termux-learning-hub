# قاعدة بيانات Termux - Termux Database

قاعدة بيانات شاملة وكاملة لأوامر Termux بدعم كامل للعربية والإنجليزية

**Comprehensive and complete database for Termux commands with full Arabic and English support**

---

## 📋 المحتويات | Table of Contents

- [نظرة عامة](#نظرة-عامة--overview)
- [الميزات](#الميزات--features)
- [هيكل البيانات](#هيكل-البيانات--database-structure)
- [التثبيت](#التثبيت--installation)
- [الاستخدام](#الاستخدام--usage)
- [الاستعلامات](#الاستعلامات--queries)
- [الإحصائيات](#الإحصائيات--statistics)

---

## نظرة عامة | Overview

### العربية
قاعدة بيانات متقدمة تحتوي على أوامر Termux الشاملة مع دعم كامل للغة العربية والإنجليزية. تتضمن البيانات الأمثلة والنصائح والأخطاء الشائعة والحلول لكل أمر.

### English
An advanced database containing comprehensive Termux commands with full support for Arabic and English languages. The data includes examples, tips, common errors, and solutions for each command.

---

## الميزات | Features

### ✨ المميزات الرئيسية

- **🌍 دعم ثنائي اللغة** - Full bilingual support (Arabic/English)
- **📚 10 فئات رئيسية** - 10 main categories
- **⚙️ 26+ أمر** - 26+ commands with details
- **📖 أمثلة عملية** - Practical examples for each command
- **💡 نصائح مفيدة** - Helpful tips and tricks
- **🐛 حل المشاكل** - Common errors and solutions
- **📊 تتبع الاستخدام** - Usage tracking and analytics
- **❤️ المفضلة** - Bookmarking system
- **🔍 البحث المتقدم** - Full-text search support
- **📱 متوافق مع التطبيقات** - API-ready structure

---

## هيكل البيانات | Database Structure

### الجداول الرئيسية | Main Tables

#### 1. **categories** - الفئات
جدول يحتوي على فئات أوامر Termux الرئيسية

```sql
- id: معرف الفئة الفريد
- title_ar: عنوان الفئة بالعربية
- title_en: عنوان الفئة بالإنجليزية
- description_ar: وصف الفئة بالعربية
- description_en: وصف الفئة بالإنجليزية
- icon: اسم الأيقونة
- difficulty: مستوى الصعوبة (beginner/intermediate/advanced)
- order: ترتيب العرض
```

#### 2. **commands** - الأوامر
جدول يحتوي على أوامر Termux مع التفاصيل الكاملة

```sql
- id: معرف الأمر الفريد
- category_id: معرف الفئة (مفتاح أجنبي)
- title_ar: عنوان الأمر بالعربية
- title_en: عنوان الأمر بالإنجليزية
- description_ar: وصف الأمر بالعربية
- description_en: وصف الأمر بالإنجليزية
- command_syntax: صيغة الأمر
- difficulty: مستوى الصعوبة
- usage_count: عدد مرات العرض
```

#### 3. **command_examples** - أمثلة الأوامر
جدول يحتوي على أمثلة عملية لكل أمر

```sql
- id: معرف المثال الفريد
- command_id: معرف الأمر (مفتاح أجنبي)
- command_ar: الأمر في السياق العربي
- command_en: الأمر في السياق الإنجليزي
- description_ar: وصف المثال بالعربية
- description_en: وصف المثال بالإنجليزية
- order: ترتيب العرض
```

#### 4. **command_tips** - النصائح
جدول يحتوي على نصائح مفيدة لكل أمر

```sql
- id: معرف النصيحة الفريد
- command_id: معرف الأمر (مفتاح أجنبي)
- tip_ar: النصيحة بالعربية
- tip_en: النصيحة بالإنجليزية
- order: ترتيب العرض
```

#### 5. **command_errors** - الأخطاء الشائعة
جدول يحتوي على الأخطاء الشائعة والحلول

```sql
- id: معرف الخطأ الفريد
- command_id: معرف الأمر (مفتاح أجنبي)
- error_ar: رسالة الخطأ بالعربية
- error_en: رسالة الخطأ بالإنجليزية
- solution_ar: الحل بالعربية
- solution_en: الحل بالإنجليزية
- order: ترتيب العرض
```

#### 6. **user_progress** - تتبع التقدم
جدول يتتبع تقدم المستخدم في التعلم

```sql
- id: معرف السجل الفريد
- user_id: معرف المستخدم
- command_id: معرف الأمر (مفتاح أجنبي)
- viewed_at: وقت العرض
- is_bookmarked: هل تم حفظه كمفضل
- notes: ملاحظات المستخدم
```

#### 7. **favorites** - المفضلة
جدول يحتوي على الأوامر المفضلة للمستخدم

```sql
- id: معرف السجل الفريد
- user_id: معرف المستخدم
- command_id: معرف الأمر (مفتاح أجنبي)
- added_at: وقت الإضافة
```

#### 8. **search_history** - سجل البحث
جدول يتتبع عمليات البحث

```sql
- id: معرف السجل الفريد
- user_id: معرف المستخدم
- query: نص البحث
- language: لغة البحث
- results_count: عدد النتائج
- searched_at: وقت البحث
```

---

## التثبيت | Installation

### المتطلبات | Requirements

- MySQL 5.7+ أو MariaDB 10.3+
- Python 3.6+
- mysql-connector-python

### خطوات التثبيت | Installation Steps

#### 1. إنشاء قاعدة البيانات | Create Database

```bash
# استخدام MySQL CLI
mysql -u root -p < termux-database.sql

# أو استخدام Python
python3 import_json_to_sql.py
```

#### 2. تثبيت المكتبات | Install Dependencies

```bash
pip install mysql-connector-python
```

#### 3. تحديث إعدادات الاتصال | Update Connection Settings

عدّل ملف `import_json_to_sql.py` أو `export_sql_to_json.py`:

```python
DB_CONFIG = {
    'host': 'localhost',
    'user': 'your_username',
    'password': 'your_password',
    'database': 'termux_learning'
}
```

---

## الاستخدام | Usage

### استيراد البيانات من JSON | Import from JSON

```bash
python3 import_json_to_sql.py termux-commands.json
```

**الإخراج | Output:**
```
✓ JSON file loaded: termux-commands.json
✓ Database connection established
✓ Imported 10 categories
✓ Imported 26 commands
✓ Imported 20+ command examples
✓ Imported 8+ command tips
✓ Imported 3+ common errors
✓ Import completed successfully!
```

### تصدير البيانات إلى JSON | Export to JSON

```bash
python3 export_sql_to_json.py termux-commands-export.json
```

**الإخراج | Output:**
```
✓ Database connection established
✓ Exported 10 categories
✓ Exported 26 commands

Export Summary:
  - Categories: 10
  - Commands: 26
  - Examples: 20+
  - Tips: 8+
  - Errors: 3+

✓ Export completed successfully!
```

---

## الاستعلامات | Queries

### الاستعلامات الشائعة | Common Queries

#### 1. الحصول على جميع الأوامر في فئة معينة
```sql
SELECT * FROM commands 
WHERE category_id = 'linux'
ORDER BY id;
```

#### 2. البحث عن أمر معين
```sql
SELECT * FROM v_commands_with_categories
WHERE title_ar LIKE '%ملف%' OR title_en LIKE '%file%';
```

#### 3. الحصول على أمثلة أمر معين
```sql
SELECT ce.*, c.title_ar, c.title_en
FROM command_examples ce
JOIN commands c ON ce.command_id = c.id
WHERE ce.command_id = 'linux-1'
ORDER BY ce.order;
```

#### 4. الحصول على نصائح أمر معين
```sql
SELECT ct.*, c.title_ar, c.title_en
FROM command_tips ct
JOIN commands c ON ct.command_id = c.id
WHERE ct.command_id = 'linux-1'
ORDER BY ct.order;
```

#### 5. الحصول على الأخطاء الشائعة
```sql
SELECT ce.*, c.title_ar, c.title_en
FROM command_errors ce
JOIN commands c ON ce.command_id = c.id
WHERE ce.command_id = 'linux-1'
ORDER BY ce.order;
```

#### 6. الحصول على إحصائيات الأوامر
```sql
SELECT * FROM v_commands_with_stats
WHERE difficulty = 'beginner'
ORDER BY example_count DESC;
```

#### 7. البحث بالكلمات المفتاحية (Full-Text Search)
```sql
SELECT * FROM commands
WHERE MATCH(title_ar, description_ar) AGAINST('ملف' IN BOOLEAN MODE)
OR MATCH(title_en, description_en) AGAINST('file' IN BOOLEAN MODE);
```

#### 8. الحصول على الأوامر الأكثر استخداماً
```sql
SELECT * FROM commands
ORDER BY usage_count DESC
LIMIT 10;
```

#### 9. الحصول على المفضلة للمستخدم
```sql
SELECT c.*, f.added_at
FROM favorites f
JOIN commands c ON f.command_id = c.id
WHERE f.user_id = 'user123'
ORDER BY f.added_at DESC;
```

#### 10. تحديث عدد مرات العرض
```sql
UPDATE commands
SET usage_count = usage_count + 1
WHERE id = 'linux-1';
```

---

## الإحصائيات | Statistics

### إحصائيات قاعدة البيانات | Database Statistics

| المقياس | العدد | Metric | Count |
|--------|------|--------|-------|
| الفئات | 10 | Categories | 10 |
| الأوامر | 26+ | Commands | 26+ |
| الأمثلة | 20+ | Examples | 20+ |
| النصائح | 8+ | Tips | 8+ |
| الأخطاء | 3+ | Common Errors | 3+ |
| اللغات | 2 | Languages | 2 |

### توزيع الأوامر حسب الصعوبة | Commands by Difficulty

| المستوى | العدد | Difficulty | Count |
|--------|------|------------|-------|
| مبتدئ | 12 | Beginner | 12 |
| متوسط | 7 | Intermediate | 7 |
| متقدم | 7 | Advanced | 7 |

### توزيع الأوامر حسب الفئة | Commands by Category

| الفئة | العدد | Category | Count |
|------|------|----------|-------|
| Linux الأساسي | 6 | Basic Linux | 6 |
| إدارة الحزم | 3 | Package Management | 3 |
| البرمجة | 2 | Programming | 2 |
| الشبكات | 2 | Networking | 2 |
| Termux API | 2 | Termux API | 2 |
| الخوادم | 2 | Servers | 2 |
| الواجهة الرسومية | 2 | GUI | 2 |
| التخصيص | 2 | Customization | 2 |
| Docker | 2 | Docker | 2 |
| المراقبة | 2 | Monitoring | 2 |

---

## ملفات قاعدة البيانات | Database Files

### 📁 الملفات المتضمنة | Included Files

```
termux-database/
├── termux-commands.json          # ملف JSON الشامل
├── termux-database.sql           # ملف SQL لإنشاء قاعدة البيانات
├── import_json_to_sql.py         # سكريبت استيراد JSON إلى SQL
├── export_sql_to_json.py         # سكريبت تصدير SQL إلى JSON
├── README.md                     # هذا الملف
└── QUERIES.md                    # استعلامات SQL متقدمة
```

---

## الترخيص | License

هذه قاعدة البيانات متاحة للاستخدام الحر والتطوير

This database is available for free use and development

---

## الدعم | Support

للمزيد من المعلومات أو الإبلاغ عن مشاكل، يرجى التواصل عبر:

For more information or to report issues, please contact:

- 📧 Email: support@termux-learning.com
- 🐛 Issues: GitHub Issues
- 💬 Discussions: GitHub Discussions

---

## التحديثات | Updates

آخر تحديث: 2026-03-26
Last Updated: 2026-03-26

الإصدار: 1.0.0
Version: 1.0.0

---

**تم إنشاؤها بواسطة | Created by:** Manus AI
**التاريخ | Date:** 2026-03-26
