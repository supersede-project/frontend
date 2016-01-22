package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.JudgeActsJpa;
import demo.model.JudgeAct;

@RestController
@RequestMapping("/judgeact")
public class JudgeActRest {

	@Autowired
    private JudgeActsJpa judgeActs;
	
	// get all the judgeActs if user is a judge
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<JudgeAct> getJudgeActs() {

		return judgeActs.findAll();
	}
}
