import { Chip } from "@mui/material";

export default function StatusChip({ status }) {
  return (
    <Chip label={status} color={status === "FAILED" ? "error" : "success"} />
  );
}
