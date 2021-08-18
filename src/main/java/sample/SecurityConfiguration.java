package sample;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
class SecurityConfiguration {

	@Configuration(proxyBeanMethods = false)
	static class WebSecurityConfiguration {

		@Bean
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
			return httpSecurity
					.authorizeRequests(requests -> requests.anyRequest().authenticated())
					.formLogin(Customizer.withDefaults())
					.build();
		}

	}

	@Order(99)
	@Configuration(proxyBeanMethods = false)
	static class H2ConsoleSecurityConfiguration {

		@Bean
		SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
			return httpSecurity
					.requestMatcher(PathRequest.toH2Console())
					.authorizeRequests(requests -> requests.anyRequest().authenticated())
					.formLogin(Customizer.withDefaults())
					.csrf(AbstractHttpConfigurer::disable)
					.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
					.build();
		}

	}

}
