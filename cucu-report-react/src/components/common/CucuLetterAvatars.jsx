import Avatar from "@mui/material/Avatar";
import { deepPurple } from "@mui/material/colors";

export default function CucuLetterAvatars({ name }) {
  const initials = name
    ? name
        .split(" ")
        .map((word) => word[0])
        .join("")
        .toUpperCase()
    : "";

  return <Avatar sx={{ bgcolor: deepPurple[500] }}>{initials}</Avatar>;
}
