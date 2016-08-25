package sample;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleAppTests {

	@Autowired
	private ApplicationContext ctx;

	@Test
	public void entityManagerCount() {
		assertThat(this.ctx.getBeansOfType(EntityManagerFactory.class)).hasSize(1);
	}

}
