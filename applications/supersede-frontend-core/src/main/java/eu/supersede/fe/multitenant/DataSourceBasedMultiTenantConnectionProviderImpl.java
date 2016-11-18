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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import eu.supersede.fe.configuration.ApplicationConfiguration;

@SuppressWarnings("serial")
@Component
@PropertySource("file:../conf/multitenancy.properties")
@EnableConfigurationProperties(JpaProperties.class)
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Environment env;

	private String[] tenantNames;
	private String defaultTenant;

	private Map<String, DataSource> datasources;

	@PostConstruct
	public void load() {
		datasources = new HashMap<>();
		String applicationName = ApplicationConfiguration.getApplicationName();
		String tmpTenants = env.getProperty(applicationName + ".multitenancy.names");

		if (tmpTenants == null)
		{
			datasources.put("fake", getFakeDatasource());
			return;
		}

		tenantNames = tmpTenants.split(",");

		for(int i = 0; i < tenantNames.length; i++)
		{
			tenantNames[i] = tenantNames[i].trim();
			log.info("Found tenant: " + tenantNames[i]);
		}

		defaultTenant = env.getRequiredProperty(applicationName + ".multitenancy.default");

		if (defaultTenant == null)
		{
			defaultTenant = tenantNames[0];
		}

		for (String n : tenantNames)
		{
			if (env.getProperty(applicationName + ".multitenancy." + n + ".driverClassName") != null)
			{
				DataSourceBuilder factory = DataSourceBuilder
						.create(env.getClass().getClassLoader())
						.driverClassName(env.getRequiredProperty(applicationName + ".multitenancy." + n + ".driverClassName"))
						.username(env.getRequiredProperty(applicationName + ".multitenancy." + n + ".username"))
						.password(env.getRequiredProperty(applicationName + ".multitenancy." + n + ".password"))
						.url(env.getRequiredProperty(applicationName + ".multitenancy." + n + ".url"));
				DataSource tmp = factory.build();

				datasources.put(n, tmp);
			}
		}

		if(datasources.size() == 0)
		{
			datasources.put("fake", getFakeDatasource());
		}
	}

	private DataSource getFakeDatasource()
	{
		DataSourceBuilder factory = DataSourceBuilder
				.create(env.getClass().getClassLoader())
				.driverClassName("org.h2.Driver")
				.username("test")
				.url("jdbc:h2:mem:");
		return factory.build();
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
		else
		{
			Set<String> keys = datasources.keySet();
			Iterator keysIt = keys.iterator();
			while(keysIt.hasNext())
			{
				dS = datasources.get(keysIt.next());
				break;
			}
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