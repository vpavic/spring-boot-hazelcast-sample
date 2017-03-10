package sample;

import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.mongo.AbstractMongoSessionConverter;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SessionMongoSampleApp {

	public static void main(String[] args) {
		SpringApplication.run(SessionMongoSampleApp.class, args);
	}

	@GetMapping(path = "/")
	public String home(HttpSession session) {
		return session.getId();
	}

	@Bean
	public AbstractMongoSessionConverter sessionMongoConverter() {
		return new JdkMongoSessionConverter();
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	static class SecurityConfig extends WebSecurityConfigurerAdapter {
	}

}
