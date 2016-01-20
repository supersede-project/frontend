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
@Table(name="criterias_matrices_data")
public class CriteriasMatrixData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long criteriasMatrixDataId;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="criterias_matrix_id", nullable = false)
	private CriteriasMatrix criteriasMatrix;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="row", nullable = false)
	private ValutationCriteria rowCriteria;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="column", nullable = false)
	private ValutationCriteria columnCriteria;
	
	private Long value;
	 
	public CriteriasMatrixData() {    	
	}
	
    public Long getCriteriasMatrixDataId() {
        return criteriasMatrixDataId;
    }
 
    public void setCriteriasMatrixDataId(Long criteriasMatrixDataId) {
        this.criteriasMatrixDataId = criteriasMatrixDataId;
    }
    
    public CriteriasMatrix getCriteriasMatrix() {
        return criteriasMatrix;
    }
 
    public void setCriteriasMatrix(CriteriasMatrix criteriasMatrix) {
        this.criteriasMatrix = criteriasMatrix;
    }
    
    public ValutationCriteria getRowCriteria() {
        return rowCriteria;
    }
 
    public void setRowCriteria(ValutationCriteria rowCriteria) {
        this.rowCriteria = rowCriteria;
    }
    
    public ValutationCriteria getColumnCriteria() {
        return columnCriteria;
    }
 
    public void setColumnCriteria(ValutationCriteria columnCriteria) {
        this.columnCriteria = columnCriteria;
    }
    
    public Long getValue() {
        return value;
    }
 
    public void setValue(Long value) {
        this.value = value;
    }
}
