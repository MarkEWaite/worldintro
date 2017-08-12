package org.jenkinsci.plugins.worldintro;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.FreeStyleProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.FormValidation.CheckMethod;
import java.lang.reflect.Method;

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
    }

    @Test
    public void testDescriptorDoCheckname() throws Exception {
        BuildStepDescriptor<Builder> descriptor = builder.getDescriptor();
        CheckMethod checkMethod = descriptor.getCheckMethod("name");
        assertThat(checkMethod.getDependsOn(), is("")); // name field does not depend on any other field
        Method doCheckNameMethod = descriptor.getClass().getDeclaredMethod("doCheckName", String.class);
        assertThat(doCheckNameMethod.invoke(descriptor, "long name"), is(FormValidation.ok()));
        assertThat(doCheckNameMethod.invoke(descriptor, "x").toString(), is(FormValidation.warning("Isn't the name too short?").toString()));
        assertThat(doCheckNameMethod.invoke(descriptor, "").toString(), is(FormValidation.error("Please set a name").toString()));
        // Appears that FormValidation does not implement equals()
        // assertThat(doCheckNameMethod.invoke(descriptor, "x"), is(FormValidation.warning("Isn't the name too short?")));
    }

    /* Test that the job configuration is unharmed by a round trip through UI */
    @Test
    public void testJobConfigRoundTrip() throws Exception {
        HelloWorldBuilder after = jenkins.configRoundtrip(builder);
        jenkins.assertEqualDataBoundBeans(builder, after);
    }
}
