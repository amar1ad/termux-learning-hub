import { createTheme } from '@mui/material/styles';

export const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#00ff00',
      light: '#66ff66',
      dark: '#00cc00',
    },
    secondary: {
      main: '#00dd00',
      light: '#44ff44',
      dark: '#00aa00',
    },
    background: {
      default: '#000000',
      paper: '#1a1a1a',
    },
    surface: {
      main: '#1a1a1a',
      light: '#2d2d2d',
    },
    text: {
      primary: '#ffffff',
      secondary: '#b3b3b3',
      disabled: '#666666',
    },
    success: {
      main: '#00ff00',
    },
    warning: {
      main: '#ffa500',
    },
    error: {
      main: '#ff3333',
    },
    info: {
      main: '#00ccff',
    },
    divider: '#333333',
  },
  typography: {
    fontFamily: '"Roboto", "Cairo", sans-serif',
    h1: {
      fontSize: '2.5rem',
      fontWeight: 700,
      color: '#00ff00',
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 700,
    },
    h3: {
      fontSize: '1.5rem',
      fontWeight: 600,
    },
    h4: {
      fontSize: '1.25rem',
      fontWeight: 600,
    },
    h5: {
      fontSize: '1rem',
      fontWeight: 600,
    },
    h6: {
      fontSize: '0.875rem',
      fontWeight: 600,
    },
    body1: {
      fontSize: '1rem',
      lineHeight: 1.5,
    },
    body2: {
      fontSize: '0.875rem',
      lineHeight: 1.43,
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          fontWeight: 600,
          borderRadius: '8px',
          padding: '10px 20px',
        },
        contained: {
          backgroundColor: '#00ff00',
          color: '#000000',
          '&:hover': {
            backgroundColor: '#00dd00',
          },
        },
        outlined: {
          borderColor: '#00ff00',
          color: '#00ff00',
          '&:hover': {
            backgroundColor: 'rgba(0, 255, 0, 0.1)',
            borderColor: '#00dd00',
          },
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          backgroundColor: '#1a1a1a',
          border: '1px solid #333333',
          borderRadius: '12px',
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            '& fieldset': {
              borderColor: '#333333',
            },
            '&:hover fieldset': {
              borderColor: '#00ff00',
            },
            '&.Mui-focused fieldset': {
              borderColor: '#00ff00',
            },
          },
        },
      },
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundColor: '#000000',
          borderBottom: '2px solid #00ff00',
        },
      },
    },
    MuiChip: {
      styleOverrides: {
        root: {
          backgroundColor: '#1a1a1a',
          borderColor: '#00ff00',
          color: '#00ff00',
        },
      },
    },
  },
});

export const lightTheme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#00cc00',
      light: '#66ff66',
      dark: '#009900',
    },
    secondary: {
      main: '#00aa00',
    },
    background: {
      default: '#ffffff',
      paper: '#f5f5f5',
    },
    text: {
      primary: '#212121',
      secondary: '#757575',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Cairo", sans-serif',
  },
});
