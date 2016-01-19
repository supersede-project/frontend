package demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="requirements_matrices")
public class RequirementsMatrix {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long requirementsMatrixId;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id", nullable = false)
	private Game game;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="criteria_id", nullable = false)
	private ValutationCriteria criteria;
	 
	public RequirementsMatrix() {    	
	}
	
    public Long getRequirementsMatrixId() {
        return requirementsMatrixId;
    }
 
    public void setRequirementsMatrixId(Long requirementsMatrixId) {
        this.requirementsMatrixId = requirementsMatrixId;
    }
    
    public Game getGame() {
        return game;
    }
 
    public void setGame(Game game) {
        this.game = game;
    }
    
    public ValutationCriteria getCriteria() {
        return criteria;
    }
 
    public void setCriteria(ValutationCriteria criteria) {
        this.criteria = criteria;
    }
}
