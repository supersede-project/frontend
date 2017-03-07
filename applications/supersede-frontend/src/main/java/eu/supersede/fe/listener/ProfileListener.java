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

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.message.ProfileRedisTemplate;
import eu.supersede.fe.message.model.Profile;
import eu.supersede.fe.multitenant.MultiJpaProvider;

@Component
public class ProfileListener
{
    @Autowired
    private ProfileRedisTemplate profileTemplate;

    @Autowired
    private MultiJpaProvider multiJpaProvider;

    private Map<String, ProfilesJpa> profilesJpa;

    @PostConstruct
    private void init()
    {
        profilesJpa = multiJpaProvider.getRepositories(ProfilesJpa.class);
        loadProfiles();
    }

    private void loadProfiles()
    {
        while (profileTemplate.opsForSet().size("profiles") > 0L)
        {
            Profile p = profileTemplate.opsForSet().pop("profiles");

            for (ProfilesJpa profiles : profilesJpa.values())
            {
                if (p.getName() != null && !p.getName().equals("") && profiles.findByName(p.getName()) == null)
                {
                    eu.supersede.fe.model.Profile prof = new eu.supersede.fe.model.Profile();
                    prof.setName(p.getName());
                    profiles.save(prof);
                }
            }
        }
    }

    public void receiveMessage(String message)
    {
        loadProfiles();
    }
}