package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.StaticResourceRequest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class HazelcastUseCasesApplication implements CommandLineRunner {

	@Autowired
	private SampleEntityRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(HazelcastUseCasesApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) {
		this.repository.save(new SampleEntity("sample1"));
		this.repository.save(new SampleEntity("sample2"));
		this.repository.save(new SampleEntity("sample3"));
	}

	@GetMapping(path = "/")
	public Iterable<SampleEntity> home() {
		return this.repository.findAll();
	}

	@Configuration
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Bean
		public UserDetailsService userDetailsService() {
			return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder().username("user")
					.password("password").roles("USER").build());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.requestMatchers(StaticResourceRequest.toCommonLocations()).permitAll()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.permitAll();
			// @formatter:on
		}

	}

}
