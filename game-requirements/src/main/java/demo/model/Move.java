package demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="moves")
public class Move {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long moveId;
    private String name; 
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    private int timer;
    private boolean finish;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="requirement_id", insertable=false, updatable=false)
    private Requirement firstRequirement;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="requirement_id", insertable=false, updatable=false)
    private Requirement secondRequirement;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User firstPlayer;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",insertable=false, updatable=false)
    private User secondPlayer;
    
    // TODO, finish to implement and check
    //private Requirement firstPlayerRequirement;
    //private Requirement secondPlayerRequirement;
    
    
    public Move() {
    }
 
    public Move(String name, int timer, Requirement firstRequirement, Requirement secondRequirement, User firstPlayer, User secondPlayer) {
        this.name = name;
        this.startTime = new Date();
        this.timer = timer;
        this.finish = false;
        this.firstRequirement = firstRequirement;
        this.secondRequirement = secondRequirement;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
 
    public Long getMoveId() {
        return moveId;
    }
 
    public void setMoveId(Long moveId) {
        this.moveId = moveId;
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
      
    public Requirement getFirstRequirement() {
        return firstRequirement;
    }
 
    public void setFirstRequirement(Requirement firstRequirement) {
        this.firstRequirement = firstRequirement;
    }
    
    public Requirement getSecondRequirement() {
        return secondRequirement;
    }
 
    public void setSecondRequirement(Requirement secondRequirement) {
        this.secondRequirement = secondRequirement;
    } 
    
    public User getFirstPlayer() {
        return firstPlayer;
    }
 
    public void setFirstPlayer(User firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    
    public User getSecondPlayer() {
        return secondPlayer;
    }
 
    public void setSecondPlayer(User secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}