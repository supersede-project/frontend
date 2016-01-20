package eu.supersede.fe.model;

import javax.persistence.*;

@Entity
@Table(name="users_gadgets")
@IdClass(UserGadgetKey.class)
public class UserGadget {

	@Id
	private Long userId;
	@Id
	private Long gadgetId;
	private String applicationName;
	private String gadgetName;
	
	public UserGadget() 
	{

	}
	
	public UserGadget(Long userId, Long gadgetId, String applicationName, String gadgetName)
	{
		this.userId = userId;
		this.gadgetId = gadgetId;
		this.applicationName = applicationName;
		this.gadgetName = gadgetName;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getGadgetId() {
		return gadgetId;
	}
	
	public void setGadgetId(Long gadgetId) {
		this.gadgetId = gadgetId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getGadgetName() {
		return gadgetName;
	}

	public void setGadgetName(String gadgetName) {
		this.gadgetName = gadgetName;
	}
	
}
