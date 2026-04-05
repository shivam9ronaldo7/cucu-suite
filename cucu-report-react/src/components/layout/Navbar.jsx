import { AppBar, Toolbar, Typography } from "@mui/material";

export default function Navbar() {
  return (
    <AppBar position="sticky" sx={{ zIndex: 1201 }}>
      <Toolbar>
        <Typography variant="h6" sx={{ fontWeight: "bold" }}>
          Cucumber Report Dashboard
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
