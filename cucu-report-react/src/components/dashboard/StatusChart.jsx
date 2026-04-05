import { PieChart, Pie, Cell } from "recharts";

export default function StatusChart({ summary }) {
  const data = [
    { name: "Passed", value: summary.passed },
    { name: "Failed", value: summary.failed },
  ];

  return (
    <PieChart width={400} height={300}>
      <Pie data={data} dataKey="value" outerRadius={100}>
        <Cell fill="#00b894" />
        <Cell fill="#d63031" />
      </Pie>
    </PieChart>
  );
}
