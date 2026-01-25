import { PieChart } from "@mui/x-charts/PieChart";

export default function Scenario({ data }) {
  const passed = data.steps.filter(s => s.status === "PASSED").length;
  const failed = data.steps.length - passed;

  return (
    <>
      <h2>{data.scenario}</h2>
      <PieChart
        height={220}
        series={[{
          data: [
            { label: "Passed", value: passed },
            { label: "Failed", value: failed }
          ]
        }]}
      />
      <ul>
        {data.steps.map((s,i) => (
          <li key={i} style={{ color: s.time > 2000 ? "red" : "inherit" }}>
            {s.text} - {s.time}ms
          </li>
        ))}
      </ul>
    </>
  );
}
