package sample;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.session.data.mongo.AbstractMongoSessionConverter;
import org.springframework.session.data.mongo.MongoOperationsSessionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SessionMongoSampleApp.class)
@ContextConfiguration(initializers = SessionMongoSampleAppTests.Initializer.class)
public class SessionMongoSampleAppTests {

	@ClassRule
	public static GenericContainer mongo = new GenericContainer("mongo:3.4.2")
			.withExposedPorts(27017);

	@Autowired
	private MongoOperationsSessionRepository sessionRepository;

	@Autowired
	private AbstractMongoSessionConverter mongoSessionConverter;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sessionRepositoryUsesConfiguredSessionMongoConverter() {
		assertThat(ReflectionTestUtils.getField(this.sessionRepository, "mongoSessionConverter"))
				.isEqualTo(this.mongoSessionConverter);
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			String host = mongo.getContainerIpAddress();
			Integer port = mongo.getMappedPort(27017);
			EnvironmentTestUtils.addEnvironment("testcontainers",
					configurableApplicationContext.getEnvironment(),
					"spring.data.mongodb.uri=mongodb://" + host + ":" + port + "/test");
		}

	}

}
