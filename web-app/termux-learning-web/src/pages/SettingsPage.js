import React from 'react';
import { Box, Typography, Card, CardContent, Button, Stack, Switch, FormControlLabel } from '@mui/material';
import { useTranslation } from 'react-i18next';

function SettingsPage({ onLanguageChange, onThemeToggle, currentTheme }) {
  const { t, i18n } = useTranslation();

  return (
    <Box className="fade-in">
      <Typography variant="h2" sx={{ mb: 3, color: '#00ff00' }}>
        {t('nav.settings')}
      </Typography>

      {/* Language Settings */}
      <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
        <CardContent>
          <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
            {t('settings.language')}
          </Typography>
          <Stack direction="row" spacing={2}>
            <Button
              variant={i18n.language === 'ar' ? 'contained' : 'outlined'}
              onClick={() => onLanguageChange('ar')}
            >
              العربية
            </Button>
            <Button
              variant={i18n.language === 'en' ? 'contained' : 'outlined'}
              onClick={() => onLanguageChange('en')}
            >
              English
            </Button>
          </Stack>
        </CardContent>
      </Card>

      {/* Theme Settings */}
      <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
        <CardContent>
          <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
            {t('settings.theme')}
          </Typography>
          <FormControlLabel
            control={
              <Switch
                checked={currentTheme === 'dark'}
                onChange={onThemeToggle}
              />
            }
            label={currentTheme === 'dark' ? 'Dark Mode' : 'Light Mode'}
          />
        </CardContent>
      </Card>

      {/* About */}
      <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
        <CardContent>
          <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
            {t('settings.about')}
          </Typography>
          <Typography variant="body2" sx={{ color: '#b3b3b3', mb: 1 }}>
            {t('app.subtitle')}
          </Typography>
          <Typography variant="caption" sx={{ color: '#666666' }}>
            Version 1.0.0 | Created by Manus AI
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
}

export default SettingsPage;
