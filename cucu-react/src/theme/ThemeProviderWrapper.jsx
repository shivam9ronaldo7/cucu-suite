import { ThemeProvider, CssBaseline, createTheme } from "@mui/material";
import { DEFAULT_THEME_CONFIG } from "./defaultThemeConfig";

export default function ThemeProviderWrapper({ children }) {
  const cfg = DEFAULT_THEME_CONFIG;
  const theme = createTheme({ palette: { mode: cfg.mode } });
  return <ThemeProvider theme={theme}><CssBaseline />{children}</ThemeProvider>;
}
