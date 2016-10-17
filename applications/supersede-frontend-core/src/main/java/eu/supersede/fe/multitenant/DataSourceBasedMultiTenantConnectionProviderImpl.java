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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@PropertySources({
		@PropertySource(value = "file:../conf/multitenancy.properties", ignoreResourceNotFound=true),
		@PropertySource(value = "file:../conf/multitenancy_${application.name}.properties", ignoreResourceNotFound=true)
      })
@EnableConfigurationProperties(JpaProperties.class)
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl
{
	@Autowired
	Environment env;
		
	private String[] tenantNames;
	private String defaultTenant;
	
	private Map<String, DataSource> datasources;

	@PostConstruct
	public void load() {
		datasources = new HashMap<>();
		
		tenantNames = env.getProperty("application.multitenancy.names").split(",");
		if(tenantNames == null)
		{
			return;
		}
		
		for(int i = 0; i < tenantNames.length; i++)
		{
			tenantNames[i] = tenantNames[i].trim();
		}
		
		defaultTenant = env.getRequiredProperty("application.multitenancy.default");
		if(defaultTenant == null)
		{
			defaultTenant = tenantNames[0];
		}
		
		for(String n : tenantNames)
		{
			if(env.getProperty("application.multitenancy." + n + ".driverClassName") != null)
			{
				DataSourceBuilder factory = DataSourceBuilder
						.create(env.getClass().getClassLoader())
						.driverClassName(env.getRequiredProperty("application.multitenancy." + n + ".driverClassName"))
						.username(env.getRequiredProperty("application.multitenancy." + n + ".username"))
						.password(env.getRequiredProperty("application.multitenancy." + n + ".password"))
						.url(env.getRequiredProperty("application.multitenancy." + n + ".url"));
				DataSource tmp = factory.build();
				
				datasources.put(n, tmp);
			}
		}
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return selectAnyDataSource();
	}
	
	@Override
	protected DataSource selectAnyDataSource() {
		DataSource dS = null;
		if(datasources.containsKey(defaultTenant))
		{
			dS = datasources.get(defaultTenant);	
		}
		return dS;
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		DataSource dS = null;
		if(datasources.containsKey(tenantIdentifier))
		{
			dS = datasources.get(tenantIdentifier);
		}
		return dS;
	}
	
	protected Map<String, DataSource> getDataSources()
	{
		return datasources;
	}
}