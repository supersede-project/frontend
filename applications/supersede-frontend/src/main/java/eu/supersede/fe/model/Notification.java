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

package eu.supersede.fe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notifications")
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Boolean read;
    private Boolean emailSent;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    private String link;

    public Notification()
    {
    }

    public Notification(String message, User user)
    {
        this.message = message;
        this.user = user;
        read = false;
        emailSent = false;
        creationTime = new Date();
    }

    public Long getNotificationId()
    {
        return notificationId;
    }

    public void setNotificationId(Long notificationId)
    {
        this.notificationId = notificationId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @JsonIgnore
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Boolean getRead()
    {
        return read;
    }

    public void setRead(Boolean read)
    {
        this.read = read;
    }

    public Boolean getEmailSent()
    {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent)
    {
        this.emailSent = emailSent;
    }

    public Date getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}