package demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="games")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
	
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="game_requirements", joinColumns=@JoinColumn(name="game_id"), inverseJoinColumns=@JoinColumn(name="requirement_id"))  
    private List<Requirement> requirements;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="game_players", joinColumns=@JoinColumn(name="game_id"), inverseJoinColumns=@JoinColumn(name="player_id"))  
    private List<User> players;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="game_criterias", joinColumns=@JoinColumn(name="game_id"), inverseJoinColumns=@JoinColumn(name="criteria_id"))  
    private List<ValutationCriteria> criterias;
    
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    private Boolean finished;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	private List<RequirementsMatrixData> requirementsMatrixData;
    
	public Game() {    	
	}
	
    public Long getGameId() {
        return gameId;
    }
 
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }
 
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }	
    
    public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public List<ValutationCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<ValutationCriteria> criterias) {
		this.criterias = criterias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
	@JsonIgnore
	public List<RequirementsMatrixData> getRequirementsMatrixData() {
		return requirementsMatrixData;
	}

	@JsonIgnore
	public void setRequirementsMatrixData(List<RequirementsMatrixData> requirementsMatrixData) {
		this.requirementsMatrixData = requirementsMatrixData;
	}
	
	public Float getProgress()
	{
		Float total = 0f;
		Float voted = 0f;
		
		if(getRequirementsMatrixData() != null)
		{
			for(RequirementsMatrixData data : getRequirementsMatrixData())
			{
				if(data != null && data.getPlayerMoves() != null)
				{
					for(PlayerMove pm : data.getPlayerMoves())
					{
						total++;
						if(pm.getValue() != null && pm.getValue() > 0l)
						{
							voted++;
						}
					}
				}
			}
		}
		return total == 0f ? 0f : (voted / total);
	}

}
