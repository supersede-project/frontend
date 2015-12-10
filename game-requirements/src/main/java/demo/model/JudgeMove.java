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
@Table(name="judge_moves")
public class JudgeMove {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long judgeMoveId;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="move_id", nullable = false)
    private Move move;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="judge_id")
    private User judge;
    
    private boolean needArguments;
    private boolean gaveOpinion;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="opinion")
    private Requirement opinionRequirement;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="first_argument_id")
    private Argument firstArgument;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="second_argument_id")
    private Argument secondArgument;
    
    private boolean finish;
    private boolean notificationSent;
    
    public JudgeMove(){
    	
    }
    
    public JudgeMove(Move move){
    	this.move = move;
    	this.needArguments = false;
    	this.gaveOpinion = false;
    	this.finish = false;
    }
    
    public JudgeMove(Move move, User judge, Requirement opinionRequirement, Argument firstArgument, Argument secondArgument){
    	this.move = move;
    	this.judge = judge;
    	this.needArguments = false;
    	this.gaveOpinion = false;
    	this.opinionRequirement = opinionRequirement;
    	this.firstArgument = firstArgument;
    	this.secondArgument = secondArgument;
    	this.finish = false;
    }
    
    public Long getJudgeMoveId(){
    	return judgeMoveId;
    }
    
    public void setJudgeMoveId(Long judgeMoveId){
    	this.judgeMoveId = judgeMoveId;
    }
    
    public Move getMove(){
    	return move;
    }
    
    public void setMove(Move move){
    	this.move = move;
    }
    
    public User getJudge(){
    	return judge;
    }
    
    public void setJudge(User judge){
    	this.judge = judge;
    }  
    
    public boolean getNeedArguments(){
    	return needArguments;
    }
    
    public void setNeedArguments(boolean needArguments){
    	this.needArguments = needArguments;
    }
    
    public boolean getGaveOpinion(){
    	return gaveOpinion;
    }
    
    public void setGaveOpinion(boolean gaveOpinion){
    	this.gaveOpinion = gaveOpinion;
    }
       
    public Requirement getOpinionRequirement(){
    	return opinionRequirement;
    }
    
    public void setOpinionRequirement(Requirement opinionRequirement){
    	this.opinionRequirement = opinionRequirement;
    }
    
    public Argument getFirstArgument(){
    	return firstArgument;
    }
    
    public void setFirstArgument(Argument firstArgument){
    	this.firstArgument = firstArgument;
    }
    
    public Argument getSecondArgument(){
    	return secondArgument;
    }
    
    public void setSecondArgument(Argument secondArgument){
    	this.secondArgument = secondArgument;
    }
    
    public boolean getFinish(){
    	return finish;
    }
    
    public void setFinish(boolean finish){
    	this.finish = finish;
    }
    
    public boolean getNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	} 
}
