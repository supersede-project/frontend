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

/**
 * Generic gadget handled by the frontend.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationGadget
{
    private String applicationName;
    private String applicationGadget;
    private List<String> profilesRequired;

    /**
     * Is this necessary?
     */
    public ApplicationGadget()
    {
    }

    /**
     * Default constructor.
     * @param applicationName
     * @param applicationGadget
     * @param profilesRequired
     */
    public ApplicationGadget(String applicationName, String applicationGadget, List<String> profilesRequired)
    {
        this.setApplicationName(applicationName);
        this.setApplicationGadget(applicationGadget);
        this.setProfilesRequired(profilesRequired);
    }

    /**
     * Return the id of the gadget.
     */
    public String getId()
    {
        return applicationName + applicationGadget;
    }

    /**
     * Return the name of the application associated to the gadget.
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * Set the application name.
     * @param applicationName
     */
    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }

    /**
     * Return the name of the gadget.
     */
    public String getApplicationGadget()
    {
        return applicationGadget;
    }

    /**
     * Set the name of the gadget.
     * @param applicationGadget
     */
    public void setApplicationGadget(String applicationGadget)
    {
        this.applicationGadget = applicationGadget;
    }

    /**
     * Return the profiles required by the gadget.
     */
    public List<String> getProfilesRequired()
    {
        return profilesRequired;
    }

    /**
     * Set the profiles required by the gadget.
     * @param profilesRequired
     */
    public void setProfilesRequired(List<String> profilesRequired)
    {
        this.profilesRequired = profilesRequired;
    }

    @Override
    public int hashCode()
    {
        return applicationName.hashCode() + applicationGadget.hashCode();
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

        ApplicationGadget other = (ApplicationGadget) obj;
        return this.applicationName.equals(other.applicationName)
                && this.applicationGadget.equals(other.applicationGadget);
    }
}