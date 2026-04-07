import React from "react";
import {
  Box,
  Drawer as MuiDrawer,
  AppBar as MuiAppBar,
  CssBaseline,
  Toolbar,
  List,
  Typography,
  Divider,
  IconButton,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import MenuIcon from "@mui/icons-material/Menu";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import CucuSidebarListItem from "./CucuSidebarListItem";

const drawerWidth = 240;

const openedMixin = {
  width: drawerWidth,
  transition: 'width 225ms cubic-bezier(0.4, 0, 0.6, 1) 0ms',
  overflowX: 'hidden',
};

const closedMixin = {
  transition: 'width 195ms cubic-bezier(0.4, 0, 0.6, 1) 0ms',
  overflowX: 'hidden',
  width: 'calc(56px + 1px)',
  '@media (min-width: 600px)': {
    width: 'calc(64px + 1px)',
  },
};

const DrawerHeader = styled('div')({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  padding: '0 8px',
  minHeight: '64px',
});

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})({
  zIndex: 1201,
  transition: 'width 195ms cubic-bezier(0.4, 0, 0.6, 1) 0ms, margin 195ms cubic-bezier(0.4, 0, 0.6, 1) 0ms',
  variants: [
    {
      props: ({ open }) => open,
      style: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: 'width 225ms cubic-bezier(0.4, 0, 0.6, 1) 0ms, margin 225ms cubic-bezier(0.4, 0, 0.6, 1) 0ms',
      },
    },
  ],
});

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })({
  width: drawerWidth,
  flexShrink: 0,
  whiteSpace: 'nowrap',
  boxSizing: 'border-box',
  variants: [
    {
      props: ({ open }) => open,
      style: {
        ...openedMixin,
        '& .MuiDrawer-paper': openedMixin,
      },
    },
    {
      props: ({ open }) => !open,
      style: {
        ...closedMixin,
        '& .MuiDrawer-paper': closedMixin,
      },
    },
  ],
});

export default function CucuLayout({ children }) {
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar position="fixed" open={open}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={[
              {
                marginRight: 5,
              },
              open && { display: "none" },
            ]}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Cucu Report
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer variant="permanent" open={open}>
        <DrawerHeader>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List>
          <CucuSidebarListItem name="Dashboard" />
          <CucuSidebarListItem name="Features" />
          <CucuSidebarListItem name="Scenarios" />
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <DrawerHeader />
        {children}
      </Box>
    </Box>
  );
}
