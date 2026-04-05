import { DataGrid } from "@mui/x-data-grid";

export default function Features() {
  const data = window.CUCUMBER_REPORT_DATA || [];

  if (!data.length) return <div>No Data Available</div>;

  const rows = data.map((feature, index) => {
    const total = feature.elements.length;

    const failed = feature.elements.filter((s) =>
      s.steps.some((st) => st.result.status === "failed"),
    ).length;

    return {
      id: index + 1,
      feature: feature.name,
      total,
      passed: total - failed,
      failed,
    };
  });

  const columns = [
    { field: "feature", headerName: "Feature", width: 250 },
    { field: "total", headerName: "Total", width: 120 },
    { field: "passed", headerName: "Passed", width: 120 },
    { field: "failed", headerName: "Failed", width: 120 },
  ];

  return (
    <div>
      <h2>Features Overview</h2>
      <DataGrid rows={rows} columns={columns} autoHeight />
    </div>
  );
}
