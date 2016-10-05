package sample;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SampleInitializer implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		this.entityManager.persist(new SampleEntity("sample1"));
		this.entityManager.persist(new SampleEntity("sample2"));
		this.entityManager.persist(new SampleEntity("sample3"));
	}

}
