import React, { useState } from 'react';
import {
  AppBar,
  Toolbar,
  IconButton,
  TextField,
  Box,
  Container,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import {
  Menu as MenuIcon,
  Search as SearchIcon,
  Home as HomeIcon,
  Favorite as FavoriteIcon,
  Settings as SettingsIcon,
  Close as CloseIcon,
} from '@mui/icons-material';
import { useTranslation } from 'react-i18next';
import './MainLayout.css';

function MainLayout({ currentPage, onNavigate, onSearch, children }) {
  const { t } = useTranslation();
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [searchOpen, setSearchOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      onSearch(searchQuery);
      setSearchQuery('');
      setSearchOpen(false);
    }
  };

  const menuItems = [
    { label: t('nav.home'), icon: <HomeIcon />, page: 'home' },
    { label: t('nav.favorites'), icon: <FavoriteIcon />, page: 'favorites' },
    { label: t('nav.settings'), icon: <SettingsIcon />, page: 'settings' },
  ];

  return (
    <Box className="main-layout">
      {/* AppBar */}
      <AppBar position="sticky" className="app-bar">
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            onClick={() => setDrawerOpen(true)}
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>

          <Box sx={{ flex: 1 }}>
            <h1 className="app-title">Termux Learning</h1>
          </Box>

          {searchOpen ? (
            <form onSubmit={handleSearch} className="search-form">
              <TextField
                size="small"
                placeholder={t('nav.search')}
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                autoFocus
                sx={{
                  width: 200,
                  '& .MuiOutlinedInput-root': {
                    color: '#ffffff',
                  },
                }}
              />
              <IconButton
                type="button"
                onClick={() => setSearchOpen(false)}
                color="inherit"
              >
                <CloseIcon />
              </IconButton>
            </form>
          ) : (
            <IconButton
              color="inherit"
              onClick={() => setSearchOpen(true)}
            >
              <SearchIcon />
            </IconButton>
          )}
        </Toolbar>
      </AppBar>

      {/* Drawer */}
      <Drawer
        anchor="left"
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
      >
        <Box sx={{ width: 250, pt: 2 }}>
          <List>
            {menuItems.map((item) => (
              <ListItem
                button
                key={item.page}
                onClick={() => {
                  onNavigate(item.page);
                  setDrawerOpen(false);
                }}
                selected={currentPage === item.page}
              >
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.label} />
              </ListItem>
            ))}
          </List>
        </Box>
      </Drawer>

      {/* Main Content */}
      <Container maxWidth="lg" className="main-content">
        {children}
      </Container>
    </Box>
  );
}

export default MainLayout;
