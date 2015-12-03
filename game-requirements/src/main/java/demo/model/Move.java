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
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="first_requirement_id", nullable = false)
    private Requirement firstRequirement;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="second_requirement_id", nullable = false)
    private Requirement secondRequirement;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="first_player_id", nullable = false)
    private User firstPlayer;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="second_player_id", nullable = false)
    private User secondPlayer;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="criteria_id", nullable = false)
    private ValutationCriteria criteria;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="first_player_requirement")
    private Requirement firstPlayerChooseRequirement;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="second_player_requirement")
    private Requirement secondPlayerChooseRequirement;
    
    private boolean notificationSent;
        
    public Move() {
    }
 
    public Move(String name, int timer, Requirement firstRequirement, Requirement secondRequirement, User firstPlayer, User secondPlayer, ValutationCriteria criteria, Requirement firstPlayerChooseRequirement, Requirement secondPlayerChooseRequirement) {
        this.name = name;
        this.startTime = new Date();
        this.timer = timer;
        this.finish = false;
        this.firstRequirement = firstRequirement;
        this.secondRequirement = secondRequirement;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.criteria = criteria;
        this.firstPlayerChooseRequirement = firstPlayerChooseRequirement;
        this.secondPlayerChooseRequirement = secondPlayerChooseRequirement;
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
    
    public ValutationCriteria getCriteria() {
        return criteria;
    }
 
    public void setCriteria(ValutationCriteria criteria) {
        this.criteria = criteria;
    }
       
    public Requirement getFirstPlayerChooseRequirement() {
        return firstPlayerChooseRequirement;
    }
 
    public void setFirstPlayerChooseRequirement(Requirement firstPlayerChooseRequirement) {
        this.firstPlayerChooseRequirement = firstPlayerChooseRequirement;
    }
    
    public Requirement getSecondPlayerChooseRequirement() {
        return secondPlayerChooseRequirement;
    }
 
    public void setSecondPlayerChooseRequirement(Requirement secondPlayerChooseRequirement) {
        this.secondPlayerChooseRequirement = secondPlayerChooseRequirement;
    }

	public boolean getNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}
    
    
}