import { useState } from "react";
import TagFilter from "../components/TagFilter";

export default function Home({ data }) {
  const [tag, setTag] = useState(null);
  const list = data.scenarios.filter(s => !tag || s.tags.includes(tag));

  return (
    <>
      <h2>Dashboard</h2>
      <TagFilter tags={data.availableTags} selected={tag} onSelect={setTag} />
      <ul>
        {list.map(s => (
          <li key={s.name}><a href={s.html}>{s.name}</a></li>
        ))}
      </ul>
    </>
  );
}
