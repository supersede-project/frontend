package demo.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.jpa.CriteriasMatricesDataJpa;
import demo.jpa.GamesJpa;
import demo.jpa.JudgeActsJpa;
import demo.jpa.PlayerMovesJpa;
import demo.jpa.RequirementsJpa;
import demo.jpa.RequirementsMatricesDataJpa;
import demo.jpa.UsersJpa;
import demo.jpa.ValutationCriteriaJpa;
import demo.model.CriteriasMatrixData;
import demo.model.Game;
import demo.model.JudgeAct;
import demo.model.PlayerMove;
import demo.model.Requirement;
import demo.model.RequirementsMatrixData;
import demo.model.User;
import demo.model.ValutationCriteria;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.security.DatabaseUser;

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
	@Autowired
    private CriteriasMatricesDataJpa criteriasMatricesData;
	@Autowired
    private RequirementsMatricesDataJpa requirementsMatricesData;
	@Autowired
    private PlayerMovesJpa playerMoves;
	@Autowired
    private JudgeActsJpa judgeActs;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Game> getGames(){
	
		return games.findAll();
	}
	
	@RequestMapping("/{gameId}")
	public Game getGame(@PathVariable Long gameId)
	{
		Game g = games.findOne(gameId);
		if(g == null)
		{
			throw new NotFoundException();
		}
		
		return g;
	}
	
	@RequestMapping(value = "/end/{gameId}", method = RequestMethod.PUT)
	public void setGameFinished(@PathVariable Long gameId)
	{
		Game g = games.findOne(gameId);
		g.setFinished(true);
		games.save(g);
		
		// set all judgeActs voted and playerMoves played
		List<RequirementsMatrixData> rmdList =  requirementsMatricesData.findByGame(g);
		for(int i = 0; i < rmdList.size(); i++)
		{
			RequirementsMatrixData rmd = rmdList.get(i);
			List<JudgeAct> jaList = judgeActs.findByRequirementsMatrixData(rmd);
			List<PlayerMove> pmList = playerMoves.findByRequirementsMatrixData(rmd);
			
			for(int j = 0; j < jaList.size(); j++)
			{
				jaList.get(j).setVoted(true);
				judgeActs.save(jaList.get(j));
			}
			
			for(int k = 0; k < pmList.size(); k++)
			{
				pmList.get(k).setPlayed(true);
				playerMoves.save(pmList.get(k));
			}
			
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createGame(Authentication auth, @RequestBody Game game,
			@RequestParam(required = true) String criteriaValues) throws JsonParseException, JsonMappingException, IOException
	{
		TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, Integer>> cvs = mapper.readValue(criteriaValues, typeRef);
		game.setStartTime(new Date());
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
	
		Object user = auth.getPrincipal();
		
		if(user instanceof DatabaseUser)
		{
			DatabaseUser dbUser = (DatabaseUser)user;
			User u = users.getOne(dbUser.getUserId());
			game.setCreator(u);
		}
		game.setFinished(false);
		game = games.save(game);
		
		for(int i = 0; i < cs.size() - 1; i++)
		{
			for(int j = i + 1; j < cs.size(); j++)
			{
				CriteriasMatrixData cmd = new CriteriasMatrixData();
				cmd.setGame(game);
				cmd.setRowCriteria(cs.get(j));
				cmd.setColumnCriteria(cs.get(i));
				String c1Id = cs.get(j).getCriteriaId().toString();
				String c2Id = cs.get(i).getCriteriaId().toString();
				
				if(cvs.containsKey(c1Id) && cvs.get(c1Id).containsKey(c2Id))
				{
					cmd.setValue(new Long(cvs.get(c1Id).get(c2Id)));
				}
				else
				{
					cmd.setValue(new Long(cvs.get(c2Id).get(c1Id)));
				}
				
				criteriasMatricesData.save(cmd);
			}
		}
		
		for(int c = 0; c < cs.size(); c++)
		{
			for(int i = 0; i < rs.size() - 1; i++)
			{
				for(int j = i + 1; j < rs.size(); j++)
				{
					RequirementsMatrixData rmd = new RequirementsMatrixData();
					rmd.setGame(game);
					rmd.setRowRequirement(rs.get(j));
					rmd.setColumnRequirement(rs.get(i));
					rmd.setCriteria(cs.get(c));
					rmd.setValue(-1l);
					requirementsMatricesData.save(rmd);
					
					for(int p = 0; p < us.size(); p++)
					{
						PlayerMove pm = new PlayerMove();
						pm.setPlayer(us.get(p));
						pm.setRequirementsMatrixData(rmd);
						pm.setPlayed(false);
						playerMoves.save(pm);
					}
					
					JudgeAct ja = new JudgeAct();
					ja.setVoted(false);
					ja.setRequirementsMatrixData(rmd);
					judgeActs.save(ja);
				}
			}
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(game.getGameId()).toUri());
		return new ResponseEntity<>(game.getGameId(), httpHeaders, HttpStatus.CREATED);
	}
}
