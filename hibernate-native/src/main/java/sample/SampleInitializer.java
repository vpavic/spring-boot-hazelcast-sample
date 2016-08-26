package sample;

import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SampleInitializer implements CommandLineRunner {

	private SessionFactory sessionFactory;

	public SampleInitializer(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample1"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample2"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample3"));
	}

}
