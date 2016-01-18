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
@Table(name="criterias_matrices")
public class CriteriasMatrix {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long criteriasMatrixId;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id", nullable = false)
	private Game game;
	 
	public CriteriasMatrix() {    	
	}
	
    public Long getCriteriasMatrixId() {
        return criteriasMatrixId;
    }
 
    public void setCriteriasMatrixId(Long criteriasMatrixId) {
        this.criteriasMatrixId = criteriasMatrixId;
    }
    
    public Game getGame() {
        return game;
    }
 
    public void setGame(Game game) {
        this.game = game;
    }
}
