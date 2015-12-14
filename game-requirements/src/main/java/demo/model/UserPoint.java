package demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name="users_points")
public class UserPoint implements Serializable{
	
	@Id
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
    private Long userPoints;
    
    public UserPoint(){
    	this.userPoints = (long) 0;
    }
    
    public Long getUserPoints(){
    	return userPoints;
    }
    
    public void setUserPoints(Long userPoints){
    	this.userPoints = userPoints;
    }
    
    @JsonIgnore
    public User getUser(){
    	return user;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
    
}