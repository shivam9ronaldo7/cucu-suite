package com.techstacklearning.cucu.report.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CucuMojoTest {

    private void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void execute_skipsWhenDisabled() throws Exception {
        CucuMojo mojo = new CucuMojo();
        setField(mojo, "enabled", false);

        try (MockedConstruction<AnnotationConfigApplicationContext> mocked =
                     Mockito.mockConstruction(AnnotationConfigApplicationContext.class)) {

            mojo.execute();

            // no context should be constructed when disabled
            assertTrue(mocked.constructed().isEmpty(), "Context should not be constructed when disabled");
        }
    }

    @Test
    void execute_invokesCucuRunner() throws Exception {
        CucuMojo mojo = new CucuMojo();
        setField(mojo, "enabled", true);
        setField(mojo, "cucumberNdjsonReportDir", "target/cucumber");
        setField(mojo, "project", mock(MavenProject.class));
        setField(mojo, "session", mock(MavenSession.class));
        mojo.execute();
    }

}
