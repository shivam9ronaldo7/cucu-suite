# cucu-report Maven Plugin

Spring Context based Maven plugin for processing Cucumber NDJSON reports.

## Architecture

Maven -> Mojo -> Spring Context -> NDJSON Parser -> Metrics

## Features
- Stream-based NDJSON parsing
- CI safe
- No Spring Boot
- Debug logging

## Usage
Bind to any phase and provide ndjson file.

## HTML Report

Generates a static HTML report using Chart.js.

### Includes
- Pass/Fail percentage bar chart
- Tag-wise failure analytics

Output file:
target/cucumber-report.html
