package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
@RestController
public class HazelcastDemoApplication implements CommandLineRunner {

	@Autowired
	private SampleEntityRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(HazelcastDemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) {
		this.repository.save(new SampleEntity("sample1"));
		this.repository.save(new SampleEntity("sample2"));
		this.repository.save(new SampleEntity("sample3"));
	}

	@GetMapping(path = "/")
	Iterable<SampleEntity> home() {
		return this.repository.findAll();
	}

	@GetMapping(path = "/{id}")
	SampleEntity entity(@PathVariable Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}
