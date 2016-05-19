package eu.supersede.fe.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="users")
public class User {
	
	@Id
    private Long userId;
    private String name;
    private String email;
 
    public User() {
    }
 
    public User(Long userId, String name, String email, String password) {
    	this.userId = userId;
    	this.name = name;
        this.email = email;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
}
