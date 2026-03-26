import React, { useState, useEffect } from 'react';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { useTranslation } from 'react-i18next';
import { darkTheme, lightTheme } from './theme/theme';
import MainLayout from './components/layout/MainLayout';
import HomePage from './pages/HomePage';
import CategoryPage from './pages/CategoryPage';
import CommandDetailPage from './pages/CommandDetailPage';
import SearchPage from './pages/SearchPage';
import FavoritesPage from './pages/FavoritesPage';
import SettingsPage from './pages/SettingsPage';
import './App.css';

function App() {
  const { i18n } = useTranslation();
  const [currentTheme, setCurrentTheme] = useState('dark');
  const [currentPage, setCurrentPage] = useState('home');
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedCommand, setSelectedCommand] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [favorites, setFavorites] = useState([]);

  // Load preferences from localStorage
  useEffect(() => {
    const savedTheme = localStorage.getItem('theme') || 'dark';
    const savedLanguage = localStorage.getItem('language') || 'en';
    const savedFavorites = localStorage.getItem('favorites') || '[]';

    setCurrentTheme(savedTheme);
    i18n.changeLanguage(savedLanguage);
    setFavorites(JSON.parse(savedFavorites));

    // Set document direction
    document.documentElement.dir = savedLanguage === 'ar' ? 'rtl' : 'ltr';
  }, [i18n]);

  // Save preferences
  useEffect(() => {
    localStorage.setItem('theme', currentTheme);
  }, [currentTheme]);

  useEffect(() => {
    localStorage.setItem('favorites', JSON.stringify(favorites));
  }, [favorites]);

  // Handle language change
  const handleLanguageChange = (lang) => {
    i18n.changeLanguage(lang);
    localStorage.setItem('language', lang);
    document.documentElement.dir = lang === 'ar' ? 'rtl' : 'ltr';
  };

  // Handle theme toggle
  const handleThemeToggle = () => {
    setCurrentTheme(currentTheme === 'dark' ? 'light' : 'dark');
  };

  // Handle navigation
  const handleNavigate = (page, data = null) => {
    setCurrentPage(page);
    if (page === 'category' && data) {
      setSelectedCategory(data);
    } else if (page === 'command' && data) {
      setSelectedCommand(data);
    } else if (page === 'search' && data) {
      setSearchQuery(data);
    }
  };

  // Handle favorites
  const handleToggleFavorite = (commandId) => {
    setFavorites(prev => {
      if (prev.includes(commandId)) {
        return prev.filter(id => id !== commandId);
      } else {
        return [...prev, commandId];
      }
    });
  };

  const theme = currentTheme === 'dark' ? darkTheme : lightTheme;

  // Render current page
  const renderPage = () => {
    switch (currentPage) {
      case 'home':
        return <HomePage onNavigate={handleNavigate} />;
      case 'category':
        return <CategoryPage category={selectedCategory} onNavigate={handleNavigate} favorites={favorites} />;
      case 'command':
        return <CommandDetailPage command={selectedCommand} onNavigate={handleNavigate} favorites={favorites} onToggleFavorite={handleToggleFavorite} />;
      case 'search':
        return <SearchPage query={searchQuery} onNavigate={handleNavigate} favorites={favorites} />;
      case 'favorites':
        return <FavoritesPage favorites={favorites} onNavigate={handleNavigate} onToggleFavorite={handleToggleFavorite} />;
      case 'settings':
        return <SettingsPage onLanguageChange={handleLanguageChange} onThemeToggle={handleThemeToggle} currentTheme={currentTheme} />;
      default:
        return <HomePage onNavigate={handleNavigate} />;
    }
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <MainLayout
        currentPage={currentPage}
        onNavigate={handleNavigate}
        onSearch={(query) => handleNavigate('search', query)}
      >
        {renderPage()}
      </MainLayout>
    </ThemeProvider>
  );
}

export default App;
