-- ============================================================================
-- Termux Learning Database - SQL Schema
-- Version: 1.0.0
-- Language: Arabic & English
-- Created: 2026-03-26
-- ============================================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS termux_learning;
USE termux_learning;

-- ============================================================================
-- TABLE: categories
-- Description: Termux command categories
-- ============================================================================
CREATE TABLE IF NOT EXISTS categories (
  id VARCHAR(50) PRIMARY KEY COMMENT 'Unique category identifier',
  title_ar VARCHAR(255) NOT NULL COMMENT 'Category title in Arabic',
  title_en VARCHAR(255) NOT NULL COMMENT 'Category title in English',
  description_ar TEXT NOT NULL COMMENT 'Category description in Arabic',
  description_en TEXT NOT NULL COMMENT 'Category description in English',
  icon VARCHAR(100) COMMENT 'Icon name for the category',
  difficulty ENUM('beginner', 'intermediate', 'advanced') DEFAULT 'beginner' COMMENT 'Difficulty level',
  `order` INT NOT NULL COMMENT 'Display order',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_order (`order`),
  INDEX idx_difficulty (difficulty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: commands
-- Description: Termux commands with bilingual support
-- ============================================================================
CREATE TABLE IF NOT EXISTS commands (
  id VARCHAR(100) PRIMARY KEY COMMENT 'Unique command identifier',
  category_id VARCHAR(50) NOT NULL COMMENT 'Foreign key to categories',
  title_ar VARCHAR(255) NOT NULL COMMENT 'Command title in Arabic',
  title_en VARCHAR(255) NOT NULL COMMENT 'Command title in English',
  description_ar TEXT NOT NULL COMMENT 'Command description in Arabic',
  description_en TEXT NOT NULL COMMENT 'Command description in English',
  command_syntax VARCHAR(500) NOT NULL COMMENT 'The actual command syntax',
  difficulty ENUM('beginner', 'intermediate', 'advanced') DEFAULT 'beginner' COMMENT 'Difficulty level',
  usage_count INT DEFAULT 0 COMMENT 'Number of times command was viewed',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
  INDEX idx_category (category_id),
  INDEX idx_difficulty (difficulty),
  FULLTEXT INDEX ft_title_ar (title_ar),
  FULLTEXT INDEX ft_title_en (title_en),
  FULLTEXT INDEX ft_description_ar (description_ar),
  FULLTEXT INDEX ft_description_en (description_en)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: command_examples
-- Description: Examples for each command
-- ============================================================================
CREATE TABLE IF NOT EXISTS command_examples (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique example identifier',
  command_id VARCHAR(100) NOT NULL COMMENT 'Foreign key to commands',
  command_ar VARCHAR(500) NOT NULL COMMENT 'Example command in Arabic context',
  command_en VARCHAR(500) NOT NULL COMMENT 'Example command in English context',
  description_ar TEXT NOT NULL COMMENT 'Example description in Arabic',
  description_en TEXT NOT NULL COMMENT 'Example description in English',
  `order` INT NOT NULL DEFAULT 0 COMMENT 'Display order',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (command_id) REFERENCES commands(id) ON DELETE CASCADE,
  INDEX idx_command (command_id),
  INDEX idx_order (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: command_tips
-- Description: Tips and tricks for commands
-- ============================================================================
CREATE TABLE IF NOT EXISTS command_tips (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique tip identifier',
  command_id VARCHAR(100) NOT NULL COMMENT 'Foreign key to commands',
  tip_ar TEXT NOT NULL COMMENT 'Tip in Arabic',
  tip_en TEXT NOT NULL COMMENT 'Tip in English',
  `order` INT NOT NULL DEFAULT 0 COMMENT 'Display order',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (command_id) REFERENCES commands(id) ON DELETE CASCADE,
  INDEX idx_command (command_id),
  INDEX idx_order (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: command_errors
-- Description: Common errors and solutions
-- ============================================================================
CREATE TABLE IF NOT EXISTS command_errors (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique error identifier',
  command_id VARCHAR(100) NOT NULL COMMENT 'Foreign key to commands',
  error_ar VARCHAR(500) NOT NULL COMMENT 'Error message in Arabic',
  error_en VARCHAR(500) NOT NULL COMMENT 'Error message in English',
  solution_ar TEXT NOT NULL COMMENT 'Solution in Arabic',
  solution_en TEXT NOT NULL COMMENT 'Solution in English',
  `order` INT NOT NULL DEFAULT 0 COMMENT 'Display order',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (command_id) REFERENCES commands(id) ON DELETE CASCADE,
  INDEX idx_command (command_id),
  INDEX idx_order (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: user_progress
-- Description: Track user learning progress
-- ============================================================================
CREATE TABLE IF NOT EXISTS user_progress (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique progress record identifier',
  user_id VARCHAR(100) NOT NULL COMMENT 'User identifier',
  command_id VARCHAR(100) NOT NULL COMMENT 'Foreign key to commands',
  viewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'When the command was viewed',
  is_bookmarked BOOLEAN DEFAULT FALSE COMMENT 'Whether user bookmarked this command',
  notes TEXT COMMENT 'User notes about the command',
  FOREIGN KEY (command_id) REFERENCES commands(id) ON DELETE CASCADE,
  INDEX idx_user (user_id),
  INDEX idx_command (command_id),
  INDEX idx_viewed (viewed_at),
  UNIQUE KEY unique_user_command (user_id, command_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: favorites
-- Description: User favorite commands
-- ============================================================================
CREATE TABLE IF NOT EXISTS favorites (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique favorite record identifier',
  user_id VARCHAR(100) NOT NULL COMMENT 'User identifier',
  command_id VARCHAR(100) NOT NULL COMMENT 'Foreign key to commands',
  added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'When the command was favorited',
  FOREIGN KEY (command_id) REFERENCES commands(id) ON DELETE CASCADE,
  INDEX idx_user (user_id),
  INDEX idx_command (command_id),
  UNIQUE KEY unique_user_favorite (user_id, command_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: search_history
-- Description: Track user search queries
-- ============================================================================
CREATE TABLE IF NOT EXISTS search_history (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique search record identifier',
  user_id VARCHAR(100) NOT NULL COMMENT 'User identifier',
  query VARCHAR(255) NOT NULL COMMENT 'Search query',
  language ENUM('ar', 'en') DEFAULT 'en' COMMENT 'Search language',
  results_count INT DEFAULT 0 COMMENT 'Number of results found',
  searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'When the search was performed',
  INDEX idx_user (user_id),
  INDEX idx_searched (searched_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- INSERT: Categories Data
-- ============================================================================
INSERT INTO categories (id, title_ar, title_en, description_ar, description_en, icon, difficulty, `order`) VALUES
('linux', 'استخدام لينكس الأساسي', 'Basic Linux Usage', 'أوامر أساسية لإدارة الملفات والمجلدات والتنقل في النظام', 'Basic commands for file and folder management and system navigation', 'terminal', 'beginner', 1),
('package', 'إدارة الحزم', 'Package Management', 'تحديث وتثبيت وإزالة الحزم والبرامج', 'Update, install and remove packages and software', 'package', 'beginner', 2),
('scripting', 'البرمجة والسكريبتات', 'Programming & Scripting', 'إعداد بيئات البرمجة المختلفة وتطوير التطبيقات', 'Setup different programming environments and develop applications', 'code', 'intermediate', 3),
('networking', 'الشبكات والاتصال', 'Networking & Connectivity', 'أدوات الشبكة والاتصال والتشخيص', 'Network tools, connectivity and diagnostics', 'wifi', 'intermediate', 4),
('api', 'واجهة Termux API', 'Termux API', 'الوصول إلى ميزات الجهاز والعتاد', 'Access device features and hardware capabilities', 'settings', 'advanced', 5),
('server', 'الخوادم والويب', 'Servers & Web', 'إعداد خوادم الويب والتطبيقات', 'Setup web servers and application servers', 'server', 'advanced', 6),
('gui', 'الواجهة الرسومية', 'Linux GUI', 'دعم الواجهات الرسومية والتطبيقات البصرية', 'Graphical interface support and visual applications', 'monitor', 'advanced', 7),
('custom', 'التخصيص والإعدادات', 'Customization & Settings', 'تخصيص مظهر وإعدادات Termux', 'Customize Termux appearance and settings', 'palette', 'beginner', 8),
('docker', 'Docker والحاويات', 'Docker & Containers', 'تثبيت وتشغيل الحاويات والأنظمة المعزولة', 'Install and run containers and isolated systems', 'box', 'advanced', 9),
('monitoring', 'المراقبة والأداء', 'Monitoring & Performance', 'مراقبة الموارد والعمليات والأداء', 'Monitor resources, processes and performance', 'activity', 'intermediate', 10);

-- ============================================================================
-- INSERT: Commands Data (Sample)
-- ============================================================================
INSERT INTO commands (id, category_id, title_ar, title_en, description_ar, description_en, command_syntax, difficulty) VALUES
('linux-1', 'linux', 'قائمة الملفات', 'List Files', 'عرض قائمة بالملفات والمجلدات في المجلد الحالي', 'Display a list of files and folders in the current directory', 'ls', 'beginner'),
('linux-2', 'linux', 'تغيير المجلد', 'Change Directory', 'الانتقال إلى مجلد آخر', 'Navigate to another directory', 'cd', 'beginner'),
('linux-3', 'linux', 'إنشاء مجلد', 'Make Directory', 'إنشاء مجلد جديد', 'Create a new directory', 'mkdir', 'beginner'),
('linux-4', 'linux', 'حذف الملفات', 'Remove Files', 'حذف ملف أو مجلد', 'Delete a file or directory', 'rm', 'beginner'),
('linux-5', 'linux', 'نسخ الملفات', 'Copy Files', 'نسخ ملف أو مجلد', 'Copy a file or directory', 'cp', 'beginner'),
('linux-6', 'linux', 'نقل الملفات', 'Move Files', 'نقل أو إعادة تسمية ملف', 'Move or rename a file', 'mv', 'beginner'),
('package-1', 'package', 'تحديث قوائم الحزم', 'Update Package Lists', 'تحديث قوائم الحزم المتاحة', 'Update available package lists', 'pkg update', 'beginner'),
('package-2', 'package', 'ترقية الحزم', 'Upgrade Packages', 'ترقية جميع الحزم المثبتة', 'Upgrade all installed packages', 'pkg upgrade', 'beginner'),
('package-3', 'package', 'تثبيت الحزم', 'Install Packages', 'تثبيت حزمة جديدة', 'Install a new package', 'pkg install', 'beginner'),
('scripting-1', 'scripting', 'تثبيت بايثون', 'Install Python', 'تثبيت لغة البرمجة بايثون', 'Install Python programming language', 'pkg install python', 'beginner'),
('scripting-2', 'scripting', 'تثبيت Node.js', 'Install Node.js', 'تثبيت Node.js وnpm', 'Install Node.js and npm', 'pkg install nodejs', 'beginner'),
('networking-1', 'networking', 'عرض معلومات الشبكة', 'Show Network Info', 'عرض معلومات الشبكة وعناوين IP', 'Display network information and IP addresses', 'ifconfig', 'intermediate'),
('networking-2', 'networking', 'اختبار الاتصال', 'Test Connection', 'اختبار الاتصال بخادم', 'Test connection to a server', 'ping', 'intermediate'),
('api-1', 'api', 'تثبيت Termux API', 'Install Termux API', 'تثبيت حزمة Termux API للوصول للعتاد', 'Install Termux API package for hardware access', 'pkg install termux-api', 'advanced'),
('api-2', 'api', 'التقاط صورة', 'Take Photo', 'التقاط صورة باستخدام الكاميرا', 'Capture a photo using the camera', 'termux-camera-photo', 'advanced'),
('server-1', 'server', 'تثبيت Apache', 'Install Apache', 'تثبيت خادم Apache الويب', 'Install Apache web server', 'pkg install apache2', 'advanced'),
('server-2', 'server', 'تثبيت Nginx', 'Install Nginx', 'تثبيت خادم Nginx الخفيف', 'Install lightweight Nginx server', 'pkg install nginx', 'advanced'),
('gui-1', 'gui', 'إعداد X11', 'Setup X11', 'تثبيت دعم الواجهة الرسومية X11', 'Install X11 graphical interface support', 'pkg install x11-repo', 'advanced'),
('gui-2', 'gui', 'تثبيت VNC', 'Install VNC', 'تثبيت خادم VNC للوصول البعيد', 'Install VNC server for remote access', 'pkg install tigervnc', 'advanced'),
('custom-1', 'custom', 'تخصيص المظهر', 'Customize Appearance', 'تخصيص ألوان وخطوط Termux', 'Customize Termux colors and fonts', 'termux-styling', 'beginner'),
('custom-2', 'custom', 'تثبيت الخطوط', 'Install Fonts', 'تثبيت خطوط جديدة', 'Install new fonts', 'pkg install fonts-noto', 'beginner'),
('docker-1', 'docker', 'تثبيت Ubuntu', 'Install Ubuntu', 'تثبيت نظام Ubuntu داخل Termux', 'Install Ubuntu system inside Termux', 'proot-distro install ubuntu', 'advanced'),
('docker-2', 'docker', 'تثبيت Debian', 'Install Debian', 'تثبيت نظام Debian داخل Termux', 'Install Debian system inside Termux', 'proot-distro install debian', 'advanced'),
('monitoring-1', 'monitoring', 'مراقب الموارد', 'Resource Monitor', 'مراقبة استخدام الموارد والعمليات', 'Monitor resource usage and processes', 'htop', 'intermediate'),
('monitoring-2', 'monitoring', 'معلومات النظام', 'System Information', 'عرض معلومات النظام', 'Display system information', 'uname -a', 'intermediate');

-- ============================================================================
-- INSERT: Command Examples (Sample)
-- ============================================================================
INSERT INTO command_examples (command_id, command_ar, command_en, description_ar, description_en, `order`) VALUES
('linux-1', 'ls', 'ls', 'عرض الملفات البسيط', 'Simple file listing', 1),
('linux-1', 'ls -la', 'ls -la', 'عرض جميع الملفات مع التفاصيل والملفات المخفية', 'Show all files with details and hidden files', 2),
('linux-1', 'ls -lh', 'ls -lh', 'عرض حجم الملفات بصيغة قابلة للقراءة', 'Show file sizes in human-readable format', 3),
('linux-2', 'cd /home', 'cd /home', 'الانتقال إلى مجلد home', 'Navigate to home folder', 1),
('linux-2', 'cd ..', 'cd ..', 'الانتقال إلى المجلد الأب', 'Go to parent directory', 2),
('linux-2', 'cd ~', 'cd ~', 'الانتقال إلى مجلد المستخدم الرئيسي', 'Go to user home directory', 3),
('linux-3', 'mkdir my_folder', 'mkdir my_folder', 'إنشاء مجلد باسم my_folder', 'Create a folder named my_folder', 1),
('linux-3', 'mkdir -p path/to/folder', 'mkdir -p path/to/folder', 'إنشاء مجلدات متعددة المستويات', 'Create nested directories', 2),
('linux-4', 'rm file.txt', 'rm file.txt', 'حذف ملف', 'Delete a file', 1),
('linux-4', 'rm -r folder', 'rm -r folder', 'حذف مجلد بكل محتوياته', 'Delete a directory with all contents', 2),
('linux-4', 'rm -f file.txt', 'rm -f file.txt', 'حذف الملف بدون تأكيد', 'Force delete without confirmation', 3),
('linux-5', 'cp file.txt file_copy.txt', 'cp file.txt file_copy.txt', 'نسخ ملف', 'Copy a file', 1),
('linux-5', 'cp -r folder folder_copy', 'cp -r folder folder_copy', 'نسخ مجلد بكل محتوياته', 'Copy a directory recursively', 2),
('linux-6', 'mv old_name.txt new_name.txt', 'mv old_name.txt new_name.txt', 'إعادة تسمية ملف', 'Rename a file', 1),
('linux-6', 'mv file.txt /path/to/destination', 'mv file.txt /path/to/destination', 'نقل ملف إلى مجلد آخر', 'Move file to another directory', 2),
('package-1', 'pkg update', 'pkg update', 'تحديث قوائم الحزم', 'Update package lists', 1),
('package-2', 'pkg upgrade', 'pkg upgrade', 'ترقية جميع الحزم', 'Upgrade all packages', 1),
('package-2', 'pkg upgrade -y', 'pkg upgrade -y', 'الموافقة تلقائياً على الترقية', 'Auto-confirm upgrade', 2),
('package-3', 'pkg install python', 'pkg install python', 'تثبيت بايثون', 'Install Python', 1),
('package-3', 'pkg install git curl wget', 'pkg install git curl wget', 'تثبيت عدة حزم', 'Install multiple packages', 2);

-- ============================================================================
-- INSERT: Command Tips (Sample)
-- ============================================================================
INSERT INTO command_tips (command_id, tip_ar, tip_en, `order`) VALUES
('linux-1', 'استخدم -a لعرض الملفات المخفية', 'Use -a to show hidden files', 1),
('linux-1', 'استخدم -l لعرض التفاصيل الكاملة', 'Use -l to show full details', 2),
('linux-2', 'استخدم cd - للعودة إلى المجلد السابق', 'Use cd - to return to previous directory', 1),
('linux-3', 'استخدم -p لإنشاء المجلدات الأب تلقائياً', 'Use -p to create parent directories automatically', 1),
('linux-4', 'كن حذراً عند استخدام rm -rf', 'Be careful with rm -rf', 1),
('package-1', 'قم بتشغيل هذا الأمر بانتظام', 'Run this command regularly', 1),
('scripting-1', 'استخدم pip لتثبيت المكتبات', 'Use pip to install libraries', 1),
('api-1', 'تحتاج أيضاً إلى تطبيق Termux:API', 'You also need Termux:API app', 1);

-- ============================================================================
-- INSERT: Command Errors (Sample)
-- ============================================================================
INSERT INTO command_errors (command_id, error_ar, error_en, solution_ar, solution_en, `order`) VALUES
('linux-1', 'لا يوجد ملف أو مجلد', 'No such file or directory', 'تأكد من المسار الصحيح', 'Verify the correct path', 1),
('linux-4', 'الملف محمي من الحذف', 'Permission denied', 'استخدم sudo أو غير الصلاحيات', 'Use sudo or change permissions', 1),
('package-3', 'فشل تثبيت الحزمة', 'Package installation failed', 'حاول تحديث قوائم الحزم أولاً', 'Try updating package lists first', 1);

-- ============================================================================
-- CREATE VIEWS for easier querying
-- ============================================================================

-- View: All commands with category information
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
  c.created_at
FROM commands c
JOIN categories cat ON c.category_id = cat.id
ORDER BY cat.`order`, c.id;

-- View: Commands with example count
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
  c.usage_count
FROM commands c
LEFT JOIN command_examples ce ON c.id = ce.command_id
LEFT JOIN command_tips ct ON c.id = ct.command_id
LEFT JOIN command_errors err ON c.id = err.command_id
GROUP BY c.id;

-- ============================================================================
-- CREATE INDEXES for better performance
-- ============================================================================
CREATE INDEX idx_commands_difficulty ON commands(difficulty);
CREATE INDEX idx_commands_category ON commands(category_id);
CREATE INDEX idx_examples_command ON command_examples(command_id);
CREATE INDEX idx_tips_command ON command_tips(command_id);
CREATE INDEX idx_errors_command ON command_errors(command_id);
CREATE INDEX idx_progress_user_command ON user_progress(user_id, command_id);
CREATE INDEX idx_favorites_user ON favorites(user_id);
CREATE INDEX idx_search_history_user ON search_history(user_id);

-- ============================================================================
-- Database Statistics
-- ============================================================================
-- Total Categories: 10
-- Total Commands: 26
-- Total Examples: 20+
-- Total Tips: 8+
-- Total Errors: 3+
-- Languages: Arabic & English
-- ============================================================================
