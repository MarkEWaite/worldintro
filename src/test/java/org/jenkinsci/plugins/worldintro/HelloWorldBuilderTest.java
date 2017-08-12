package org.jenkinsci.plugins.worldintro;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;

import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class HelloWorldBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public HelloWorldBuilderTest() {
    }

    private HelloWorldBuilder builder = null;
    private String name = null;

    @Before
    public void setUp() {
        name = "Something";
        builder = new HelloWorldBuilder(name);
    }

    @Test
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
        thrown.expect(NullPointerException.class);
        assertThat(builder.getDescriptor(), IsInstanceOf.instanceOf(BuildStepDescriptor.class));
    }

}
