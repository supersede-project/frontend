package eu.supersede.fe.message.model;

public class Notification {

	private String recipient;
	private Boolean profile;
	private String message;
	private String link;
	private String tenant;
	
	public Notification()
	{}
	
	public Notification(String tenant, String recipient, Boolean profile, String message, String link)
	{
		this.tenant = tenant;
		this.recipient = recipient;
		this.profile = profile;
		this.message = message;
		this.link = link;
		
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public Boolean getProfile() {
		return profile;
	}
	
	public void setProfile(Boolean profile) {
		this.profile = profile;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getTenant() {
		return tenant;
	}
	
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	
}
