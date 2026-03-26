# استعلامات SQL متقدمة | Advanced SQL Queries

وثيقة شاملة لاستعلامات SQL المتقدمة لقاعدة بيانات Termux

---

## 📚 جدول المحتويات | Table of Contents

1. [استعلامات البحث](#استعلامات-البحث--search-queries)
2. [استعلامات التحليل](#استعلامات-التحليل--analytics-queries)
3. [استعلامات التحديث](#استعلامات-التحديث--update-queries)
4. [استعلامات الإحصائيات](#استعلامات-الإحصائيات--statistics-queries)
5. [الآراء المخزنة](#الآراء-المخزنة--stored-views)

---

## استعلامات البحث | Search Queries

### 1. البحث الشامل عن أمر
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  c.command_syntax,
  cat.title_ar as category_ar,
  cat.title_en as category_en,
  c.difficulty
FROM commands c
JOIN categories cat ON c.category_id = cat.id
WHERE 
  c.title_ar LIKE CONCAT('%', ?, '%') OR
  c.title_en LIKE CONCAT('%', ?, '%') OR
  c.description_ar LIKE CONCAT('%', ?, '%') OR
  c.description_en LIKE CONCAT('%', ?, '%') OR
  c.command_syntax LIKE CONCAT('%', ?, '%')
ORDER BY c.id;
```

### 2. البحث بالكلمات المفتاحية (Full-Text Search)
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  c.command_syntax,
  MATCH(c.title_ar, c.description_ar) AGAINST(? IN BOOLEAN MODE) as relevance_ar,
  MATCH(c.title_en, c.description_en) AGAINST(? IN BOOLEAN MODE) as relevance_en
FROM commands c
WHERE 
  MATCH(c.title_ar, c.description_ar) AGAINST(? IN BOOLEAN MODE) OR
  MATCH(c.title_en, c.description_en) AGAINST(? IN BOOLEAN MODE)
ORDER BY 
  CASE 
    WHEN MATCH(c.title_ar) AGAINST(? IN BOOLEAN MODE) THEN 1
    WHEN MATCH(c.description_ar) AGAINST(? IN BOOLEAN MODE) THEN 2
    ELSE 3
  END;
```

### 3. البحث حسب الفئة والصعوبة
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  c.command_syntax,
  c.difficulty,
  COUNT(DISTINCT ce.id) as example_count,
  COUNT(DISTINCT ct.id) as tip_count
FROM commands c
LEFT JOIN command_examples ce ON c.id = ce.command_id
LEFT JOIN command_tips ct ON c.id = ct.command_id
WHERE 
  c.category_id = ? AND
  c.difficulty = ?
GROUP BY c.id
ORDER BY c.id;
```

### 4. البحث عن الأوامر المشابهة
```sql
SELECT 
  c1.id,
  c1.title_ar,
  c1.title_en,
  c1.command_syntax,
  c2.id as similar_id,
  c2.title_ar as similar_title_ar,
  c2.title_en as similar_title_en
FROM commands c1
JOIN commands c2 ON 
  c1.category_id = c2.category_id AND
  c1.id != c2.id AND
  (
    c1.difficulty = c2.difficulty OR
    LEVENSHTEIN(c1.title_ar, c2.title_ar) < 5
  )
WHERE c1.id = ?
LIMIT 5;
```

---

## استعلامات التحليل | Analytics Queries

### 1. الأوامر الأكثر استخداماً
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  c.usage_count,
  cat.title_ar as category_ar,
  c.difficulty,
  RANK() OVER (ORDER BY c.usage_count DESC) as rank
FROM commands c
JOIN categories cat ON c.category_id = cat.id
WHERE c.usage_count > 0
ORDER BY c.usage_count DESC
LIMIT 20;
```

### 2. تحليل سلوك المستخدم
```sql
SELECT 
  up.user_id,
  COUNT(DISTINCT up.command_id) as viewed_commands,
  COUNT(DISTINCT CASE WHEN up.is_bookmarked THEN up.command_id END) as bookmarked_commands,
  MAX(up.viewed_at) as last_viewed,
  MIN(up.viewed_at) as first_viewed,
  DATEDIFF(MAX(up.viewed_at), MIN(up.viewed_at)) as days_active
FROM user_progress up
GROUP BY up.user_id
ORDER BY viewed_commands DESC;
```

### 3. تحليل المفضلة
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  COUNT(DISTINCT f.user_id) as favorite_count,
  COUNT(DISTINCT f.user_id) * 100.0 / (SELECT COUNT(DISTINCT user_id) FROM favorites) as favorite_percentage
FROM commands c
LEFT JOIN favorites f ON c.id = f.command_id
GROUP BY c.id
HAVING favorite_count > 0
ORDER BY favorite_count DESC;
```

### 4. تحليل سجل البحث
```sql
SELECT 
  sh.query,
  sh.language,
  COUNT(*) as search_count,
  AVG(sh.results_count) as avg_results,
  MAX(sh.searched_at) as last_searched
FROM search_history sh
GROUP BY sh.query, sh.language
HAVING search_count > 1
ORDER BY search_count DESC;
```

### 5. تحليل التعلم حسب المستوى
```sql
SELECT 
  c.difficulty,
  COUNT(DISTINCT c.id) as command_count,
  COUNT(DISTINCT up.user_id) as users_viewed,
  AVG(c.usage_count) as avg_usage,
  SUM(c.usage_count) as total_usage
FROM commands c
LEFT JOIN user_progress up ON c.id = up.command_id
GROUP BY c.difficulty
ORDER BY 
  CASE c.difficulty
    WHEN 'beginner' THEN 1
    WHEN 'intermediate' THEN 2
    WHEN 'advanced' THEN 3
  END;
```

---

## استعلامات التحديث | Update Queries

### 1. تحديث عدد المرات المشاهدة
```sql
UPDATE commands
SET usage_count = usage_count + 1
WHERE id = ?;
```

### 2. إضافة أمر إلى المفضلة
```sql
INSERT INTO favorites (user_id, command_id, added_at)
VALUES (?, ?, NOW())
ON DUPLICATE KEY UPDATE added_at = NOW();
```

### 3. إزالة أمر من المفضلة
```sql
DELETE FROM favorites
WHERE user_id = ? AND command_id = ?;
```

### 4. تحديث ملاحظات المستخدم
```sql
UPDATE user_progress
SET notes = ?
WHERE user_id = ? AND command_id = ?;
```

### 5. تحديث حالة الحفظ
```sql
UPDATE user_progress
SET is_bookmarked = NOT is_bookmarked
WHERE user_id = ? AND command_id = ?;
```

### 6. حذف سجل البحث القديم
```sql
DELETE FROM search_history
WHERE searched_at < DATE_SUB(NOW(), INTERVAL 90 DAY);
```

### 7. تحديث جميع الأوامر في فئة
```sql
UPDATE commands
SET difficulty = ?
WHERE category_id = ?;
```

---

## استعلامات الإحصائيات | Statistics Queries

### 1. إحصائيات عامة
```sql
SELECT 
  (SELECT COUNT(*) FROM categories) as total_categories,
  (SELECT COUNT(*) FROM commands) as total_commands,
  (SELECT COUNT(*) FROM command_examples) as total_examples,
  (SELECT COUNT(*) FROM command_tips) as total_tips,
  (SELECT COUNT(*) FROM command_errors) as total_errors,
  (SELECT COUNT(DISTINCT user_id) FROM user_progress) as total_users,
  (SELECT SUM(usage_count) FROM commands) as total_views;
```

### 2. إحصائيات الفئات
```sql
SELECT 
  cat.id,
  cat.title_ar,
  cat.title_en,
  COUNT(DISTINCT c.id) as command_count,
  COUNT(DISTINCT ce.id) as example_count,
  COUNT(DISTINCT ct.id) as tip_count,
  SUM(c.usage_count) as total_usage
FROM categories cat
LEFT JOIN commands c ON cat.id = c.category_id
LEFT JOIN command_examples ce ON c.id = ce.command_id
LEFT JOIN command_tips ct ON c.id = ct.command_id
GROUP BY cat.id
ORDER BY cat.order;
```

### 3. توزيع الأوامر حسب الصعوبة
```sql
SELECT 
  difficulty,
  COUNT(*) as command_count,
  ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM commands), 2) as percentage,
  AVG(usage_count) as avg_usage,
  MAX(usage_count) as max_usage,
  MIN(usage_count) as min_usage
FROM commands
GROUP BY difficulty
ORDER BY 
  CASE difficulty
    WHEN 'beginner' THEN 1
    WHEN 'intermediate' THEN 2
    WHEN 'advanced' THEN 3
  END;
```

### 4. إحصائيات الأمثلة والنصائح
```sql
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  (SELECT COUNT(*) FROM command_examples WHERE command_id = c.id) as example_count,
  (SELECT COUNT(*) FROM command_tips WHERE command_id = c.id) as tip_count,
  (SELECT COUNT(*) FROM command_errors WHERE command_id = c.id) as error_count,
  c.usage_count
FROM commands c
ORDER BY example_count DESC, tip_count DESC;
```

### 5. إحصائيات المستخدمين
```sql
SELECT 
  COUNT(DISTINCT user_id) as total_users,
  AVG(commands_viewed) as avg_commands_viewed,
  MAX(commands_viewed) as max_commands_viewed,
  MIN(commands_viewed) as min_commands_viewed,
  COUNT(DISTINCT CASE WHEN is_bookmarked THEN user_id END) as users_with_bookmarks
FROM (
  SELECT 
    user_id,
    COUNT(DISTINCT command_id) as commands_viewed,
    MAX(is_bookmarked) as is_bookmarked
  FROM user_progress
  GROUP BY user_id
) as user_stats;
```

---

## الآراء المخزنة | Stored Views

### 1. عرض الأوامر مع معلومات الفئة
```sql
CREATE OR REPLACE VIEW v_commands_with_categories AS
SELECT 
  c.id,
  c.category_id,
  cat.title_ar as category_title_ar,
  cat.title_en as category_title_en,
  c.title_ar,
  c.title_en,
  c.description_ar,
  c.description_en,
  c.command_syntax,
  c.difficulty,
  c.usage_count,
  c.created_at,
  c.updated_at
FROM commands c
JOIN categories cat ON c.category_id = cat.id
ORDER BY cat.order, c.id;
```

### 2. عرض الأوامر مع الإحصائيات
```sql
CREATE OR REPLACE VIEW v_commands_with_stats AS
SELECT 
  c.id,
  c.title_ar,
  c.title_en,
  c.command_syntax,
  c.difficulty,
  COUNT(DISTINCT ce.id) as example_count,
  COUNT(DISTINCT ct.id) as tip_count,
  COUNT(DISTINCT err.id) as error_count,
  c.usage_count,
  ROUND(c.usage_count / (SELECT AVG(usage_count) FROM commands), 2) as usage_ratio
FROM commands c
LEFT JOIN command_examples ce ON c.id = ce.command_id
LEFT JOIN command_tips ct ON c.id = ct.command_id
LEFT JOIN command_errors err ON c.id = err.command_id
GROUP BY c.id;
```

### 3. عرض الأوامر الشاملة
```sql
CREATE OR REPLACE VIEW v_commands_complete AS
SELECT 
  c.id,
  c.category_id,
  cat.title_ar as category_title_ar,
  cat.title_en as category_title_en,
  c.title_ar,
  c.title_en,
  c.description_ar,
  c.description_en,
  c.command_syntax,
  c.difficulty,
  c.usage_count,
  GROUP_CONCAT(DISTINCT ce.command_en SEPARATOR ', ') as examples,
  GROUP_CONCAT(DISTINCT ct.tip_en SEPARATOR '; ') as tips
FROM commands c
JOIN categories cat ON c.category_id = cat.id
LEFT JOIN command_examples ce ON c.id = ce.command_id
LEFT JOIN command_tips ct ON c.id = ct.command_id
GROUP BY c.id;
```

---

## نصائح الأداء | Performance Tips

### 1. استخدم الفهارس
```sql
-- تم إنشاء الفهارس التالية تلقائياً:
CREATE INDEX idx_commands_category ON commands(category_id);
CREATE INDEX idx_commands_difficulty ON commands(difficulty);
CREATE INDEX idx_examples_command ON command_examples(command_id);
CREATE INDEX idx_tips_command ON command_tips(command_id);
CREATE INDEX idx_errors_command ON command_errors(command_id);
```

### 2. استخدم EXPLAIN لتحليل الاستعلامات
```sql
EXPLAIN SELECT * FROM commands WHERE category_id = 'linux';
```

### 3. استخدم LIMIT للاستعلامات الكبيرة
```sql
SELECT * FROM commands LIMIT 100 OFFSET 0;
```

### 4. تجنب الاستعلامات المتكررة
```sql
-- استخدم JOIN بدلاً من الاستعلامات الفرعية
SELECT c.*, cat.title_ar
FROM commands c
JOIN categories cat ON c.category_id = cat.id;
```

---

## الأخطاء الشائعة وحلولها | Common Errors and Solutions

### 1. خطأ: "Unknown column in on clause"
**السبب:** استخدام اسم عمود غير صحيح
**الحل:** تحقق من أسماء الأعمدة والجداول

### 2. خطأ: "Syntax error near 'WHERE'"
**السبب:** عدم وجود فاصلة قبل WHERE
**الحل:** تأكد من الفواصل بين الأعمدة

### 3. خطأ: "No matching records found"
**السبب:** البيانات غير موجودة
**الحل:** تحقق من شروط البحث

### 4. خطأ: "Deadlock found"
**السبب:** تضارب في الأقفال
**الحل:** استخدم LOCK TABLES بحذر

---

## الترخيص | License

هذه الاستعلامات متاحة للاستخدام الحر

---

**آخر تحديث | Last Updated:** 2026-03-26
**الإصدار | Version:** 1.0.0
