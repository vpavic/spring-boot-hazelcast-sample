package sample;

import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class SampleApp implements CommandLineRunner {

	private SessionFactory sessionFactory;

	public SampleApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleApp.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample1"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample2"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample3"));
	}

}
