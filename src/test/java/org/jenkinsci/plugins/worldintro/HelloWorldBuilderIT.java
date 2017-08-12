package org.jenkinsci.plugins.worldintro;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.FreeStyleProject;
import hudson.model.Project;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
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

    @Test
    public void testDescriptorGetDisplayName() {
        BuildStepDescriptor<Builder> descriptor = builder.getDescriptor();
        assertThat(descriptor.getDisplayName(), is("Say hello world"));
    }

    @Test
    public void testDescriptorIsApplicable() {
        BuildStepDescriptor<Builder> descriptor = builder.getDescriptor();
        assertThat(descriptor.isApplicable(FreeStyleProject.class), is(true));
        assertThat(descriptor.isApplicable(Project.class), is(true));
    }
}
