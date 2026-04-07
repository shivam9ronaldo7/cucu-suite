import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import { PieChart } from "@mui/x-charts/PieChart";
import { Typography } from "@mui/material";

const data = [
  { label: "Pass", value: 4 },
  { label: "Fail", value: 0 },
];

export default function CucuPieChartWithPaddingAngle() {
  return (
    <Stack width="100%" height="50vh" direction="row" spacing={2}>
      <Box sx={{ width: "50%" }}>
        <Typography variant="h6" align="center" gutterBottom>
          Pie Chart with Padding Angle
        </Typography>
        <PieChart
          sx={{ width: "100%", height: "100%" }}
          series={[
            {
              data,
              innerRadius: 45,
              outerRadius: 140,
              paddingAngle: 1,
              cornerRadius: 5,
              startAngle: 0,
              endAngle: 400,
              arcLabel: "label",
            },
          ]}
        />
      </Box>
      <Box sx={{ width: "50%" }}>
        <Typography variant="h6" align="center" gutterBottom>
          Pie Chart with Padding Angle
        </Typography>
        <PieChart
          sx={{ width: "100%", height: "100%" }}
          series={[
            {
              data,
              innerRadius: 45,
              outerRadius: 140,
              paddingAngle: 1,
              cornerRadius: 5,
              startAngle: 0,
              endAngle: 400,
              arcLabel: "label",
            },
          ]}
        />
      </Box>
    </Stack>
  );
}
