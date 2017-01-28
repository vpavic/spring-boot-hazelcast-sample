package sample;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping(path = "/")
	public String home(HttpSession session) {
		return session.getId();
	}

}
