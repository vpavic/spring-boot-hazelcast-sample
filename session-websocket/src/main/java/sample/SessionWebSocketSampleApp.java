package sample;

import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SpringBootApplication
@Controller
public class SessionWebSocketSampleApp {

	@Autowired
	private SessionRepository<? extends Session> sessionRepository;

	public static void main(String[] args) {
		SpringApplication.run(SessionWebSocketSampleApp.class, args);
	}

	@GetMapping("/")
	public String home(HttpSession session) {
		return "index";
	}

	@MessageMapping("/messages")
	@SendTo("/topic/echo")
	public Echo echo(String message, @Header("simpSessionAttributes") Map<String, Object> attributes) {
		String sessionId = (String) attributes.get("SPRING.SESSION.ID");
		Session session = this.sessionRepository.findById(sessionId);
		if (session == null) {
			throw new IllegalStateException("No session available");
		}
		return new Echo(message, session);
	}

	@MessageExceptionHandler
	@SendTo("/topic/errors")
	public String handleError(Exception e) {
		return e.getMessage();
	}

	@Configuration
	@EnableWebSocketMessageBroker
	static class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

		@Override
		protected void configureStompEndpoints(StompEndpointRegistry registry) {
			registry.addEndpoint("/messages").withSockJS();
		}

		@Override
		public void configureMessageBroker(MessageBrokerRegistry registry) {
			registry.enableSimpleBroker("/topic");
			registry.setApplicationDestinationPrefixes("/app");
		}

	}

	static class Echo {

		private String message;

		private String sessionId;

		private Instant sessionCreationTime;

		private Instant sessionLastAccessedTime;

		Echo(String message, Session session) {
			this.message = message;
			this.sessionId = session.getId();
			this.sessionCreationTime = session.getCreationTime();
			this.sessionLastAccessedTime = session.getLastAccessedTime();
		}

		public String getMessage() {
			return this.message;
		}

		public String getSessionId() {
			return this.sessionId;
		}

		public Instant getSessionCreationTime() {
			return this.sessionCreationTime;
		}

		public Instant getSessionLastAccessedTime() {
			return this.sessionLastAccessedTime;
		}

	}

}
