package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.ArgumentsJpa;
import demo.model.Argument;

@RestController
@RequestMapping("/argument")
public class ArgumentRest {
	
	@Autowired
    private ArgumentsJpa arguments;
	
	// get all the arguments
	@RequestMapping("")
	public List<Argument> getArguments() {
		return arguments.findAll();
	}
	
	// get number of arguments
	@RequestMapping("/count")
	public Long count() {
		return arguments.count();
	}
}