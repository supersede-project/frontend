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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

	private String applicationName;
	private Map<String, String> applicationLabels;
	private String homePage;
	
	public Application()
	{}
	
	public Application(String applicationName, 
			Map<String, String> applicationLabels, 
			String homePage) {
		this.applicationName = applicationName;
		this.setApplicationLabels(applicationLabels);
		this.setHomePage(homePage);
	}

	public String getId()
	{
		return applicationName;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Map<String, String> getApplicationLabels() {
		return applicationLabels;
	}

	public void setApplicationLabels(Map<String, String> applicationLabels) {
		this.applicationLabels = applicationLabels;
	}

	 public String getLocalizedApplicationLabel(String lang)
	 {
		 if(applicationLabels.containsKey(lang))
         {
			 return applicationLabels.get(lang);
         }
		 return applicationLabels.get("");
	 }
	 	
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@Override
	public int hashCode()
	{
		return applicationName.hashCode();
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
		return this.applicationName.equals(other.applicationName);
	}
}
