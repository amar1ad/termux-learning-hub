import React from 'react';
import { Box, Typography, Card, CardContent, CardActionArea } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { commands } from '../data/commands';

function FavoritesPage({ favorites, onNavigate, onToggleFavorite }) {
  const { t } = useTranslation();
  const favoriteCommands = commands.filter(cmd => favorites.includes(cmd.id));

  return (
    <Box className="fade-in">
      <Typography variant="h2" sx={{ mb: 3, color: '#00ff00' }}>
        {t('nav.favorites')}
      </Typography>

      {favoriteCommands.length === 0 ? (
        <Typography variant="body1" sx={{ color: '#b3b3b3' }}>
          {t('messages.noFavorites')}
        </Typography>
      ) : (
        favoriteCommands.map((cmd) => (
          <Card key={cmd.id} sx={{ mb: 2, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
            <CardActionArea onClick={() => onNavigate('command', cmd)}>
              <CardContent>
                <Typography variant="h6" sx={{ color: '#00ff00', mb: 1 }}>
                  ❤️ {cmd.title_ar}
                </Typography>
                <Typography variant="body2" sx={{ color: '#b3b3b3' }}>
                  {cmd.description_ar}
                </Typography>
                <Typography variant="caption" sx={{ color: '#00dd00', display: 'block', mt: 1 }}>
                  $ {cmd.command_syntax}
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        ))
      )}
    </Box>
  );
}

export default FavoritesPage;
