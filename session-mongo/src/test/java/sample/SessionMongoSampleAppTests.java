package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.session.data.mongo.AbstractMongoSessionConverter;
import org.springframework.session.data.mongo.MongoOperationsSessionRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionMongoSampleAppTests {

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

}
