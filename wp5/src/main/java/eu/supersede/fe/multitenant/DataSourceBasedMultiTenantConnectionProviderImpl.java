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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:multitenancy.properties")
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
		
		tenantNames = env.getRequiredProperty("spring.multitenancy.names").split(",");
		defaultTenant = env.getRequiredProperty("spring.multitenancy.default");
		
		for(String n : tenantNames)
		{
			DataSourceBuilder factory = DataSourceBuilder
					.create(env.getClass().getClassLoader())
					.driverClassName(env.getRequiredProperty("spring.multitenancy." + n + ".driverClassName"))
					.username(env.getRequiredProperty("spring.multitenancy." + n + ".username"))
					.password(env.getRequiredProperty("spring.multitenancy." + n + ".password"))
					.url(env.getRequiredProperty("spring.multitenancy." + n + ".url"));
			DataSource tmp = factory.build();
			
			datasources.put(n, tmp);
		}
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return selectAnyDataSource();
	}
	
	@Override
	protected DataSource selectAnyDataSource() {
		return datasources.get(defaultTenant);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return datasources.get(tenantIdentifier);
	}
	
	protected Map<String, DataSource> getDataSources()
	{
		return datasources;
	}
}