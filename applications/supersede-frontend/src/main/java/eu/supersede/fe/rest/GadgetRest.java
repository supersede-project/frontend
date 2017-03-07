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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.application.ApplicationGadget;
import eu.supersede.fe.application.ApplicationGadgetComparator;
import eu.supersede.fe.application.ApplicationUtil;
import eu.supersede.fe.jpa.UserDashboardJpa;
import eu.supersede.fe.jpa.UserGadgetsJpa;
import eu.supersede.fe.model.UserDashboard;
import eu.supersede.fe.model.UserGadget;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/gadget")
public class GadgetRest
{
    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private UserGadgetsJpa userGadgets;

    @Autowired
    private UserDashboardJpa userDashboards;

    private final static ApplicationGadgetComparator comparator = new ApplicationGadgetComparator();

    private static final Long USER_DASHBOARD_PANELS_DEFAULT = 2L;

    @RequestMapping("/available")
    public List<ApplicationGadget> getUserAuthenticatedAvailableApplicationsGadgets(Authentication auth)
    {
        // DatabaseUser user = (DatabaseUser)auth.getPrincipal();
        List<String> authNames = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        for (GrantedAuthority ga : authorities)
        {
            authNames.add(ga.getAuthority().substring(5));
        }

        List<ApplicationGadget> availGadgets = new ArrayList<>(
                applicationUtil.getApplicationsGadgetsByProfilesNames(authNames));
        Collections.sort(availGadgets, comparator);

        return availGadgets;
    }

    @RequestMapping("")
    public List<UserGadget> getUserAuthenticatedApplicationsGadgets(Authentication auth)
    {
        DatabaseUser user = (DatabaseUser) auth.getPrincipal();
        List<UserGadget> gadgets = userGadgets.findByUserIdOrderByGadgetIdAsc(user.getUserId());
        return gadgets;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "")
    public List<UserGadget> saveUserAuthenticatedApplicationsGadgets(Authentication auth,
            @RequestBody List<UserGadget> gadgets)
    {
        DatabaseUser user = (DatabaseUser) auth.getPrincipal();
        Long userId = user.getUserId();

        for (long i = 0; i < gadgets.size(); i++)
        {
            UserGadget g = gadgets.get((int) i);
            g.setUserId(userId);
            g.setGadgetId(i);
        }

        userGadgets.deleteByUserId(userId);
        userGadgets.save(gadgets);

        return gadgets;
    }

    @RequestMapping(value = "/panel", method = RequestMethod.GET)
    public Long getNumPanels(Authentication auth)
    {
        Long p = null;

        DatabaseUser user = (DatabaseUser) auth.getPrincipal();
        Long userId = user.getUserId();

        UserDashboard ud = userDashboards.findOne(userId);

        if (ud == null)
        {
            p = USER_DASHBOARD_PANELS_DEFAULT;
        }
        else
        {
            p = ud.getPanels();
        }

        return p;
    }

    @RequestMapping(value = "/panel", method = RequestMethod.PUT)
    public void setNumPanels(Authentication auth, @RequestBody Long numPanels)
    {
        DatabaseUser user = (DatabaseUser) auth.getPrincipal();
        Long userId = user.getUserId();

        UserDashboard ud = userDashboards.findOne(userId);

        if (ud == null)
        {
            ud = new UserDashboard();
            ud.setUserId(userId);
        }

        ud.setPanels(numPanels);
        userDashboards.save(ud);

        List<UserGadget> gadgets = userGadgets.findByUserIdOrderByGadgetIdAsc(user.getUserId());

        for (UserGadget g : gadgets)
        {
            if (g.getPanel() > (numPanels - 1L))
            {
                g.setPanel(0L);
                userGadgets.save(g);
            }
        }
    }
}