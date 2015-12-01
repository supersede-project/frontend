package demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="valutation_criterias")
public class ValutationCriteria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long criteriaId;
    private String name;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "valutationCriteria")
	public List<UserCriteriaPoint> userCriteriaPoints;

    public ValutationCriteria() {
    }
 
    public ValutationCriteria(String name) {
        this.name = name;
    }
 
    public Long getCriteriaId() {
        return criteriaId;
    }
 
    public void setCriteriaId(Long criteriaId) {
        this.criteriaId = criteriaId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonIgnore
    public List<UserCriteriaPoint> getUserCriteriaPoints(){
    	return userCriteriaPoints;
    }
    
    public void setUserCriteriaPoints(List<UserCriteriaPoint> userCriteriaPoints){
    	this.userCriteriaPoints = userCriteriaPoints;
    }
}
