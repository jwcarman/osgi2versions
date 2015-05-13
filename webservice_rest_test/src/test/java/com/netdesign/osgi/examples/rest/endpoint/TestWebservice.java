package com.netdesign.osgi.examples.rest.endpoint;

import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ConfigurationManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
@RunWith(PaxExam.class)
public class TestWebservice {

	@Inject
	private BundleContext bundleContext;

	@Configuration
	public Option[] config() {
		MavenArtifactUrlReference karafUrl = maven()
				.groupId("org.apache.karaf").artifactId("apache-karaf")
				.version(karafVersion()).type("zip");
		MavenUrlReference karafStandardRepo = maven()
				.groupId("org.apache.karaf.features").artifactId("standard")
				.version(karafVersion()).classifier("features").type("xml");
		MavenUrlReference karafCXFRepo = maven()
				.groupId("org.apache.cxf.karaf").artifactId("apache-cxf")
				.version(karafVersion()).classifier("features").type("xml");

		return new Option[] {
				karafDistributionConfiguration().frameworkUrl(karafUrl)
						.unpackDirectory(new File("target", "exam"))
						.useDeployFolder(false),
						 new LogLevelOption(LogLevel.DEBUG),
				configureConsole().ignoreLocalConsole(),
				features(karafStandardRepo, "scr"),
				features(karafCXFRepo, "cxf"),
				features(karafStandardRepo, "webconsole"),

				mavenBundle("com.netdesign.osgi.examples.rest",
						"rest", "1.0-SNAPSHOT"),
				junitBundles(), };
	}


	public static String karafVersion() {
		ConfigurationManager cm = new ConfigurationManager();
		String karafVersion = cm.getProperty("pax.exam.karaf.version", "3.0.3");
		return karafVersion;
	}

	@Test
	public void getHelloService() throws IOException {
		System.out.println("ready and waitning");
		System.in.read();
	}
}
