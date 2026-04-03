import React from "react";
import { HashRouter, Routes, Route } from "react-router-dom";

import DashboardTheme from "./theme/DashboardTheme";
import DashboardLayout from "./layout/DashboardLayout";

import HomePage from "./pages/HomePage";
import ScenarioPage from "./pages/ScenarioPage";
import FeaturePage from "./pages/FeaturePage";
import TagsPage from "./pages/TagsPage";
import AnalyticsProPage from "./pages/AnalyticsProPage";
import ScenarioDetailPage from "./pages/ScenarioDetailPage";

export default function App() {
  const scenarios = window.SCENARIO_DATA || [];
  const featureMap = {};
  const tagMap = {};

  for (var i = 0; i < scenarios.length; i++) {
    var s = scenarios[i];
    var fName = s.featureName;
    if (!featureMap[fName]) {
      featureMap[fName] = {
        name: s.featureName,
        description: s.featureDescription,
        path: s.featureFilePath,
        startTime: s.startTime,
        endTime: s.endTime,
        totalExecutionTime: 0,
        totalPass: 0,
        totalFail: 0,
      };
    }
    var f = featureMap[fName];
    if (s.startTime < f.startTime) {
      f.startTime = s.startTime;
    }
    if (s.endTime > f.endTime) {
      f.endTime = s.endTime;
    }
    if (s.status === "Pass") {
      f.totalPass++;
    } else {
      f.totalFail++;
    }
    var start = new Date(s.startTime.replace(" ", "T"));
    var end = new Date(s.endTime.replace(" ", "T"));
    f.totalExecutionTime += end - start;
    var tags = s.scenarioTags || [];
    for (var j = 0; j < tags.length; j++) {
      var tag = tags[j];
      if (!tagMap[tag]) {
        tagMap[tag] = {
          name: tag,
          startTime: s.startTime,
          endTime: s.endTime,
          totalExecutionTime: 0,
          totalPass: 0,
          totalFail: 0,
        };
      }
      var t = tagMap[tag];
      if (s.startTime < t.startTime) {
        t.startTime = s.startTime;
      }
      if (s.endTime > t.endTime) {
        t.endTime = s.endTime;
      }
      if (s.status === "Pass") {
        t.totalPass++;
      } else {
        t.totalFail++;
      }
      t.totalExecutionTime += end - start;
    }
  }
  // console.log(featureMap);
  // console.log(tagMap);
  return (
    <DashboardTheme>
      <HashRouter>
        <Routes>
          <Route path="/" element={<DashboardLayout />}>
            <Route index element={<HomePage />} />
            <Route path="scenarios" element={<ScenarioPage />} />
            <Route path="features" element={<FeaturePage />} />
            <Route path="tags" element={<TagsPage />} />
            <Route path="analytics" element={<AnalyticsProPage />} />
            <Route path="scenario/:id" element={<ScenarioDetailPage />} />
          </Route>
        </Routes>
      </HashRouter>
    </DashboardTheme>
  );
}
