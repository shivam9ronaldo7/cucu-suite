import { HashRouter, Routes, Route } from "react-router-dom";

import CucuDashboard from "./pages/CucuDashboard";

export default function App() {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<CucuDashboard />} />
        <Route path="/dashboard" element={<CucuDashboard />} />
      </Routes>
    </HashRouter>
  );
}
