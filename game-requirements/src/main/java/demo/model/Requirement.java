package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requirements")
public class Requirement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long requirementId;
    private String content;
    
    public Requirement() {
    }
 
    public Requirement(String content) {
        this.content = content;
    }
 
    public Long getRequirementId() {
        return requirementId;
    }
 
    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
}