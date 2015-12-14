package eu.supersede.fe.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

	private String applicationName;
	private String applicationPage;
	private String profileRequired;
	
	public Application()
	{}
	
	public Application(String applicationName, String applicationPage, String profileRequired) {
		this.applicationName = applicationName;
		this.applicationPage = applicationPage;
		this.profileRequired = profileRequired;
	}

	public String getId()
	{
		return applicationName + applicationPage + profileRequired;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getApplicationPage() {
		return applicationPage;
	}
	
	public void setApplicationPage(String applicationPage) {
		this.applicationPage = applicationPage;
	}
	
	public String getProfileRequired() {
		return profileRequired;
	}
	
	public void setProfileRequired(String profileRequired) {
		this.profileRequired = profileRequired;
	}
	
	@Override
	public int hashCode()
	{
		return applicationName.hashCode() + applicationPage .hashCode() + profileRequired.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (Application.class != obj.getClass())
		{
			return false;
		}
		Application other = (Application)obj;
		return this.applicationName.equals(other.applicationName) && 
				this.applicationPage.equals(other.applicationPage) && 
				this.profileRequired.equals(other.profileRequired);
	}
}
