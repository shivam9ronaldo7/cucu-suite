import React from "react";
import {
  createTheme,
  ThemeProvider,
  CssBaseline
} from "@mui/material";

export const ColorModeContext = React.createContext();

export default function DashboardTheme({ children }) {

  const prefersDark = window.matchMedia(
    "(prefers-color-scheme: dark)"
  ).matches;

  const [mode, setMode] = React.useState(
    prefersDark ? "dark" : "light"
  );

  const colorMode = React.useMemo(() => ({
    toggleColorMode: () => {
      setMode(prev => (prev === "light" ? "dark" : "light"));
    }
  }), []);

  const theme = React.useMemo(() =>
    createTheme({
      palette: {
        mode,
        primary: { main: "#1976d2" },
        background: {
          default: mode === "dark" ? "#121212" : "#f4f6f8"
        }
      }
    }), [mode]);

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}
