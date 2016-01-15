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
@Table(name="game_requirements")
public class GameRequirement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameRequirementId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id", nullable = false)
	private Game game;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="requirement_id", nullable = false)
    private Requirement requirement;
	
	public GameRequirement() {    	
	}
	
    public Long getGameRequirementId() {
        return gameRequirementId;
    }
 
    public void setGameRequirementId(Long gameRequirementId) {
        this.gameRequirementId = gameRequirementId;
    }
    
    public Game getGame() {
        return game;
    }
 
    public void setGame(Game game) {
        this.game = game;
    }
    
    public Requirement getRequirement() {
        return requirement;
    }
 
    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}
