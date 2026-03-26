export const categories = [
  {
    id: 'linux',
    title_ar: 'استخدام لينكس الأساسي',
    title_en: 'Basic Linux Usage',
    description_ar: 'أوامر أساسية لإدارة الملفات والمجلدات',
    description_en: 'Basic commands for file management',
    icon: 'Terminal',
    difficulty: 'beginner',
    order: 1
  },
  {
    id: 'package',
    title_ar: 'إدارة الحزم',
    title_en: 'Package Management',
    description_ar: 'تحديث وتثبيت الحزم',
    description_en: 'Update and install packages',
    icon: 'Package',
    difficulty: 'beginner',
    order: 2
  },
  {
    id: 'scripting',
    title_ar: 'البرمجة والسكريبتات',
    title_en: 'Programming & Scripting',
    description_ar: 'إعداد بيئات البرمجة',
    description_en: 'Setup programming environments',
    icon: 'Code',
    difficulty: 'intermediate',
    order: 3
  },
  {
    id: 'networking',
    title_ar: 'الشبكات والاتصال',
    title_en: 'Networking & Connectivity',
    description_ar: 'أدوات الشبكة والاتصال',
    description_en: 'Network tools and connectivity',
    icon: 'Wifi',
    difficulty: 'intermediate',
    order: 4
  },
  {
    id: 'api',
    title_ar: 'واجهة Termux API',
    title_en: 'Termux API',
    description_ar: 'الوصول إلى ميزات الجهاز',
    description_en: 'Access device features',
    icon: 'Settings',
    difficulty: 'advanced',
    order: 5
  },
  {
    id: 'server',
    title_ar: 'الخوادم والويب',
    title_en: 'Servers & Web',
    description_ar: 'إعداد خوادم الويب',
    description_en: 'Setup web servers',
    icon: 'Server',
    difficulty: 'advanced',
    order: 6
  },
  {
    id: 'gui',
    title_ar: 'الواجهة الرسومية',
    title_en: 'Linux GUI',
    description_ar: 'دعم الواجهات الرسومية',
    description_en: 'Graphical interface support',
    icon: 'Monitor',
    difficulty: 'advanced',
    order: 7
  },
  {
    id: 'custom',
    title_ar: 'التخصيص والإعدادات',
    title_en: 'Customization & Settings',
    description_ar: 'تخصيص Termux',
    description_en: 'Customize Termux',
    icon: 'Palette',
    difficulty: 'beginner',
    order: 8
  },
  {
    id: 'docker',
    title_ar: 'Docker والحاويات',
    title_en: 'Docker & Containers',
    description_ar: 'تثبيت وتشغيل الحاويات',
    description_en: 'Install and run containers',
    icon: 'Box',
    difficulty: 'advanced',
    order: 9
  },
  {
    id: 'monitoring',
    title_ar: 'المراقبة والأداء',
    title_en: 'Monitoring & Performance',
    description_ar: 'مراقبة الموارد',
    description_en: 'Monitor resources',
    icon: 'Activity',
    difficulty: 'intermediate',
    order: 10
  }
];

