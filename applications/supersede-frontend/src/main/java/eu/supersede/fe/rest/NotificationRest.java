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

package eu.supersede.fe.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.exception.UnauthorizedException;
import eu.supersede.fe.jpa.NotificationsJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Notification;
import eu.supersede.fe.model.User;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/notification")
public class NotificationRest
{
    @Autowired
    private UsersJpa users;

    @Autowired
    private NotificationsJpa notifications;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public List<Notification> getByUserId(Authentication authentication, HttpServletRequest request,
            @RequestParam(defaultValue = "true") Boolean toRead)
    {
        String scheme;
        String host;
        String port;

        if (request.getHeader("x-forwarded-proto") != null || request.getHeader("x-forwarded-host") != null
                || request.getHeader("x-forwarded-port") != null)
        {
            scheme = request.getHeader("x-forwarded-proto") != null ? request.getHeader("x-forwarded-proto") : "http";
            host = request.getHeader("x-forwarded-host") != null ? request.getHeader("x-forwarded-host")
                    : request.getServerName();
            port = request.getHeader("x-forwarded-port") != null ? request.getHeader("x-forwarded-port") : null;
        }
        else
        {
            scheme = request.getScheme();
            host = request.getServerName();
            port = new Integer(request.getServerPort()).toString();
        }

        String baseUrl = port != null ? scheme + "://" + host + ":" + port + "/#/" : scheme + "://" + host + "/#/";

        DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
        User u = users.getOne(currentUser.getUserId());
        List<Notification> ns;

        if (toRead)
        {
            ns = notifications.findByUserAndReadOrderByCreationTimeDesc(u, !toRead);
        }
        else
        {
            ns = notifications.findByUserOrderByCreationTimeDesc(u);
        }

        for (Notification n : ns)
        {
            if (n.getLink() != null && !n.getLink().equals(""))
            {
                try
                {
                    URI uri = new URI(n.getLink());

                    if (!uri.isAbsolute())
                    {
                        n.setLink(baseUrl + n.getLink());
                    }
                }
                catch (URISyntaxException e)
                {
                    log.debug("Error inside link: " + e.getMessage());
                }
            }
        }

        return ns;
    }

    @RequestMapping("/count")
    public Long countByUserId(Authentication authentication, @RequestParam(defaultValue = "true") Boolean toRead)
    {
        DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
        User u = users.getOne(currentUser.getUserId());
        Long c;

        if (toRead)
        {
            c = notifications.countByUserAndRead(u, !toRead);
        }
        else
        {
            c = notifications.countByUser(u);
        }

        return c;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{notificationId}/read")
    public void setRead(Authentication authentication, @PathVariable Long notificationId)
    {
        DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
        User u = users.getOne(currentUser.getUserId());
        Notification n = notifications.findOne(notificationId);

        if (n.getUser().equals(u))
        {
            n.setRead(true);
            notifications.save(n);
        }
        else
        {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{notificationId}")
    public void delete(Authentication authentication, @PathVariable Long notificationId)
    {
        DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
        User u = users.getOne(currentUser.getUserId());
        Notification n = notifications.findOne(notificationId);

        if (n.getUser().equals(u))
        {
            notifications.delete(notificationId);
        }
        else
        {
            throw new UnauthorizedException();
        }
    }
}