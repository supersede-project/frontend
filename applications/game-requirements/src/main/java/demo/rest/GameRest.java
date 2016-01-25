package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.jpa.*;
import demo.model.*;
import eu.supersede.fe.exception.NotFoundException;

@RestController
@RequestMapping("/game")
public class GameRest {

	@Autowired
    private GamesJpa games;
	@Autowired
    private RequirementsJpa requirements;
	@Autowired
    private UsersJpa users;
	@Autowired
    private ValutationCriteriaJpa criterias;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Game> getGames(){
	
		return games.findAll();
	}
	
	@RequestMapping("/{gameId}")
	public Game getUser(@PathVariable Long gameId)
	{
		Game g = games.findOne(gameId);
		if(g == null)
		{
			throw new NotFoundException();
		}
		
		return g;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createGame(@RequestBody Game game)
	{
		//re-attach detached requirements
		List<Requirement> rs = game.getRequirements();
		for(int i = 0; i < rs.size(); i++)
		{
			rs.set(i, requirements.findOne(rs.get(i).getRequirementId()));
		}
		//re-attach detached users
		List<User> us = game.getPlayers();
		for(int i = 0; i < us.size(); i++)
		{
			us.set(i, users.findOne(us.get(i).getUserId()));
		}
		//re-attach detached criterias
		List<ValutationCriteria> cs = game.getCriterias();
		for(int i = 0; i < cs.size(); i++)
		{
			cs.set(i, criterias.findOne(cs.get(i).getCriteriaId()));
		}
	
		game = games.save(game);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(game.getGameId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
}
