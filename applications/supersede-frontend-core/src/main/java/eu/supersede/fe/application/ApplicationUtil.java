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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility methods for applications and gadgets.
 */
@Component
public class ApplicationUtil
{
    private ObjectMapper mapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate template;

    private static final String APP_KEY = "Application";
    private static final String PAGE_KEY = "ApplicationPage";
    private static final String GADGET_KEY = "ApplicationGadget";

    /**
     * Add an application with the given name, labels and home page.
     * @param applicationName
     * @param applicationLabels
     * @param homePage
     */
    public void addApplication(String applicationName, Map<String, String> applicationLabels, String homePage)
    {
        Application app = new Application(applicationName, applicationLabels, homePage);

        try
        {
            template.opsForHash().put(APP_KEY, app.getId(), mapper.writeValueAsString(app));
        }
        catch (JsonProcessingException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Return the applications with the given name.
     * @param applicationName
     */
    public Application getApplication(String applicationName)
    {
        Object o = template.opsForHash().get(APP_KEY, applicationName);
        return getFromString(Application.class, (String) o);
    }

    /**
     * Remove the given application.
     * @param app
     */
    public void removeApplication(Application app)
    {
        template.opsForHash().delete(APP_KEY, app.getId());
    }

    /**
     * Add the given page for the given application with the given localised labels and required profiles.
     * @param applicationName
     * @param applicationPage
     * @param applicationPageLabels
     * @param profilesRequired
     */
    public void addApplicationPage(String applicationName, String applicationPage,
            Map<String, String> applicationPageLabels, List<String> profilesRequired)
    {
        ApplicationPage app = new ApplicationPage(applicationName, applicationPage, applicationPageLabels,
                profilesRequired);
        try
        {
            template.opsForHash().put(PAGE_KEY, app.getId(), mapper.writeValueAsString(app));
        }
        catch (JsonProcessingException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Remove the given application page.
     * @param page
     */
    public void removeApplicationPage(ApplicationPage page)
    {
        template.opsForHash().delete(PAGE_KEY, page.getId());
    }

    /**
     * Return all the application pages.
     */
    public Set<ApplicationPage> getAllApplicationsPages()
    {
        Set<ApplicationPage> apps = new HashSet<>();
        List<Object> applications = template.opsForHash().values(PAGE_KEY);

        for (Object o : applications)
        {
            apps.add(getFromString(ApplicationPage.class, (String) o));
        }

        return apps;
    }

    /**
     * Return all the application pages compatible with the given profile.
     * @param profile
     */
    public Set<ApplicationPage> getApplicationsPagesByProfileName(String profile)
    {
        Set<ApplicationPage> apps = new HashSet<>();
        List<Object> applications = template.opsForHash().values(PAGE_KEY);

        for (Object o : applications)
        {
            ApplicationPage a = getFromString(ApplicationPage.class, (String) o);

            if (a.getProfilesRequired().contains(profile))
            {
                apps.add(a);
            }
        }

        return apps;
    }

    /**
     * Return the application, application page or application gadget corresponding to the given string.
     * @param clazz
     * @param s
     */
    private <T> T getFromString(Class<T> clazz, String s)
    {
        try
        {
            return mapper.readValue(s, clazz);
        }
        catch (JsonParseException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add a gadget with the given name for the given application with the given required profiles.
     * @param applicationName
     * @param applicationGadget
     * @param profilesRequired
     */
    public void addApplicationGadget(String applicationName, String applicationGadget, List<String> profilesRequired)
    {
        ApplicationGadget gadget = new ApplicationGadget(applicationName, applicationGadget, profilesRequired);

        try
        {
            template.opsForHash().put(GADGET_KEY, gadget.getId(), mapper.writeValueAsString(gadget));
        }
        catch (JsonProcessingException e)
        {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Remove the given gadget.
     * @param gadget
     */
    public void removeApplicationGadget(ApplicationGadget gadget)
    {
        template.opsForHash().delete(GADGET_KEY, gadget.getId());
    }

    /**
     * Return all gadgets.
     */
    public Set<ApplicationGadget> getAllApplicationsGadget()
    {
        Set<ApplicationGadget> gadgs = new HashSet<>();
        List<Object> gadgets = template.opsForHash().values(GADGET_KEY);

        for (Object o : gadgets)
        {
            gadgs.add(getFromString(ApplicationGadget.class, (String) o));
        }

        return gadgs;
    }

    /**
     * Return all the gadgets compatible with the given profile.
     * @param profile
     */
    public Set<ApplicationGadget> getApplicationsGadgetsByProfileName(String profile)
    {
        Set<ApplicationGadget> gadgs = new HashSet<>();
        List<Object> gadgets = template.opsForHash().values(GADGET_KEY);

        for (Object o : gadgets)
        {
            ApplicationGadget g = getFromString(ApplicationGadget.class, (String) o);

            if (g.getProfilesRequired().contains(profile))
            {
                gadgs.add(g);
            }
        }

        return gadgs;
    }

    /**
     * Return the gadgets compatible with the given profiles.
     * @param profiles
     */
    public Set<ApplicationGadget> getApplicationsGadgetsByProfilesNames(Collection<String> profiles)
    {
        Set<ApplicationGadget> gadgs = new HashSet<>();
        List<Object> gadgets = template.opsForHash().values(GADGET_KEY);

        for (Object o : gadgets)
        {
            ApplicationGadget g = getFromString(ApplicationGadget.class, (String) o);

            if (!Collections.disjoint(g.getProfilesRequired(), profiles))
            {
                gadgs.add(g);
            }
        }

        return gadgs;
    }
}