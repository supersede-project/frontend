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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationGadget {

	private String applicationName;
	private String applicationGadget;
	private List<String> profilesRequired;

	public ApplicationGadget()
	{}
	
	public ApplicationGadget(String applicationName, String applicationGadget, List<String> profilesRequired) {
		this.setApplicationName(applicationName);
		this.setApplicationGadget(applicationGadget);
		this.setProfilesRequired(profilesRequired);
	}
	
	public String getId()
	{
		return applicationName + applicationGadget;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationGadget() {
		return applicationGadget;
	}

	public void setApplicationGadget(String applicationGadget) {
		this.applicationGadget = applicationGadget;
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
		return applicationName.hashCode() + applicationGadget .hashCode();
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
		if (ApplicationGadget.class != obj.getClass())
		{
			return false;
		}
		ApplicationGadget other = (ApplicationGadget)obj;
		return this.applicationName.equals(other.applicationName) && 
				this.applicationGadget.equals(other.applicationGadget);
	}
}
