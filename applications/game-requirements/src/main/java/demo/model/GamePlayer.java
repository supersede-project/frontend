package demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="game_players")
public class GamePlayer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long gamePlayerId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id", nullable = false)
	private Game game;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="player_id", nullable = false)
    private User player;
	
	public GamePlayer(){
		
	}
	
    public Long getGamePlayerId() {
        return gamePlayerId;
    }
 
    public void setGamePlayerId(Long gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }
    
    public Game getGame() {
        return game;
    }
 
    public void setGame(Game game) {
        this.game = game;
    }
    
    public User getPlayer() {
        return player;
    }
 
    public void setPlayer(User player) {
        this.player = player;
    }
}
