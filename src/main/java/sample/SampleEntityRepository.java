package sample;

import java.util.List;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

public interface SampleEntityRepository extends CrudRepository<SampleEntity, Long> {

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<SampleEntity> findAll();

}
