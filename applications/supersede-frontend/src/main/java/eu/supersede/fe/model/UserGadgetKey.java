package eu.supersede.fe.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserGadgetKey implements Serializable {

	protected Long userId;
	protected Long gadgetId;
	
	public UserGadgetKey()
	{
		
	}
	
	public UserGadgetKey(Long userId, Long gadgetId)
	{
		this.userId = userId;
		this.gadgetId = gadgetId;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserGadgetKey)
		{
			UserGadgetKey other = (UserGadgetKey) obj;
			
			return userId.equals(other.userId) && gadgetId.equals(other.gadgetId);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return userId.hashCode() + gadgetId.hashCode();
	}
	
}
