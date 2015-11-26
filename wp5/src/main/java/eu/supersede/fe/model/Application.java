package eu.supersede.fe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationId;
	private String name;
    private String mainPage;
    private Long requiredProfileId;
	
    public Application()
    {}
    
    public Application(String name, String mainPage, Long requiredProfileId)
    {
    	this.name = name;
    	this.mainPage = mainPage;
    	this.requiredProfileId = requiredProfileId;
    }
    
    public Long getApplicationId() {
        return applicationId;
    }
 
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMainPage() {
        return mainPage;
    }
 
    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }
    
    public Long getRequiredProfileId() {
        return requiredProfileId;
    }
 
    public void setRequiredProfileId(Long requiredProfileId) {
        this.requiredProfileId = requiredProfileId;
    }
    
}
