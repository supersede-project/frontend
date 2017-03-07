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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.configuration.ApplicationConfiguration;

@RestController
@RequestMapping("/tenant")
@PropertySource("file:../conf/multitenancy.properties")
public class TenantRest
{
    @Autowired
    Environment env;

    private String[] tenantNames;

    @PostConstruct
    private void load()
    {
        String applicationName = ApplicationConfiguration.getApplicationName();
        String tmpTenants = env.getProperty(applicationName + ".multitenancy.names");

        // Read values of the default application
        if (tmpTenants == null)
        {
            applicationName = ApplicationConfiguration.DEFAULT_APPLICATION_NAME;
            tmpTenants = env.getProperty(applicationName + ".multitenancy.names");
        }

        tenantNames = tmpTenants.split(",");

        for (int i = 0; i < tenantNames.length; i++)
        {
            tenantNames[i] = tenantNames[i].trim();
        }
    }

    @RequestMapping("")
    public String[] getTenants()
    {
        return tenantNames;
    }
}