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
    @JoinColumn(name="requirements_matrix_id", nullable = false)
	private RequirementsMatrix requirementsMatrix;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="row", nullable = false)
	private Requirement rowRequirement;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="column", nullable = false)
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
    
    public RequirementsMatrix getRequirementsMatrix() {
        return requirementsMatrix;
    }
 
    public void setRequirementsMatrix(RequirementsMatrix requirementsMatrix) {
        this.requirementsMatrix = requirementsMatrix;
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
