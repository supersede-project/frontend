package eu.supersede.fe.notification.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Entity
//@Table(name="users")
public class InternalUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String name;
    private String email;
    private String password;
    @ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="users_profiles", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="profile_id"))  
    private List<InternalProfile> profiles;
 
    public InternalUser() {
    }
 
    public InternalUser(String name, String email, String password, List<InternalProfile> profiles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profiles = profiles;
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
 
    @JsonIgnore
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public List<InternalProfile> getProfiles() {
        return profiles;
    }
 
    public void setProfiles(List<InternalProfile> profiles) {
        this.profiles = profiles;
    }
}
