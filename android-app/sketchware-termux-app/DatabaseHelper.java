package com.termux.learning.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SQLite Database Helper for Termux Learning App
 * Version: 1.0.0
 * Language: Java
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "termux_learning.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_COMMANDS = "commands";
    private static final String TABLE_COMMAND_EXAMPLES = "command_examples";
    private static final String TABLE_COMMAND_TIPS = "command_tips";
    private static final String TABLE_COMMAND_ERRORS = "command_errors";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String TABLE_SEARCH_HISTORY = "search_history";

    // Categories Table Columns
    private static final String COL_CAT_ID = "id";
    private static final String COL_CAT_TITLE_AR = "title_ar";
    private static final String COL_CAT_TITLE_EN = "title_en";
    private static final String COL_CAT_DESC_AR = "description_ar";
    private static final String COL_CAT_DESC_EN = "description_en";
    private static final String COL_CAT_ICON = "icon";
    private static final String COL_CAT_DIFFICULTY = "difficulty";
    private static final String COL_CAT_ORDER = "order";

    // Commands Table Columns
    private static final String COL_CMD_ID = "id";
    private static final String COL_CMD_CATEGORY_ID = "category_id";
    private static final String COL_CMD_TITLE_AR = "title_ar";
    private static final String COL_CMD_TITLE_EN = "title_en";
    private static final String COL_CMD_DESC_AR = "description_ar";
    private static final String COL_CMD_DESC_EN = "description_en";
    private static final String COL_CMD_SYNTAX = "command_syntax";
    private static final String COL_CMD_DIFFICULTY = "difficulty";
    private static final String COL_CMD_USAGE = "usage_count";

    // Examples Table Columns
    private static final String COL_EX_ID = "id";
    private static final String COL_EX_CMD_ID = "command_id";
    private static final String COL_EX_CMD_AR = "command_ar";
    private static final String COL_EX_CMD_EN = "command_en";
    private static final String COL_EX_DESC_AR = "description_ar";
    private static final String COL_EX_DESC_EN = "description_en";

    // Tips Table Columns
    private static final String COL_TIP_ID = "id";
    private static final String COL_TIP_CMD_ID = "command_id";
    private static final String COL_TIP_AR = "tip_ar";
    private static final String COL_TIP_EN = "tip_en";

    // Errors Table Columns
    private static final String COL_ERR_ID = "id";
    private static final String COL_ERR_CMD_ID = "command_id";
    private static final String COL_ERR_MSG_AR = "error_ar";
    private static final String COL_ERR_MSG_EN = "error_en";
    private static final String COL_ERR_SOL_AR = "solution_ar";
    private static final String COL_ERR_SOL_EN = "solution_en";

    // Favorites Table Columns
    private static final String COL_FAV_ID = "id";
    private static final String COL_FAV_USER_ID = "user_id";
    private static final String COL_FAV_CMD_ID = "command_id";
    private static final String COL_FAV_DATE = "added_at";

    // Search History Table Columns
    private static final String COL_SEARCH_ID = "id";
    private static final String COL_SEARCH_USER_ID = "user_id";
    private static final String COL_SEARCH_QUERY = "query";
    private static final String COL_SEARCH_LANG = "language";
    private static final String COL_SEARCH_DATE = "searched_at";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        createTables();
        insertInitialData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMAND_EXAMPLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMAND_TIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMAND_ERRORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_HISTORY);
        onCreate(db);
    }

    private void createTables() {
        // Create Categories Table
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
                COL_CAT_ID + " TEXT PRIMARY KEY, " +
                COL_CAT_TITLE_AR + " TEXT NOT NULL, " +
                COL_CAT_TITLE_EN + " TEXT NOT NULL, " +
                COL_CAT_DESC_AR + " TEXT NOT NULL, " +
                COL_CAT_DESC_EN + " TEXT NOT NULL, " +
                COL_CAT_ICON + " TEXT, " +
                COL_CAT_DIFFICULTY + " TEXT DEFAULT 'beginner', " +
                COL_CAT_ORDER + " INTEGER NOT NULL)";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        // Create Commands Table
        String CREATE_COMMANDS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMANDS + " (" +
                COL_CMD_ID + " TEXT PRIMARY KEY, " +
                COL_CMD_CATEGORY_ID + " TEXT NOT NULL, " +
                COL_CMD_TITLE_AR + " TEXT NOT NULL, " +
                COL_CMD_TITLE_EN + " TEXT NOT NULL, " +
                COL_CMD_DESC_AR + " TEXT NOT NULL, " +
                COL_CMD_DESC_EN + " TEXT NOT NULL, " +
                COL_CMD_SYNTAX + " TEXT NOT NULL, " +
                COL_CMD_DIFFICULTY + " TEXT DEFAULT 'beginner', " +
                COL_CMD_USAGE + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY(" + COL_CMD_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COL_CAT_ID + "))";
        db.execSQL(CREATE_COMMANDS_TABLE);

        // Create Command Examples Table
        String CREATE_EXAMPLES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMAND_EXAMPLES + " (" +
                COL_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EX_CMD_ID + " TEXT NOT NULL, " +
                COL_EX_CMD_AR + " TEXT NOT NULL, " +
                COL_EX_CMD_EN + " TEXT NOT NULL, " +
                COL_EX_DESC_AR + " TEXT NOT NULL, " +
                COL_EX_DESC_EN + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_EX_CMD_ID + ") REFERENCES " + TABLE_COMMANDS + "(" + COL_CMD_ID + "))";
        db.execSQL(CREATE_EXAMPLES_TABLE);

        // Create Command Tips Table
        String CREATE_TIPS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMAND_TIPS + " (" +
                COL_TIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TIP_CMD_ID + " TEXT NOT NULL, " +
                COL_TIP_AR + " TEXT NOT NULL, " +
                COL_TIP_EN + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_TIP_CMD_ID + ") REFERENCES " + TABLE_COMMANDS + "(" + COL_CMD_ID + "))";
        db.execSQL(CREATE_TIPS_TABLE);

        // Create Command Errors Table
        String CREATE_ERRORS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMAND_ERRORS + " (" +
                COL_ERR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ERR_CMD_ID + " TEXT NOT NULL, " +
                COL_ERR_MSG_AR + " TEXT NOT NULL, " +
                COL_ERR_MSG_EN + " TEXT NOT NULL, " +
                COL_ERR_SOL_AR + " TEXT NOT NULL, " +
                COL_ERR_SOL_EN + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_ERR_CMD_ID + ") REFERENCES " + TABLE_COMMANDS + "(" + COL_CMD_ID + "))";
        db.execSQL(CREATE_ERRORS_TABLE);

        // Create Favorites Table
        String CREATE_FAVORITES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + " (" +
                COL_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FAV_USER_ID + " TEXT NOT NULL, " +
                COL_FAV_CMD_ID + " TEXT NOT NULL, " +
                COL_FAV_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "UNIQUE(" + COL_FAV_USER_ID + ", " + COL_FAV_CMD_ID + "), " +
                "FOREIGN KEY(" + COL_FAV_CMD_ID + ") REFERENCES " + TABLE_COMMANDS + "(" + COL_CMD_ID + "))";
        db.execSQL(CREATE_FAVORITES_TABLE);

        // Create Search History Table
        String CREATE_SEARCH_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEARCH_HISTORY + " (" +
                COL_SEARCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SEARCH_USER_ID + " TEXT NOT NULL, " +
                COL_SEARCH_QUERY + " TEXT NOT NULL, " +
                COL_SEARCH_LANG + " TEXT DEFAULT 'en', " +
                COL_SEARCH_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_SEARCH_TABLE);
    }

    private void insertInitialData() {
        // Insert Categories
        insertCategory("linux", "استخدام لينكس الأساسي", "Basic Linux Usage",
                "أوامر أساسية لإدارة الملفات والمجلدات", "Basic commands for file management", "terminal", "beginner", 1);
        insertCategory("package", "إدارة الحزم", "Package Management",
                "تحديث وتثبيت الحزم", "Update and install packages", "package", "beginner", 2);
        insertCategory("scripting", "البرمجة والسكريبتات", "Programming & Scripting",
                "إعداد بيئات البرمجة", "Setup programming environments", "code", "intermediate", 3);
        insertCategory("networking", "الشبكات والاتصال", "Networking & Connectivity",
                "أدوات الشبكة والاتصال", "Network tools and connectivity", "wifi", "intermediate", 4);
        insertCategory("api", "واجهة Termux API", "Termux API",
                "الوصول إلى ميزات الجهاز", "Access device features", "settings", "advanced", 5);
        insertCategory("server", "الخوادم والويب", "Servers & Web",
                "إعداد خوادم الويب", "Setup web servers", "server", "advanced", 6);
        insertCategory("gui", "الواجهة الرسومية", "Linux GUI",
                "دعم الواجهات الرسومية", "Graphical interface support", "monitor", "advanced", 7);
        insertCategory("custom", "التخصيص والإعدادات", "Customization & Settings",
                "تخصيص Termux", "Customize Termux", "palette", "beginner", 8);
        insertCategory("docker", "Docker والحاويات", "Docker & Containers",
                "تثبيت وتشغيل الحاويات", "Install and run containers", "box", "advanced", 9);
        insertCategory("monitoring", "المراقبة والأداء", "Monitoring & Performance",
                "مراقبة الموارد", "Monitor resources", "activity", "intermediate", 10);

        // Insert Commands (Sample)
        insertCommand("linux-1", "linux", "قائمة الملفات", "List Files",
                "عرض قائمة بالملفات", "Display file list", "ls", "beginner");
        insertCommand("linux-2", "linux", "تغيير المجلد", "Change Directory",
                "الانتقال إلى مجلد آخر", "Navigate to another directory", "cd", "beginner");
        insertCommand("linux-3", "linux", "إنشاء مجلد", "Make Directory",
                "إنشاء مجلد جديد", "Create a new directory", "mkdir", "beginner");
        insertCommand("linux-4", "linux", "حذف الملفات", "Remove Files",
                "حذف ملف أو مجلد", "Delete a file or directory", "rm", "beginner");
        insertCommand("linux-5", "linux", "نسخ الملفات", "Copy Files",
                "نسخ ملف أو مجلد", "Copy a file or directory", "cp", "beginner");
        insertCommand("linux-6", "linux", "نقل الملفات", "Move Files",
                "نقل أو إعادة تسمية ملف", "Move or rename a file", "mv", "beginner");

        insertCommand("package-1", "package", "تحديث قوائم الحزم", "Update Package Lists",
                "تحديث قوائم الحزم المتاحة", "Update available packages", "pkg update", "beginner");
        insertCommand("package-2", "package", "ترقية الحزم", "Upgrade Packages",
                "ترقية جميع الحزم", "Upgrade all packages", "pkg upgrade", "beginner");
        insertCommand("package-3", "package", "تثبيت الحزم", "Install Packages",
                "تثبيت حزمة جديدة", "Install a new package", "pkg install", "beginner");

        insertCommand("scripting-1", "scripting", "تثبيت بايثون", "Install Python",
                "تثبيت لغة البرمجة بايثون", "Install Python programming language", "pkg install python", "beginner");
        insertCommand("scripting-2", "scripting", "تثبيت Node.js", "Install Node.js",
                "تثبيت Node.js وnpm", "Install Node.js and npm", "pkg install nodejs", "beginner");

        // Insert Examples for linux-1
        insertExample("linux-1", "ls", "ls", "عرض الملفات البسيط", "Simple file listing");
        insertExample("linux-1", "ls -la", "ls -la", "عرض جميع الملفات", "Show all files");
        insertExample("linux-1", "ls -lh", "ls -lh", "عرض حجم الملفات", "Show file sizes");

        // Insert Tips
        insertTip("linux-1", "استخدم -a لعرض الملفات المخفية", "Use -a to show hidden files");
        insertTip("linux-1", "استخدم -l لعرض التفاصيل الكاملة", "Use -l to show full details");

        // Insert Errors
        insertError("linux-1", "لا يوجد ملف أو مجلد", "No such file or directory",
                "تأكد من المسار الصحيح", "Verify the correct path");
    }

    // Insert Methods
    public void insertCategory(String id, String titleAr, String titleEn, String descAr, String descEn,
                               String icon, String difficulty, int order) {
        ContentValues values = new ContentValues();
        values.put(COL_CAT_ID, id);
        values.put(COL_CAT_TITLE_AR, titleAr);
        values.put(COL_CAT_TITLE_EN, titleEn);
        values.put(COL_CAT_DESC_AR, descAr);
        values.put(COL_CAT_DESC_EN, descEn);
        values.put(COL_CAT_ICON, icon);
        values.put(COL_CAT_DIFFICULTY, difficulty);
        values.put(COL_CAT_ORDER, order);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    public void insertCommand(String id, String categoryId, String titleAr, String titleEn,
                              String descAr, String descEn, String syntax, String difficulty) {
        ContentValues values = new ContentValues();
        values.put(COL_CMD_ID, id);
        values.put(COL_CMD_CATEGORY_ID, categoryId);
        values.put(COL_CMD_TITLE_AR, titleAr);
        values.put(COL_CMD_TITLE_EN, titleEn);
        values.put(COL_CMD_DESC_AR, descAr);
        values.put(COL_CMD_DESC_EN, descEn);
        values.put(COL_CMD_SYNTAX, syntax);
        values.put(COL_CMD_DIFFICULTY, difficulty);
        db.insert(TABLE_COMMANDS, null, values);
    }

    public void insertExample(String commandId, String commandAr, String commandEn,
                              String descAr, String descEn) {
        ContentValues values = new ContentValues();
        values.put(COL_EX_CMD_ID, commandId);
        values.put(COL_EX_CMD_AR, commandAr);
        values.put(COL_EX_CMD_EN, commandEn);
        values.put(COL_EX_DESC_AR, descAr);
        values.put(COL_EX_DESC_EN, descEn);
        db.insert(TABLE_COMMAND_EXAMPLES, null, values);
    }

    public void insertTip(String commandId, String tipAr, String tipEn) {
        ContentValues values = new ContentValues();
        values.put(COL_TIP_CMD_ID, commandId);
        values.put(COL_TIP_AR, tipAr);
        values.put(COL_TIP_EN, tipEn);
        db.insert(TABLE_COMMAND_TIPS, null, values);
    }

    public void insertError(String commandId, String errorAr, String errorEn,
                            String solutionAr, String solutionEn) {
        ContentValues values = new ContentValues();
        values.put(COL_ERR_CMD_ID, commandId);
        values.put(COL_ERR_MSG_AR, errorAr);
        values.put(COL_ERR_MSG_EN, errorEn);
        values.put(COL_ERR_SOL_AR, solutionAr);
        values.put(COL_ERR_SOL_EN, solutionEn);
        db.insert(TABLE_COMMAND_ERRORS, null, values);
    }

    // Query Methods
    public List<HashMap<String, String>> getAllCategories() {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " ORDER BY " + COL_CAT_ORDER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(cursor.getColumnIndex(COL_CAT_ID)));
                map.put("title_ar", cursor.getString(cursor.getColumnIndex(COL_CAT_TITLE_AR)));
                map.put("title_en", cursor.getString(cursor.getColumnIndex(COL_CAT_TITLE_EN)));
                map.put("description_ar", cursor.getString(cursor.getColumnIndex(COL_CAT_DESC_AR)));
                map.put("description_en", cursor.getString(cursor.getColumnIndex(COL_CAT_DESC_EN)));
                map.put("icon", cursor.getString(cursor.getColumnIndex(COL_CAT_ICON)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<HashMap<String, String>> getCommandsByCategory(String categoryId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_COMMANDS + " WHERE " + COL_CMD_CATEGORY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{categoryId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(cursor.getColumnIndex(COL_CMD_ID)));
                map.put("title_ar", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_AR)));
                map.put("title_en", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_EN)));
                map.put("command_syntax", cursor.getString(cursor.getColumnIndex(COL_CMD_SYNTAX)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public HashMap<String, String> getCommandDetails(String commandId) {
        HashMap<String, String> map = new HashMap<>();
        String query = "SELECT * FROM " + TABLE_COMMANDS + " WHERE " + COL_CMD_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{commandId});

        if (cursor.moveToFirst()) {
            map.put("id", cursor.getString(cursor.getColumnIndex(COL_CMD_ID)));
            map.put("title_ar", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_AR)));
            map.put("title_en", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_EN)));
            map.put("description_ar", cursor.getString(cursor.getColumnIndex(COL_CMD_DESC_AR)));
            map.put("description_en", cursor.getString(cursor.getColumnIndex(COL_CMD_DESC_EN)));
            map.put("command_syntax", cursor.getString(cursor.getColumnIndex(COL_CMD_SYNTAX)));
            map.put("difficulty", cursor.getString(cursor.getColumnIndex(COL_CMD_DIFFICULTY)));
        }
        cursor.close();
        return map;
    }

    public List<HashMap<String, String>> getCommandExamples(String commandId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_COMMAND_EXAMPLES + " WHERE " + COL_EX_CMD_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{commandId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("command_ar", cursor.getString(cursor.getColumnIndex(COL_EX_CMD_AR)));
                map.put("command_en", cursor.getString(cursor.getColumnIndex(COL_EX_CMD_EN)));
                map.put("description_ar", cursor.getString(cursor.getColumnIndex(COL_EX_DESC_AR)));
                map.put("description_en", cursor.getString(cursor.getColumnIndex(COL_EX_DESC_EN)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<HashMap<String, String>> getCommandTips(String commandId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_COMMAND_TIPS + " WHERE " + COL_TIP_CMD_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{commandId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("tip_ar", cursor.getString(cursor.getColumnIndex(COL_TIP_AR)));
                map.put("tip_en", cursor.getString(cursor.getColumnIndex(COL_TIP_EN)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<HashMap<String, String>> getCommandErrors(String commandId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_COMMAND_ERRORS + " WHERE " + COL_ERR_CMD_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{commandId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("error_ar", cursor.getString(cursor.getColumnIndex(COL_ERR_MSG_AR)));
                map.put("error_en", cursor.getString(cursor.getColumnIndex(COL_ERR_MSG_EN)));
                map.put("solution_ar", cursor.getString(cursor.getColumnIndex(COL_ERR_SOL_AR)));
                map.put("solution_en", cursor.getString(cursor.getColumnIndex(COL_ERR_SOL_EN)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<HashMap<String, String>> searchCommands(String query) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_COMMANDS + " WHERE " +
                COL_CMD_TITLE_AR + " LIKE ? OR " +
                COL_CMD_TITLE_EN + " LIKE ? OR " +
                COL_CMD_DESC_AR + " LIKE ? OR " +
                COL_CMD_DESC_EN + " LIKE ? OR " +
                COL_CMD_SYNTAX + " LIKE ?";
        String searchQuery = "%" + query + "%";
        Cursor cursor = db.rawQuery(sql, new String[]{searchQuery, searchQuery, searchQuery, searchQuery, searchQuery});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(cursor.getColumnIndex(COL_CMD_ID)));
                map.put("title_ar", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_AR)));
                map.put("title_en", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_EN)));
                map.put("command_syntax", cursor.getString(cursor.getColumnIndex(COL_CMD_SYNTAX)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Favorites Methods
    public void addFavorite(String userId, String commandId) {
        ContentValues values = new ContentValues();
        values.put(COL_FAV_USER_ID, userId);
        values.put(COL_FAV_CMD_ID, commandId);
        db.insert(TABLE_FAVORITES, null, values);
    }

    public void removeFavorite(String userId, String commandId) {
        db.delete(TABLE_FAVORITES, COL_FAV_USER_ID + " = ? AND " + COL_FAV_CMD_ID + " = ?",
                new String[]{userId, commandId});
    }

    public List<HashMap<String, String>> getFavorites(String userId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT c.* FROM " + TABLE_COMMANDS + " c " +
                "JOIN " + TABLE_FAVORITES + " f ON c." + COL_CMD_ID + " = f." + COL_FAV_CMD_ID +
                " WHERE f." + COL_FAV_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(cursor.getColumnIndex(COL_CMD_ID)));
                map.put("title_ar", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_AR)));
                map.put("title_en", cursor.getString(cursor.getColumnIndex(COL_CMD_TITLE_EN)));
                map.put("command_syntax", cursor.getString(cursor.getColumnIndex(COL_CMD_SYNTAX)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean isFavorite(String userId, String commandId) {
        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " +
                COL_FAV_USER_ID + " = ? AND " + COL_FAV_CMD_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, commandId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Search History Methods
    public void addSearchHistory(String userId, String query, String language) {
        ContentValues values = new ContentValues();
        values.put(COL_SEARCH_USER_ID, userId);
        values.put(COL_SEARCH_QUERY, query);
        values.put(COL_SEARCH_LANG, language);
        db.insert(TABLE_SEARCH_HISTORY, null, values);
    }

    public List<HashMap<String, String>> getSearchHistory(String userId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String query = "SELECT DISTINCT " + COL_SEARCH_QUERY + " FROM " + TABLE_SEARCH_HISTORY +
                " WHERE " + COL_SEARCH_USER_ID + " = ? ORDER BY " + COL_SEARCH_DATE + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("query", cursor.getString(cursor.getColumnIndex(COL_SEARCH_QUERY)));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Update Methods
    public void updateUsageCount(String commandId) {
        String query = "UPDATE " + TABLE_COMMANDS + " SET " + COL_CMD_USAGE + " = " + COL_CMD_USAGE + " + 1 " +
                "WHERE " + COL_CMD_ID + " = ?";
        db.execSQL(query, new String[]{commandId});
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
