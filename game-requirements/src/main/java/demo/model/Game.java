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
    private String name; 
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    private int timer;
    private boolean finish;
    
    public Game() {
    }
 
    public Game(String name, int timer) {
        this.name = name;
        this.startTime = new Date();
        this.timer = timer;
        this.finish = false;
    }
 
    public Long getGameId() {
        return gameId;
    }
 
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getStartTime() {
        return startTime;
    }
 
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public int getTimer() {
        return timer;
    }
 
    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    public boolean getFinish() {
        return finish;
    }
 
    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}