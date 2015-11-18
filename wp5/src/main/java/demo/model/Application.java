package demo.model;

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
    private String requiredRole;
	
    public Application()
    {}
    
    public Application(String name, String mainPage, String requiredRole)
    {
    	this.name = name;
    	this.mainPage = mainPage;
    	this.requiredRole = requiredRole;
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
    
    public String getRequiredRole() {
        return requiredRole;
    }
 
    public void setRequiredRole(String requiredRole) {
        this.requiredRole = requiredRole;
    }
    
}
