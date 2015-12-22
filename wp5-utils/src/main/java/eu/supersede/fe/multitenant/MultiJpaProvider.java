package eu.supersede.fe.multitenant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.cfg.Environment;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@PropertySource("classpath:multitenancy.properties")
public class MultiJpaProvider {

	@Value("${spring.multitenancy.models.packages}")
	private String MODELS_PACKAGES;
	
	@Autowired
	private DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProviderImpl;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	EntityManagerFactoryBuilder builder;

	private Map<String, Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager>> repositoriesFactory;
	
	@PostConstruct
	private void load()
	{
		Map<String, DataSource> datasources = dataSourceBasedMultiTenantConnectionProviderImpl.getDataSources();
		
		repositoriesFactory = new HashMap<>();
		for(String n : datasources.keySet())
		{
			Map<String, Object> hibernateProps = new LinkedHashMap<>();
			hibernateProps.putAll(jpaProperties.getHibernateProperties(datasources.get(n)));

			hibernateProps.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

			Set<String> packages = new HashSet<>(Arrays.asList(MODELS_PACKAGES.split(",")));
			packages.add("eu.supersede.fe.notification.model");
			
			LocalContainerEntityManagerFactoryBean emfb = builder.dataSource(datasources.get(n))
					.packages(packages.toArray(new String[packages.size()]))
					.properties(hibernateProps)
					.jta(false)
					.build();
			
			emfb.afterPropertiesSet();
			EntityManagerFactory emf = emfb.getObject();
			EntityManager em = emf.createEntityManager();
			
			final JpaTransactionManager jpaTranMan = new JpaTransactionManager(emf);
			JpaRepositoryFactory jpaFactory = new JpaRepositoryFactory(em);
			jpaFactory.addRepositoryProxyPostProcessor(new MultiJpaRepositoryProxyPostProcessor(jpaTranMan));
			
			repositoriesFactory.put(n, new Triple<>(jpaFactory, emf, em));
		}
	}
	
	public <T extends JpaRepository<?, ?>> Map<String, T> getRepositories(Class<T> c)
	{
		Map<String, T> tmp = new HashMap<String, T>();
		
		for(String tenant : repositoriesFactory.keySet())
		{
			Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager> factory = repositoriesFactory.get(tenant);
			tmp.put(tenant, factory.a.getRepository(c));
			
			if(!TransactionSynchronizationManager.hasResource(factory.b))
			{
				EntityManagerHolder emh = new EntityManagerHolder(factory.c);
				TransactionSynchronizationManager.bindResource(factory.b, emh);
			}
		}
		
		return tmp;
	}
	
	public <T extends JpaRepository<?, ?>> T getRepository(Class<T> c, String tenant)
	{
		T repo = null;
		if(repositoriesFactory.containsKey(tenant))
		{
			Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager> factory = repositoriesFactory.get(tenant);
			repo = factory.a.getRepository(c);
			
			if(!TransactionSynchronizationManager.hasResource(factory.b))
			{
				EntityManagerHolder emh = new EntityManagerHolder(factory.c);
				TransactionSynchronizationManager.bindResource(factory.b, emh);
			}
		}
		
		return repo;
	}
	
	public void clearTenants()
	{
		for(String tenant : repositoriesFactory.keySet())
		{
			Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager> factory = repositoriesFactory.get(tenant);
			factory.c.clear();
		}
	}
	
	public void clearTenant(String tenant)
	{
		Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager> factory = repositoriesFactory.get(tenant);
		factory.c.clear();
	}
	
	private class MultiJpaRepositoryProxyPostProcessor implements RepositoryProxyPostProcessor
	{
		private JpaTransactionManager jpaTranMan;

		private MultiJpaRepositoryProxyPostProcessor(JpaTransactionManager jpaTranMan)
		{
			this.jpaTranMan = jpaTranMan;
		}
		
		@Override
	    public void postProcess(ProxyFactory jpaFactory, RepositoryInformation repositoryInformation) {
	    	jpaFactory.addAdvice(new TransactionInterceptor(jpaTranMan, new MatchAlwaysTransactionAttributeSource()));
	    }
	}
	
	private class Triple<T, U, V>
	{
		private T a;
		private U b;
		private V c;

		private Triple(T a, U b, V c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
}
