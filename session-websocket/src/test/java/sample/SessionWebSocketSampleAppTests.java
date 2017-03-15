package sample;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SessionWebSocketSampleApp.class)
@ContextConfiguration(initializers = SessionWebSocketSampleAppTests.Initializer.class)
public class SessionWebSocketSampleAppTests {

	@ClassRule
	public static GenericContainer redis = new GenericContainer("redis:3.0.7")
			.withExposedPorts(6379);

	@Test
	public void contextLoads() {
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			String host = redis.getContainerIpAddress();
			Integer port = redis.getMappedPort(6379);
			EnvironmentTestUtils.addEnvironment("testcontainers",
					configurableApplicationContext.getEnvironment(),
					"spring.redis.host=" + host, "spring.redis.port=" + port);
		}

	}

}
