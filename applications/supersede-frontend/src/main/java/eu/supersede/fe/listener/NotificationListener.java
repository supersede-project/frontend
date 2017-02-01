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

package eu.supersede.fe.listener;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.jpa.NotificationsJpa;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.message.NotificationRedisTemplate;
import eu.supersede.fe.message.model.Notification;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;
import eu.supersede.fe.multitenant.MultiJpaProvider;

/**
 * Listener for notification.
 */
@Component
public class NotificationListener
{
    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NotificationRedisTemplate notificationTemplate;

    @Autowired
    private MultiJpaProvider multiJpaProvider;

    private Map<String, ProfilesJpa> profiles;
    private Map<String, NotificationsJpa> notifications;
    private Map<String, UsersJpa> users;

    /**
     * Load notifications at initialization.
     */
    @PostConstruct
    private void init()
    {
        profiles = multiJpaProvider.getRepositories(ProfilesJpa.class);
        notifications = multiJpaProvider.getRepositories(NotificationsJpa.class);
        users = multiJpaProvider.getRepositories(UsersJpa.class);

        loadNotifications();
    }

    /**
     * Load all the notifications.
     */
    private synchronized void loadNotifications()
    {
        while (notificationTemplate.opsForSet().size("notifications") > 0L)
        {
            Notification n = notificationTemplate.opsForSet().pop("notifications");
            String tenant = n.getTenant();

            if (n.getProfile())
            {
                Profile p = profiles.get(tenant).findByName(n.getRecipient());

                for (User u : p.getUsers())
                {
                    createNotification(users.get(tenant), notifications.get(tenant), u.getEmail(), n.getLink(),
                            n.getMessage());
                }
            }
            else
            {
                createNotification(users.get(tenant), notifications.get(tenant), n.getRecipient(), n.getLink(),
                        n.getMessage());
            }
        }
    }

    /**
     * Create a notification with the given parameters.
     * @param users
     * @param notifications
     * @param email
     * @param link
     * @param message
     */
    private void createNotification(UsersJpa users, NotificationsJpa notifications, String email, String link,
            String message)
    {
        User u = users.findByEmail(email);

        if (u != null)
        {
            eu.supersede.fe.model.Notification notif = new eu.supersede.fe.model.Notification();
            notif.setCreationTime(new Date());
            notif.setEmailSent(false);
            notif.setLink(link);
            notif.setMessage(message);
            notif.setRead(false);
            notif.setUser(u);

            notifications.saveAndFlush(notif);
        }
    }

    public void receiveMessage(String message)
    {
        loadNotifications();
    }
}