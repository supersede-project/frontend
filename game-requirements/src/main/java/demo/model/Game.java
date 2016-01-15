package demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="games")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
	 
	public Game() {    	
	}
	
    public Long getGameId() {
        return gameId;
    }
 
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    
    public Date getStartTime() {
        return startTime;
    }
 
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }	
}
