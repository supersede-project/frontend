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
@Table(name="requirements_matrices_data")
public class RequirementsMatrixData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long requirementsMatrixDataId;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id", nullable = false)
	private Game game;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="criteria_id", nullable = false)
	private ValutationCriteria criteria;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="requirement_row_id", nullable = false)
	private Requirement rowRequirement;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="requirement_column_id", nullable = false)
	private Requirement columnRequirement;
	
	private Long value;
	 
	public RequirementsMatrixData() {    	
	}
	
    public Long getRequirementsMatrixDataId() {
        return requirementsMatrixDataId;
    }
 
    public void setRequirementsMatrixDataId(Long requirementsMatrixDataId) {
        this.requirementsMatrixDataId = requirementsMatrixDataId;
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
    
    public Requirement getRowRequirement() {
        return rowRequirement;
    }
 
    public void setRowRequirement(Requirement rowRequirement) {
        this.rowRequirement = rowRequirement;
    }
    
    public Requirement getColumnRequirement() {
        return columnRequirement;
    }
 
    public void setColumnRequirement(Requirement columnRequirement) {
        this.columnRequirement = columnRequirement;
    }
    
    public Long getValue() {
        return value;
    }
 
    public void setValue(Long value) {
        this.value = value;
    }
}
