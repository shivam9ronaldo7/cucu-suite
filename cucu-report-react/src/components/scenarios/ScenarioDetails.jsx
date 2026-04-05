import { Drawer, Box, Typography } from "@mui/material";

export default function ScenarioDetails({ open, onClose, scenario }) {
  if (!scenario) return null;

  return (
    <Drawer anchor="right" open={open} onClose={onClose}>
      <Box sx={{ width: 400, p: 2 }}>
        <Typography variant="h6">{scenario.name}</Typography>

        {scenario.steps.map((step, i) => (
          <Typography key={i}>
            Step {i + 1}: {step.result.status}
          </Typography>
        ))}
      </Box>
    </Drawer>
  );
}
