package sample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping("/")
	@Transactional(readOnly = true)
	public List<SampleEntity> home() {
		return this.entityManager.createQuery(
				"select se from SampleEntity se", SampleEntity.class)
				.getResultList();
	}

}
