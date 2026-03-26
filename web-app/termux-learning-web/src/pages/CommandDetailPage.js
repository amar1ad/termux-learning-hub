import React from 'react';
import { Box, Typography, Button, Card, CardContent, Chip, Stack, TextField } from '@mui/material';
import { ContentCopy, Share, Favorite, FavoriteBorder } from '@mui/icons-material';
import { useTranslation } from 'react-i18next';

function CommandDetailPage({ command, onNavigate, favorites, onToggleFavorite }) {
  const { t } = useTranslation();
  const isFavorite = favorites.includes(command?.id);

  if (!command) {
    return <Typography>No command selected</Typography>;
  }

  const handleCopy = () => {
    navigator.clipboard.writeText(command.command_syntax);
    alert(t('messages.copied'));
  };

  const handleShare = () => {
    if (navigator.share) {
      navigator.share({
        title: command.title_ar,
        text: `Command: ${command.command_syntax}`,
      });
    }
  };

  return (
    <Box className="fade-in">
      <Typography variant="h2" sx={{ mb: 2, color: '#00ff00' }}>
        {command.title_ar}
      </Typography>

      {/* Command Syntax */}
      <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '2px solid #00ff00' }}>
        <CardContent>
          <Typography variant="subtitle2" sx={{ color: '#b3b3b3', mb: 1 }}>
            {t('sections.syntax')}
          </Typography>
          <Stack direction="row" spacing={1} sx={{ alignItems: 'center' }}>
            <TextField
              fullWidth
              value={command.command_syntax}
              InputProps={{ readOnly: true }}
              sx={{ backgroundColor: '#000000' }}
            />
            <Button
              variant="contained"
              startIcon={<ContentCopy />}
              onClick={handleCopy}
              sx={{ whiteSpace: 'nowrap' }}
            >
              {t('actions.copy')}
            </Button>
            <Button
              variant="outlined"
              startIcon={<Share />}
              onClick={handleShare}
              sx={{ whiteSpace: 'nowrap' }}
            >
              {t('actions.share')}
            </Button>
            <Button
              variant={isFavorite ? 'contained' : 'outlined'}
              startIcon={isFavorite ? <Favorite /> : <FavoriteBorder />}
              onClick={() => onToggleFavorite(command.id)}
              sx={{ whiteSpace: 'nowrap' }}
            >
              {isFavorite ? t('actions.unfavorite') : t('actions.favorite')}
            </Button>
          </Stack>
        </CardContent>
      </Card>

      {/* Description */}
      <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
        <CardContent>
          <Typography variant="h6" sx={{ color: '#00ff00', mb: 1 }}>
            {t('sections.description')}
          </Typography>
          <Typography variant="body2" sx={{ color: '#b3b3b3' }}>
            {command.description_ar}
          </Typography>
        </CardContent>
      </Card>

      {/* Examples */}
      {command.examples && command.examples.length > 0 && (
        <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
          <CardContent>
            <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
              {t('sections.examples')}
            </Typography>
            {command.examples.map((ex, idx) => (
              <Box key={idx} sx={{ mb: 2, p: 1, backgroundColor: '#000000', borderRadius: 1 }}>
                <Typography variant="caption" sx={{ color: '#00dd00', display: 'block', fontFamily: 'monospace' }}>
                  $ {ex.command_ar}
                </Typography>
                <Typography variant="body2" sx={{ color: '#b3b3b3' }}>
                  {ex.description_ar}
                </Typography>
              </Box>
            ))}
          </CardContent>
        </Card>
      )}

      {/* Tips */}
      {command.tips && command.tips.length > 0 && (
        <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
          <CardContent>
            <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
              {t('sections.tips')}
            </Typography>
            <Stack spacing={1}>
              {command.tips.map((tip, idx) => (
                <Chip key={idx} label={tip.tip_ar} color="primary" variant="outlined" />
              ))}
            </Stack>
          </CardContent>
        </Card>
      )}

      {/* Errors */}
      {command.errors && command.errors.length > 0 && (
        <Card sx={{ mb: 3, backgroundColor: '#1a1a1a', border: '1px solid #333333' }}>
          <CardContent>
            <Typography variant="h6" sx={{ color: '#00ff00', mb: 2 }}>
              {t('sections.errors')}
            </Typography>
            {command.errors.map((err, idx) => (
              <Box key={idx} sx={{ mb: 2, p: 1, backgroundColor: '#000000', borderRadius: 1 }}>
                <Typography variant="subtitle2" sx={{ color: '#ff3333' }}>
                  ❌ {err.error_ar}
                </Typography>
                <Typography variant="body2" sx={{ color: '#b3b3b3', mt: 1 }}>
                  ✅ {err.solution_ar}
                </Typography>
              </Box>
            ))}
          </CardContent>
        </Card>
      )}
    </Box>
  );
}

export default CommandDetailPage;
