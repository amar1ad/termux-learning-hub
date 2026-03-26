# Deployment Guide | دليل النشر

**Termux Learning Web App - GitHub Pages Deployment**

---

## 📋 Prerequisites | المتطلبات

- ✅ حساب GitHub
- ✅ Git مثبت على جهازك
- ✅ Node.js و npm
- ✅ المشروع جاهز للنشر

---

## 🚀 Step-by-Step Deployment | خطوات النشر

### الخطوة 1: إنشاء Repository على GitHub

```bash
# 1. اذهب إلى https://github.com/new
# 2. أدخل اسم المشروع: termux-learning-web
# 3. اختر "Public"
# 4. اضغط "Create repository"
```

### الخطوة 2: تهيئة Git في المشروع

```bash
cd /home/ubuntu/termux-learning-web

# تهيئة git
git init

# إضافة الملفات
git add .

# Commit الأول
git commit -m "Initial commit: Termux Learning Web App"

# إضافة remote
git remote add origin https://github.com/yourusername/termux-learning-web.git

# Push إلى GitHub
git branch -M main
git push -u origin main
```

### الخطوة 3: تحديث package.json

تأكد من أن `package.json` يحتوي على:

```json
{
  "homepage": "https://yourusername.github.io/termux-learning-web",
  "scripts": {
    "predeploy": "npm run build",
    "deploy": "gh-pages -d build"
  }
}
```

استبدل `yourusername` باسم المستخدم الفعلي على GitHub.

### الخطوة 4: تثبيت gh-pages

```bash
npm install --save-dev gh-pages
```

### الخطوة 5: بناء والنشر

```bash
# بناء المشروع
npm run build

# نشر على GitHub Pages
npm run deploy
```

### الخطوة 6: تفعيل GitHub Pages

```
1. اذهب إلى GitHub Repository
2. اضغط على "Settings"
3. اختر "Pages" من القائمة اليسرى
4. تحت "Source"، اختر "gh-pages" branch
5. اضغط "Save"
```

---

## ✅ التحقق من النشر

بعد النشر، التطبيق سيكون متاحاً على:
```
https://yourusername.github.io/termux-learning-web
```

---

## 🔄 التحديثات المستقبلية

لتحديث التطبيق:

```bash
# 1. عمل التغييرات المطلوبة
# 2. Commit التغييرات
git add .
git commit -m "Update: description of changes"

# 3. Push إلى GitHub
git push origin main

# 4. بناء والنشر
npm run deploy
```

---

## 🔧 Troubleshooting | استكشاف الأخطاء

### المشكلة: "fatal: not a git repository"
```bash
git init
git remote add origin https://github.com/yourusername/termux-learning-web.git
```

### المشكلة: "gh-pages is not recognized"
```bash
npm install --save-dev gh-pages
npm run deploy
```

### المشكلة: الموقع لا يعمل بعد النشر
1. تحقق من اسم المستخدم في `homepage`
2. تأكد من أن `gh-pages` branch موجود
3. انتظر 5 دقائق لتحديث الموقع

### المشكلة: الموقع يعرض 404
```bash
# تأكد من أن build folder موجود
npm run build

# أعد النشر
npm run deploy
```

---

## 📊 Deployment Checklist | قائمة التحقق

- [ ] تم إنشاء GitHub Repository
- [ ] تم تهيئة Git في المشروع
- [ ] تم تحديث `homepage` في package.json
- [ ] تم تثبيت `gh-pages`
- [ ] تم بناء المشروع بنجاح
- [ ] تم النشر على GitHub Pages
- [ ] تم تفعيل GitHub Pages في الإعدادات
- [ ] الموقع متاح على الرابط المتوقع
- [ ] جميع الميزات تعمل بشكل صحيح

---

## 🌐 Alternative Deployment Options | خيارات النشر الأخرى

### Vercel (الأسرع)

```bash
# 1. تثبيت Vercel CLI
npm i -g vercel

# 2. النشر
vercel
```

### Netlify

```bash
# 1. بناء المشروع
npm run build

# 2. رفع build folder على Netlify
# أو استخدام Netlify CLI
npm i -g netlify-cli
netlify deploy --prod --dir=build
```

### Firebase Hosting

```bash
# 1. تثبيت Firebase CLI
npm i -g firebase-tools

# 2. تسجيل الدخول
firebase login

# 3. تهيئة المشروع
firebase init hosting

# 4. النشر
firebase deploy
```

---

## 📈 Performance Tips | نصائح الأداء

1. **تفعيل Gzip Compression**
   - GitHub Pages يفعلها تلقائياً

2. **استخدام CDN**
   - GitHub Pages يستخدم CDN عالمي

3. **تحسين الصور**
   - استخدم صور بحجم مناسب

4. **تقليل حجم Bundle**
   ```bash
   npm run build
   # يجب أن يكون أقل من 100KB
   ```

---

## 🔒 Security | الأمان

1. **لا تضع بيانات حساسة في الكود**
2. **استخدم environment variables للـ API Keys**
3. **فعّل HTTPS** (GitHub Pages يفعلها تلقائياً)
4. **راجع .gitignore** قبل النشر

---

## 📞 Support | الدعم

للمساعدة:
- 📖 [GitHub Pages Docs](https://pages.github.com)
- 🐛 [GitHub Issues](https://github.com/yourusername/termux-learning-web/issues)
- 💬 [Stack Overflow](https://stackoverflow.com/questions/tagged/github-pages)

---

**تم الإنشاء بواسطة Manus AI**
**آخر تحديث: 2026-03-26**
