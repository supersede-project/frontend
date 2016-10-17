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

package eu.supersede.fe.multitenant;

import javax.annotation.PostConstruct;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@PropertySources({
	@PropertySource(value = "file:../conf/multitenancy.properties", ignoreResourceNotFound=true),
	@PropertySource(value = "file:../conf/multitenancy_${application.name}.properties", ignoreResourceNotFound=true)
  })
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Autowired
	Environment env;
	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String DEFAULT_TENANT_ID = "";
	
	@PostConstruct
	public void load() {
		DEFAULT_TENANT_ID = env.getProperty("application.multitenancy.default");
	}
	
	@Override
	public String resolveCurrentTenantIdentifier()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) 
		{
			String identifier = (String) requestAttributes.getAttribute("CURRENT_TENANT_IDENTIFIER", RequestAttributes.SCOPE_REQUEST);
			if (identifier != null) {
				return identifier;
			}
		}
		
		//current tenant identifier not set, this may happen on login, if present in header we can just use this one
		//TODO: investigate better (add MultiTenancyInterceptor before SecurityConfiguration)
		try
		{
			ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			String multiTenantId = currentRequestAttributes.getRequest().getHeader("TenantId");
			if(multiTenantId != null)
			{
				return multiTenantId;
			}
		}
		catch(IllegalStateException ex)
		{
			//throw if no request were make (????)
		}

		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}