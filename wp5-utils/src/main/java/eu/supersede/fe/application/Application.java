package eu.supersede.fe.application;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

	private String applicationName;
	private Map<String, String> applicationLabels;
	private String applicationPage;
	private Map<String, String> applicationPageLabels;
	private List<String> profilesRequired;
	
	public Application()
	{}
	
	public Application(String applicationName, 
			Map<String, String> applicationLabels, 
			String applicationPage,
			Map<String, String> applicationPageLabels, 
			List<String> profilesRequired) {
		this.applicationName = applicationName;
		this.applicationLabels = applicationLabels;
		this.applicationPage = applicationPage;
		this.setApplicationPageLabels(applicationPageLabels);
		this.setProfilesRequired(profilesRequired);
	}

	public String getId()
	{
		return applicationName + applicationPage;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getLocalizedApplicationLabel(String lang)
	{
		if(applicationLabels.containsKey(lang))
		{
			return applicationLabels.get(lang);
		}
		
		return applicationLabels.get("");
	}
	
	public Map<String, String> getApplicationLabels() {
		return applicationLabels;
	}

	public void setApplicationLabels(Map<String, String> applicationLabels) {
		this.applicationLabels = applicationLabels;
	}
	
	public String getApplicationPage() {
		return applicationPage;
	}
	
	public void setApplicationPage(String applicationPage) {
		this.applicationPage = applicationPage;
	}

	public String getLocalizedApplicationPageLabel(String lang) {
		if(applicationPageLabels.containsKey(lang))
		{
			return applicationPageLabels.get(lang);
		}
		
		return applicationPageLabels.get("");
	}
	
	public Map<String, String> getApplicationPageLabels() {
		return applicationPageLabels;
	}

	public void setApplicationPageLabels(Map<String, String> applicationPageLabels) {
		this.applicationPageLabels = applicationPageLabels;
	}

	public List<String> getProfilesRequired() {
		return profilesRequired;
	}

	public void setProfilesRequired(List<String> profilesRequired) {
		this.profilesRequired = profilesRequired;
	}

	@Override
	public int hashCode()
	{
		return applicationName.hashCode() + applicationPage .hashCode();
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
				this.applicationPage.equals(other.applicationPage);
	}
}
