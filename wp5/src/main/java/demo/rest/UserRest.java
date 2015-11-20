package demo.rest;

import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRest {

	@RequestMapping("")
	public Principal user(Principal user) {
		return user;
	}
}
