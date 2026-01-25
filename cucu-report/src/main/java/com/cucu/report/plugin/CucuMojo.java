package com.cucu.report.plugin;

import lombok.SneakyThrows;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.Arrays;

@Mojo(
        name = "execute",
        defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST,
        requiresProject = true,
        threadSafe = true
)
public class CucuMojo extends AbstractMojo {

    @Parameter(property = "enabled", defaultValue = "true")
    private boolean enabled;

    @Parameter(property = "cucumber-ndjson-report-dir", defaultValue = "${project.build.directory}/target/cucumber")
    private String cucumberNdjsonReportDir;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @SneakyThrows
    @Override
    public void execute() {
        Log mavenLog = getLog();
        mavenLog.info("Starting CucuMojo execution...");
        mavenLog.info("CucuMojo enabled: " + enabled);
        mavenLog.info("Cucumber ndjson report directory: " + cucumberNdjsonReportDir);

        if (!enabled) {
            mavenLog.info("Cucu report is disabled. Hence skipping CucuRunner execution.");
            return;
        }
        if (cucumberNdjsonReportDir == null || cucumberNdjsonReportDir.trim().isEmpty()) {
            throw new MojoFailureException("Parameter 'cucumber-ndjson-report-dir' is not set or empty.");
        }
        File reportDir = new File(cucumberNdjsonReportDir);
        if (!reportDir.exists()) {
            throw new MojoFailureException("Configured path '" + cucumberNdjsonReportDir + "' does not exist.");
        }
        if (!reportDir.isDirectory()) {
            throw new MojoFailureException("Configured path '" + cucumberNdjsonReportDir + "' is not a directory.");
        }

        mavenLog.info("Cucumber ndjson report absolute directory: " + reportDir.getAbsolutePath());

        File[] ndjsonArr = reportDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".ndjson"));
        if (ndjsonArr == null || ndjsonArr.length == 0) {
            throw new MojoFailureException("No .ndjson files found in directory: " + reportDir.getAbsolutePath());
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.cucu.report.plugin");
        context.getBeanFactory().registerSingleton("cucumberNdjsonReportDir", cucumberNdjsonReportDir);
        context.getBeanFactory().registerSingleton("mavenProject", project);
        context.getBeanFactory().registerSingleton("mavenSession", session);
        context.getBeanFactory().registerSingleton("mavenLog", mavenLog);
        context.refresh();
        context.getBean(CucuTransformer.class).transform(ndjsonArr);
        context.close();
        mavenLog.info("CucuMojo execution completed.");
    }

}
