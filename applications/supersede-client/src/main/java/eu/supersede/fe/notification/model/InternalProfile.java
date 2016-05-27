package eu.supersede.fe.notification.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="profiles")
public class InternalProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileId;
    private String name;
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="profiles", fetch=FetchType.LAZY)  
    private List<InternalUser> users;
    
    public InternalProfile() {
    }
 
    public InternalProfile(String name) {
        this.name = name;
    }
 
    public Long getProfileId() {
        return profileId;
    }
 
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonIgnore
    public List<InternalUser> getUsers()
    {
    	return users;
    }
}
