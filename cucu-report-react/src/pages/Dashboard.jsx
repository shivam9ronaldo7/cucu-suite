import Layout from "../components/common/Layout";
import { Box } from '@mui/material';
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

export default function Dashboard() {
  const data = window.CUCUMBER_DASHBOARD_DATA || [];
  return (
    <Layout>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Typography variant="h4">Dashboard</Typography>
      </Box>
    </Layout>
  );
}
