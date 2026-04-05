import { Box, TextField, MenuItem } from "@mui/material";

export default function FilterBar({ tag, setTag, status, setStatus, tags }) {
  return (
    <Box sx={{ display: "flex", gap: 2, mb: 2 }}>
      <TextField
        select
        label="Tag"
        value={tag}
        onChange={(e) => setTag(e.target.value)}
      >
        <MenuItem value="">All</MenuItem>
        {tags.map((t) => (
          <MenuItem key={t} value={t}>
            {t}
          </MenuItem>
        ))}
      </TextField>

      <TextField
        select
        label="Status"
        value={status}
        onChange={(e) => setStatus(e.target.value)}
      >
        <MenuItem value="">All</MenuItem>
        <MenuItem value="PASSED">PASSED</MenuItem>
        <MenuItem value="FAILED">FAILED</MenuItem>
      </TextField>
    </Box>
  );
}
