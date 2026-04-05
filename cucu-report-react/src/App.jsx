import { HashRouter, Routes, Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Scenarios from "./pages/Scenarios";
import Features from "./pages/Features";
import Tags from "./pages/Tags";

export default function App() {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/scenarios" element={<Scenarios />} />
        <Route path="/features" element={<Features />} />
        <Route path="/tags" element={<Tags />} />
      </Routes>
    </HashRouter>
  );
}
