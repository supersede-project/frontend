package eu.supersede.fe.multitenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:wp5.properties")
public class MultiJpaProvider {
	
	@Autowired
	private DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProviderImpl;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	EntityManagerFactoryBuilder builder;

	private Map<String, RepositoryFactorySupport> repositoriesFactory;
	
	@PostConstruct
	public void load()
	{
		Map<String, DataSource> datasources = dataSourceBasedMultiTenantConnectionProviderImpl.getDataSources();
		
		repositoriesFactory = new HashMap<>();
		for(String n : datasources.keySet())
		{
			Map<String, Object> hibernateProps = new LinkedHashMap<>();
			hibernateProps.putAll(jpaProperties.getHibernateProperties(datasources.get(n)));

			hibernateProps.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

			LocalContainerEntityManagerFactoryBean emf = builder.dataSource(datasources.get(n))
					.packages("eu.supersede.fe.model")
					.properties(hibernateProps)
					.jta(false)
					.build();
			
			emf.afterPropertiesSet();
			repositoriesFactory.put(n, new JpaRepositoryFactory(emf.getObject().createEntityManager()));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public <T extends JpaRepository> List<T> getRepositories(Class<T> c)
	{
		List<T> tmp = new ArrayList<T>();
		
		for(RepositoryFactorySupport factory : repositoriesFactory.values())
		{
			tmp.add(factory.getRepository(c));
		}
		
		return tmp;
	}
	
}
