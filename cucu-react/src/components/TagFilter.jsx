import { Chip, Stack } from "@mui/material";

export default function TagFilter({ tags, selected, onSelect }) {
  return (
    <Stack direction="row" spacing={1}>
      {tags.map(t => (
        <Chip
          key={t}
          label={t}
          color={selected === t ? "primary" : "default"}
          onClick={() => onSelect(t)}
        />
      ))}
    </Stack>
  );
}
