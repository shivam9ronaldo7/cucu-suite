import { useState, useMemo } from "react";
import { DataGrid } from "@mui/x-data-grid";

import StatusChip from "../components/common/StatusChip";
import FilterBar from "../components/common/FilterBar";
import ScenarioDetails from "../components/scenarios/ScenarioDetails";

export default function Scenarios() {
  const data = window.CUCUMBER_REPORT_DATA || [];

  const [tag, setTag] = useState("");
  const [status, setStatus] = useState("");
  const [selected, setSelected] = useState(null);

  const allTags = useMemo(() => {
    const set = new Set();
    data.forEach((f) =>
      f.elements.forEach((s) => (s.tags || []).forEach((t) => set.add(t.name))),
    );
    return Array.from(set);
  }, []);

  const rows = useMemo(() => {
    let id = 1,
      list = [];

    data.forEach((f) => {
      f.elements.forEach((s) => {
        const statusVal = s.steps.some((st) => st.result.status === "failed")
          ? "FAILED"
          : "PASSED";

        const tags = (s.tags || []).map((t) => t.name);

        list.push({
          id: id++,
          feature: f.name,
          name: s.name,
          status: statusVal,
          tags,
          raw: s,
        });
      });
    });

    return list
      .filter((r) => !tag || r.tags.includes(tag))
      .filter((r) => !status || r.status === status);
  }, [tag, status]);

  const columns = [
    { field: "feature", headerName: "Feature", width: 200 },
    { field: "name", headerName: "Scenario", width: 250 },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      renderCell: (params) => <StatusChip status={params.value} />,
    },
    {
      field: "tags",
      headerName: "Tags",
      width: 200,
      valueGetter: (p) => p.value.join(", "),
    },
  ];

  return (
    <>
      <FilterBar
        tag={tag}
        setTag={setTag}
        status={status}
        setStatus={setStatus}
        tags={allTags}
      />

      <DataGrid
        rows={rows}
        columns={columns}
        autoHeight
        onRowClick={(p) => setSelected(p.row.raw)}
      />

      <ScenarioDetails
        open={!!selected}
        scenario={selected}
        onClose={() => setSelected(null)}
      />
    </>
  );
}
