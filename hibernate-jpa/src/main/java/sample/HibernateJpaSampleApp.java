package sample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HibernateJpaSampleApp implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(HibernateJpaSampleApp.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		this.entityManager.persist(new SampleEntity("sample1"));
		this.entityManager.persist(new SampleEntity("sample2"));
		this.entityManager.persist(new SampleEntity("sample3"));
	}

	@GetMapping(path = "/")
	@Transactional(readOnly = true)
	public List<SampleEntity> home() {
		return this.entityManager.createQuery(
				"select se from SampleEntity se", SampleEntity.class)
				.getResultList();
	}

}
