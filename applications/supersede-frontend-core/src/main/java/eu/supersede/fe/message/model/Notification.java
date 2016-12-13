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

package eu.supersede.fe.message.model;

/**
 * Notification used in the frontend.
 */
public class Notification
{
    private String recipient;
    private Boolean profile;
    private String message;
    private String link;
    private String tenant;

    /**
     * Is this necessary?
     */
    public Notification()
    {
    }

    /**
     * Default constructor.
     * @param tenant
     * @param recipient
     * @param profile
     * @param message
     * @param link
     */
    public Notification(String tenant, String recipient, Boolean profile, String message, String link)
    {
        this.tenant = tenant;
        this.recipient = recipient;
        this.profile = profile;
        this.message = message;
        this.link = link;
    }

    /**
     * Return the recipient of the notification.
     */
    public String getRecipient()
    {
        return recipient;
    }

    /**
     * Set the recipient of the notification.
     * @param recipient
     */
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    /**
     * Return the profile associated to the notification.
     */
    public Boolean getProfile()
    {
        return profile;
    }

    /**
     * Set the profile associated to the notification.
     * @param profile
     */
    public void setProfile(Boolean profile)
    {
        this.profile = profile;
    }

    /**
     * Return the message of the notification.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Set the message of the notification.
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Return the link associated to the notification.
     */
    public String getLink()
    {
        return link;
    }

    /**
     * Set the link associated to the notification.
     * @param link
     */
    public void setLink(String link)
    {
        this.link = link;
    }

    /**
     * Return the tenant associated to the notification.
     */
    public String getTenant()
    {
        return tenant;
    }

    /**
     * Set the tenant associated to the notification.
     * @param tenant
     */
    public void setTenant(String tenant)
    {
        this.tenant = tenant;
    }
}