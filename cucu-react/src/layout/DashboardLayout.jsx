import React from "react";
import { Outlet, Link } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Button,
  Box,
  Stack,
} from "@mui/material";
import Switch from "@mui/material/Switch";
import { ColorModeContext } from "../theme/DashboardTheme";

const drawerWidth = 260;

export default function DashboardLayout() {
  const colorMode = React.useContext(ColorModeContext);
  const scenarios = window.SCENARIO_DATA || [];

  const folderMap = {};
  scenarios.forEach((s) => {
    const folder = (s.featureFilePath || "root").split("/").slice(-2, -1)[0];
    if (!folderMap[folder]) folderMap[folder] = [];
    folderMap[folder].push(s.featureName);
  });

  return (
    <Box sx={{ display: "flex" }}>
      <AppBar
        position="fixed"
        sx={{
          width: `calc(100% - ${drawerWidth}px)`,
          ml: `${drawerWidth}px`,
        }}
      >
        <Toolbar>
          <Typography variant="h6" sx={{ mr: 3 }}>
            BDD Dashboard
          </Typography>

          <Stack direction="row" spacing={2}>
            <Button color="inherit" component={Link} to="/">
              Home
            </Button>
            <Button color="inherit" component={Link} to="/scenarios">
              Scenarios
            </Button>
            <Button color="inherit" component={Link} to="/features">
              Features
            </Button>
            <Button color="inherit" component={Link} to="/tags">
              Tags
            </Button>
            <Button color="inherit" component={Link} to="/analytics">
              Analytics
            </Button>
          </Stack>
          <Switch color="default" onChange={colorMode.toggleColorMode} />
        </Toolbar>
      </AppBar>

      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          "& .MuiDrawer-paper": { width: drawerWidth },
        }}
      >
        <Toolbar />
        <List>
          {Object.entries(folderMap).map(([folder, features]) => (
            <React.Fragment key={folder}>
              <ListItem>
                <ListItemText primary={folder} />
              </ListItem>
              {features.map((f, i) => (
                <ListItem key={i} sx={{ pl: 4 }}>
                  <ListItemText primary={f} />
                </ListItem>
              ))}
            </React.Fragment>
          ))}
        </List>
      </Drawer>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          ml: `${drawerWidth}px`,
        }}
      >
        <Toolbar />
        <div>
          <Outlet />
        </div>
      </Box>
    </Box>
  );
}
