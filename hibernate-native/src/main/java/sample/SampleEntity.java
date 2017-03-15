package sample;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SampleEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public SampleEntity(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	private SampleEntity() {
		// for Hibernate
	}

}
