package eu.supersede.fe.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
	private String username;
	private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="users_profiles", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="profile_id"))  
    private List<Profile> profiles;
    private String locale;
 
    public User() {
    }
 
    public User(String username, String firstName, String lastName, String email, List<Profile> profiles) {
    	this.username = username;
    	this.firstName = firstName;
    	this.lastName = lastName;
        this.email = email;
        this.profiles = profiles;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public String getUsername() {
		return username;
	}
    
    public void setUsername(String username) {
		this.username = username;
	}
    
    public String getFirstName() {
		return firstName;
	}
    
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
    public String getLastName() {
		return lastName;
	}
    
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public List<Profile> getProfiles() {
        return profiles;
    }
 
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
