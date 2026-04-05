import { useMemo } from "react";
import SummaryCards from "../components/dashboard/SummaryCards";
import StatusChart from "../components/dashboard/StatusChart";

export default function Dashboard() {
  const summary = useMemo(() => {
    const data = window.CUCUMBER_REPORT_DATA || [];
    let total = 0,
      passed = 0,
      failed = 0;

    data.forEach((f) =>
      f.elements.forEach((s) => {
        total++;
        s.steps.some((st) => st.result.status === "failed")
          ? failed++
          : passed++;
      }),
    );

    return { total, passed, failed };
  }, []);

  return (
    <>
      <SummaryCards summary={summary} />
      <StatusChart summary={summary} />
    </>
  );
}
