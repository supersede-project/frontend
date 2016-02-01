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
@Table(name="judge_acts")
public class JudgeAct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long judgeActId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="requirements_matrix_data_id", nullable = false)
	private RequirementsMatrixData requirementsMatrixData;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="judge_id")
	private User judge;
	
	private Boolean voted;
	
	public JudgeAct(){
		
	}
	
    public Long getJudgeActId() {
        return judgeActId;
    }
 
    public void setJudgeActId(Long judgeActId) {
        this.judgeActId = judgeActId;
    }

    public RequirementsMatrixData getRequirementsMatrixData() {
        return requirementsMatrixData;
    }
 
    public void setRequirementsMatrixData(RequirementsMatrixData requirementsMatrixData) {
        this.requirementsMatrixData = requirementsMatrixData;
    }
    
    public User getJudge() {
        return judge;
    }
 
    public void setJudge(User judge) {
        this.judge = judge;
    }
	
	public Boolean getVoted() {
		return voted;
	}

	public void setVoted(Boolean voted) {
		this.voted = voted;
	}
}
