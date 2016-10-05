package sample;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	private SessionFactory sessionFactory;

	public SampleController(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@RequestMapping("/")
	@Transactional(readOnly = true)
	public List<SampleEntity> home() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select se from SampleEntity se", SampleEntity.class)
				.list();
	}

}
