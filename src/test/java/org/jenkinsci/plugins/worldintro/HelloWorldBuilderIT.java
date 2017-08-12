package org.jenkinsci.plugins.worldintro;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.WithoutJenkins;

public class HelloWorldBuilderIT {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public HelloWorldBuilderIT() {
    }

    private HelloWorldBuilder builder = null;
    private String name = null;

    @Before
    public void setUp() {
        name = "New name";
        builder = new HelloWorldBuilder(name);
    }

    @Test
    @WithoutJenkins
    public void testGetName() {
        assertThat(builder.getName(), is(name));
    }

    @Test
    public void testPerform() {
        Run build = null;
        FilePath workspace = null;
        Launcher launcher = null;
        TaskListener listener = null;
        thrown.expect(NullPointerException.class);
        builder.perform(build, workspace, launcher, listener);
    }

    @Test
    public void testGetDescriptor() {
        assertThat(builder.getDescriptor(), IsInstanceOf.instanceOf(BuildStepDescriptor.class));
    }

}
