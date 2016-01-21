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
	private String size;
	
	public UserGadget() 
	{

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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