export const commands = [
  // Linux Commands
  {
    id: 'linux-1',
    category_id: 'linux',
    title_ar: 'قائمة الملفات',
    title_en: 'List Files',
    description_ar: 'عرض قائمة بالملفات والمجلدات في المجلد الحالي',
    description_en: 'Display a list of files and directories in the current folder',
    command_syntax: 'ls',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'ls', command_en: 'ls', description_ar: 'عرض الملفات البسيط', description_en: 'Simple file listing' },
      { command_ar: 'ls -la', command_en: 'ls -la', description_ar: 'عرض جميع الملفات مع التفاصيل', description_en: 'Show all files with details' },
      { command_ar: 'ls -lh', command_en: 'ls -lh', description_ar: 'عرض حجم الملفات بصيغة مقروءة', description_en: 'Show file sizes in readable format' }
    ],
    tips: [
      { tip_ar: 'استخدم -a لعرض الملفات المخفية', tip_en: 'Use -a to show hidden files' },
      { tip_ar: 'استخدم -l لعرض التفاصيل الكاملة', tip_en: 'Use -l to show full details' }
    ],
    errors: [
      { error_ar: 'لا يوجد ملف أو مجلد', error_en: 'No such file or directory', solution_ar: 'تأكد من المسار الصحيح', solution_en: 'Verify the correct path' }
    ]
  },
  {
    id: 'linux-2',
    category_id: 'linux',
    title_ar: 'تغيير المجلد',
    title_en: 'Change Directory',
    description_ar: 'الانتقال إلى مجلد آخر',
    description_en: 'Navigate to another directory',
    command_syntax: 'cd [directory]',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'cd /home', command_en: 'cd /home', description_ar: 'الانتقال إلى مجلد home', description_en: 'Go to home directory' },
      { command_ar: 'cd ..', command_en: 'cd ..', description_ar: 'الانتقال إلى المجلد الأب', description_en: 'Go to parent directory' },
      { command_ar: 'cd ~', command_en: 'cd ~', description_ar: 'الانتقال إلى مجلد المستخدم', description_en: 'Go to user home directory' }
    ],
    tips: [
      { tip_ar: 'استخدم cd بدون معاملات للعودة إلى المنزل', tip_en: 'Use cd without arguments to go home' }
    ],
    errors: []
  },
  {
    id: 'linux-3',
    category_id: 'linux',
    title_ar: 'إنشاء مجلد',
    title_en: 'Make Directory',
    description_ar: 'إنشاء مجلد جديد',
    description_en: 'Create a new directory',
    command_syntax: 'mkdir [directory_name]',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'mkdir my_folder', command_en: 'mkdir my_folder', description_ar: 'إنشاء مجلد جديد', description_en: 'Create a new folder' },
      { command_ar: 'mkdir -p a/b/c', command_en: 'mkdir -p a/b/c', description_ar: 'إنشاء مجلدات متعددة', description_en: 'Create multiple directories' }
    ],
    tips: [
      { tip_ar: 'استخدم -p لإنشاء المجلدات الأب تلقائياً', tip_en: 'Use -p to create parent directories automatically' }
    ],
    errors: []
  },
  // Package Management Commands
  {
    id: 'package-1',
    category_id: 'package',
    title_ar: 'تحديث قوائم الحزم',
    title_en: 'Update Package Lists',
    description_ar: 'تحديث قوائم الحزم المتاحة',
    description_en: 'Update available packages list',
    command_syntax: 'pkg update',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'pkg update', command_en: 'pkg update', description_ar: 'تحديث قائمة الحزم', description_en: 'Update package list' }
    ],
    tips: [
      { tip_ar: 'قم بتشغيل هذا الأمر بانتظام', tip_en: 'Run this command regularly' }
    ],
    errors: []
  },
  {
    id: 'package-2',
    category_id: 'package',
    title_ar: 'ترقية الحزم',
    title_en: 'Upgrade Packages',
    description_ar: 'ترقية جميع الحزم المثبتة',
    description_en: 'Upgrade all installed packages',
    command_syntax: 'pkg upgrade',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'pkg upgrade', command_en: 'pkg upgrade', description_ar: 'ترقية جميع الحزم', description_en: 'Upgrade all packages' }
    ],
    tips: [
      { tip_ar: 'قم بتشغيل pkg update أولاً', tip_en: 'Run pkg update first' }
    ],
    errors: []
  },
  {
    id: 'package-3',
    category_id: 'package',
    title_ar: 'تثبيت الحزم',
    title_en: 'Install Packages',
    description_ar: 'تثبيت حزمة جديدة',
    description_en: 'Install a new package',
    command_syntax: 'pkg install [package_name]',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'pkg install python', command_en: 'pkg install python', description_ar: 'تثبيت Python', description_en: 'Install Python' },
      { command_ar: 'pkg install git', command_en: 'pkg install git', description_ar: 'تثبيت Git', description_en: 'Install Git' }
    ],
    tips: [
      { tip_ar: 'يمكنك تثبيت عدة حزم في أمر واحد', tip_en: 'You can install multiple packages in one command' }
    ],
    errors: []
  },
  // Scripting Commands
  {
    id: 'scripting-1',
    category_id: 'scripting',
    title_ar: 'تثبيت بايثون',
    title_en: 'Install Python',
    description_ar: 'تثبيت لغة البرمجة بايثون',
    description_en: 'Install Python programming language',
    command_syntax: 'pkg install python',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'pkg install python', command_en: 'pkg install python', description_ar: 'تثبيت أحدث إصدار', description_en: 'Install latest version' },
      { command_ar: 'python --version', command_en: 'python --version', description_ar: 'التحقق من الإصدار', description_en: 'Check version' }
    ],
    tips: [
      { tip_ar: 'استخدم pip لتثبيت مكتبات Python', tip_en: 'Use pip to install Python libraries' }
    ],
    errors: []
  },
  {
    id: 'scripting-2',
    category_id: 'scripting',
    title_ar: 'تثبيت Node.js',
    title_en: 'Install Node.js',
    description_ar: 'تثبيت Node.js وnpm',
    description_en: 'Install Node.js and npm',
    command_syntax: 'pkg install nodejs',
    difficulty: 'beginner',
    examples: [
      { command_ar: 'pkg install nodejs', command_en: 'pkg install nodejs', description_ar: 'تثبيت Node.js', description_en: 'Install Node.js' },
      { command_ar: 'node --version', command_en: 'node --version', description_ar: 'التحقق من الإصدار', description_en: 'Check version' }
    ],
    tips: [
      { tip_ar: 'npm يتم تثبيته تلقائياً مع Node.js', tip_en: 'npm is installed automatically with Node.js' }
    ],
    errors: []
  }
];

export const getCommandsByCategory = (categoryId) => {
  return commands.filter(cmd => cmd.category_id === categoryId);
};

export const searchCommands = (query) => {
  const lowerQuery = query.toLowerCase();
  return commands.filter(cmd =>
    cmd.title_ar.toLowerCase().includes(lowerQuery) ||
    cmd.title_en.toLowerCase().includes(lowerQuery) ||
    cmd.description_ar.toLowerCase().includes(lowerQuery) ||
    cmd.description_en.toLowerCase().includes(lowerQuery) ||
    cmd.command_syntax.toLowerCase().includes(lowerQuery)
  );
};
