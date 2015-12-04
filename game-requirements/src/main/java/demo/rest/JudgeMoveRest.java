package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.JudgeMovesJpa;
import demo.jpa.UsersJpa;
import demo.model.JudgeMove;

@RestController
@RequestMapping("/judgemove")
public class JudgeMoveRest {

	@Autowired
    private JudgeMovesJpa judgeMoves;
	
	@Autowired
    private UsersJpa users;
	
	// get all the judgeMoves if user is a judge
	//@Secured({"ROLE_JUDGE"})
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<JudgeMove> getJudgeMoves(Authentication authentication) {

		return judgeMoves.findAll();
	}
	
}
