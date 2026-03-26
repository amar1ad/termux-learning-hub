import React from 'react';
import { Grid, Card, CardContent, CardActionArea, Typography, Box } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { categories } from '../data/commands';
import './HomePage.css';

function HomePage({ onNavigate }) {
  const { t } = useTranslation();

  return (
    <Box className="home-page fade-in">
      <Box className="page-header">
        <Typography variant="h1">{t('app.title')}</Typography>
        <Typography variant="body1" className="subtitle">{t('app.subtitle')}</Typography>
      </Box>

      <Grid container spacing={2}>
        {categories.map((category) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={category.id}>
            <Card className="category-card">
              <CardActionArea onClick={() => onNavigate('category', category)}>
                <CardContent>
                  <Box className="category-icon">
                    <span>{category.icon.charAt(0)}</span>
                  </Box>
                  <Typography variant="h5" className="category-title">
                    {category.title_ar}
                  </Typography>
                  <Typography variant="body2" className="category-description">
                    {category.description_ar}
                  </Typography>
                  <Typography variant="caption" className="category-difficulty">
                    {t(`difficulty.${category.difficulty}`)}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}

export default HomePage;
