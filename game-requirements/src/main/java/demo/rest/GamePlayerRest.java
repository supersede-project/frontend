package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.GamePlayersJpa;
import demo.model.GamePlayer;

@RestController
@RequestMapping("/gameplayer")
public class GamePlayerRest {

	@Autowired
    private GamePlayersJpa gamePlayers;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<GamePlayer> getGamePlayers(){
	
		return gamePlayers.findAll();
	}
}
