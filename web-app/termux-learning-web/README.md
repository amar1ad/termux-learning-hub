# Termux Learning Web App

تطبيق ويب تفاعلي لتعلم أوامر Termux مع دعم كامل للعربية والإنجليزية.

**Comprehensive web application for learning Termux commands with full Arabic and English support.**

---

## 🌟 Features | الميزات

- ✅ **10 Categories** - 10 فئات من أوامر Termux
- ✅ **26+ Commands** - 26+ أمر مع أمثلة وشروحات
- ✅ **Bilingual Support** - دعم العربية والإنجليزية مع RTL
- ✅ **Material Design** - تصميم احترافي حديث
- ✅ **Search & Filter** - بحث متقدم عن الأوامر
- ✅ **Favorites** - حفظ الأوامر المفضلة محلياً
- ✅ **Copy & Share** - نسخ ومشاركة الأوامر
- ✅ **Responsive Design** - تصميم متجاوب على جميع الأجهزة
- ✅ **Dark Theme** - موضوع داكن بألوان Termux الأصلية
- ✅ **PWA Ready** - تطبيق ويب تقدمي

---

## 🚀 Getting Started | البدء السريع

### Prerequisites | المتطلبات

- Node.js 14+ و npm
- Git (اختياري)

### Installation | التثبيت

```bash
# Clone the repository
git clone https://github.com/yourusername/termux-learning-web.git
cd termux-learning-web

# Install dependencies
npm install

# Start development server
npm start
```

التطبيق سيفتح تلقائياً على `http://localhost:3000`

---

## 📁 Project Structure | هيكل المشروع

```
termux-learning-web/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   └── layout/
│   │       ├── MainLayout.js
│   │       └── MainLayout.css
│   ├── pages/
│   │   ├── HomePage.js
│   │   ├── CategoryPage.js
│   │   ├── CommandDetailPage.js
│   │   ├── SearchPage.js
│   │   ├── FavoritesPage.js
│   │   └── SettingsPage.js
│   ├── data/
│   │   └── commands.js
│   ├── i18n/
│   │   ├── config.js
│   │   └── locales/
│   │       ├── ar.json
│   │       └── en.json
│   ├── theme/
│   │   └── theme.js
│   ├── App.js
│   ├── App.css
│   ├── index.js
│   └── index.css
├── package.json
├── .gitignore
└── README.md
```

---

## 🛠️ Available Scripts | الأوامر المتاحة

### `npm start`
تشغيل التطبيق في وضع التطوير على `http://localhost:3000`

### `npm build`
بناء التطبيق للإنتاج في مجلد `build`

### `npm test`
تشغيل الاختبارات

### `npm deploy`
نشر التطبيق على GitHub Pages

---

## 🌐 Deployment | النشر

### Deploy to GitHub Pages

```bash
# 1. Update homepage in package.json
# "homepage": "https://yourusername.github.io/termux-learning-web"

# 2. Deploy
npm run deploy
```

التطبيق سيكون متاحاً على: `https://yourusername.github.io/termux-learning-web`

### Deploy to Vercel

```bash
# 1. Install Vercel CLI
npm i -g vercel

# 2. Deploy
vercel
```

### Deploy to Netlify

```bash
# 1. Build the project
npm run build

# 2. Drag and drop 'build' folder to Netlify
# Or use Netlify CLI
npm i -g netlify-cli
netlify deploy --prod --dir=build
```

---

## 📱 Features Details | تفاصيل الميزات

### 1. Home Page
عرض جميع الفئات بتصميم بطاقات جميل مع إمكانية الانتقال إلى كل فئة.

### 2. Category Page
عرض جميع الأوامر في الفئة المختارة مع إمكانية الضغط على أي أمر لرؤية التفاصيل.

### 3. Command Detail
عرض تفاصيل كاملة للأمر:
- الصيغة الأساسية
- الوصف الشامل
- أمثلة عملية
- نصائح مفيدة
- الأخطاء الشائعة والحلول
- زر نسخ ومشاركة

### 4. Search
بحث متقدم عن الأوامر بالعربية والإنجليزية.

### 5. Favorites
حفظ الأوامر المفضلة محلياً واسترجاعها بسهولة.

### 6. Settings
تغيير اللغة والموضوع والمزيد.

---

## 🎨 Customization | التخصيص

### تغيير الألوان

عدّل ملف `src/theme/theme.js`:

```javascript
primary: {
  main: '#00ff00',  // اللون الأساسي
  light: '#66ff66',
  dark: '#00cc00',
}
```

### إضافة أوامر جديدة

عدّل ملف `src/data/commands.js`:

```javascript
export const commands = [
  {
    id: 'unique-id',
    category_id: 'category-id',
    title_ar: 'عنوان بالعربية',
    title_en: 'English Title',
    // ... more fields
  }
];
```

### تغيير الترجمات

عدّل ملفات `src/i18n/locales/`:
- `ar.json` - الترجمة العربية
- `en.json` - الترجمة الإنجليزية

---

## 🔧 Technologies Used | التقنيات المستخدمة

- **React 18** - مكتبة واجهات المستخدم
- **Material-UI (MUI)** - مكتبة المكونات
- **i18next** - إدارة اللغات
- **CSS3** - التنسيق والتصميم
- **React Router** - التوجيه (اختياري)
- **Firebase** - قاعدة البيانات (اختياري)

---

## 📚 Learning Resources | موارد التعلم

- [React Documentation](https://react.dev)
- [Material-UI Documentation](https://mui.com)
- [i18next Documentation](https://www.i18next.com)
- [Termux Documentation](https://termux.dev)

---

## 🐛 Troubleshooting | استكشاف الأخطاء

### المشكلة: "npm: command not found"
**الحل:** تأكد من تثبيت Node.js من [nodejs.org](https://nodejs.org)

### المشكلة: "Port 3000 is already in use"
**الحل:** استخدم منفذ مختلف:
```bash
PORT=3001 npm start
```

### المشكلة: "Module not found"
**الحل:** أعد تثبيت المكتبات:
```bash
rm -rf node_modules
npm install
```

---

## 📄 License | الترخيص

MIT License - يمكنك استخدام هذا المشروع بحرية.

---

## 👨‍💻 Contributing | المساهمة

نرحب بمساهماتك! يرجى:

1. Fork المشروع
2. أنشئ فرع جديد (`git checkout -b feature/amazing-feature`)
3. Commit التغييرات (`git commit -m 'Add amazing feature'`)
4. Push إلى الفرع (`git push origin feature/amazing-feature`)
5. افتح Pull Request

---

## 📞 Support | الدعم

للمساعدة والدعم:
- 📧 البريد الإلكتروني: support@example.com
- 🐛 Report Issues: [GitHub Issues](https://github.com/yourusername/termux-learning-web/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/yourusername/termux-learning-web/discussions)

---

## 🙏 Acknowledgments | شكر وتقدير

شكر خاص لـ:
- فريق Termux
- فريق React
- فريق Material-UI

---

**Made with ❤️ by Manus AI**

**آخر تحديث | Last Updated:** 2026-03-26
**الإصدار | Version:** 1.0.0
