import ReactDOM from "react-dom/client";
import Home from "./pages/Home";
import Scenario from "./pages/Scenario";
import ThemeProviderWrapper from "./theme/ThemeProviderWrapper";
import { readReportData } from "./utils/readReportData";

const root = document.getElementById("root");
const page = root.dataset.page;
const data = readReportData();

const Page = page === "home" ? <Home data={data}/> : <Scenario data={data}/>;

ReactDOM.createRoot(root).render(
  <ThemeProviderWrapper>{Page}</ThemeProviderWrapper>
);
