import React from "react";
import { Typography } from "@mui/material";

export default function ScenarioPage() {
  const scenarios = window.SCENARIO_DATA || [];
  return (
    <>
      <Typography variant="h4">Scenarios</Typography>
      {scenarios.map((s, i) => (
        <Typography key={i}>{s.scenarioName}</Typography>
      ))}
    </>
  );
}
