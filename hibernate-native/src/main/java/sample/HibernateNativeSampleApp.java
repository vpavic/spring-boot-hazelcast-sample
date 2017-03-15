package sample;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@RestController
public class HibernateNativeSampleApp implements CommandLineRunner {

	@Autowired
	private SessionFactory sessionFactory;

	public static void main(String[] args) {
		SpringApplication.run(HibernateNativeSampleApp.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample1"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample2"));
		this.sessionFactory.getCurrentSession().save(new SampleEntity("sample3"));
	}

	@GetMapping(path = "/")
	@Transactional(readOnly = true)
	public List<SampleEntity> home() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select se from SampleEntity se", SampleEntity.class)
				.list();
	}

	@Configuration
	static class HibernateConfig {

		@Bean
		public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
			LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
			sessionFactoryBean.setDataSource(dataSource);
			sessionFactoryBean.setPackagesToScan(HibernateNativeSampleApp.class.getPackage().getName());
			return sessionFactoryBean;
		}

		@Bean
		public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
			return new HibernateTransactionManager(sessionFactory);
		}

	}

}
