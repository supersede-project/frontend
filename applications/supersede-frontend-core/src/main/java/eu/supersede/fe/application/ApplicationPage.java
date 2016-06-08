/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.application;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationPage {

	private String applicationName;
	private String applicationPage;
	private Map<String, String> applicationPageLabels;
	private List<String> profilesRequired;
	
	public ApplicationPage()
	{}
	
	public ApplicationPage(String applicationName, 
			String applicationPage,
			Map<String, String> applicationPageLabels, 
			List<String> profilesRequired) {
		this.applicationName = applicationName;
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
		if (ApplicationPage.class != obj.getClass())
		{
			return false;
		}
		ApplicationPage other = (ApplicationPage)obj;
		return this.applicationName.equals(other.applicationName) && 
				this.applicationPage.equals(other.applicationPage);
	}
}
