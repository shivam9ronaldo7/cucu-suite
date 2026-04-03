import React from "react";
import { useParams } from "react-router-dom";
import { Typography } from "@mui/material";

export default function ScenarioDetailPage() {
  const { id } = useParams();
  const scenario = (window.SCENARIO_DATA || [])[id];

  if (!scenario) return <Typography>Not found</Typography>;

  return (
    <>
      <Typography variant="h4">{scenario.scenarioName}</Typography>
      {scenario.steps.map((s, i) => (
        <Typography key={i}>• {s.stepText}</Typography>
      ))}
    </>
  );
}
