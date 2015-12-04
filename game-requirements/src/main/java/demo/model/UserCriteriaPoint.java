package demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import demo.utility.UserCriteriaPointKey;


@SuppressWarnings("serial")
@Entity
@IdClass(UserCriteriaPointKey.class)
@Table(name="users_criteria_points")
public class UserCriteriaPoint implements Serializable {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "criteria_id", nullable = false)
	private ValutationCriteria valutationCriteria;
	
    private Long points;
    
    public UserCriteriaPoint() {
    	this.points = (long) 0;
    }
    
    public Long getPoints(){
    	return points;
    }
    
    public void setPoints(Long points){
    	this.points = points;
    }
    
    @JsonIgnore
    public User getUser(){
    	return user;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
    
    public ValutationCriteria getValutationCriteria(){
    	return valutationCriteria;
    }
    
    public void setValutationCriteria(ValutationCriteria valutationCriteria){
    	this.valutationCriteria = valutationCriteria;
    }
}