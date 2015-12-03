package eu.supersede.fe.multitenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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
public class MultiJpaProvider {
	
	@Autowired
	private DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProviderImpl;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	EntityManagerFactoryBuilder builder;

	private Map<String, Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager>> repositoriesFactory;
	
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

			LocalContainerEntityManagerFactoryBean emfb = builder.dataSource(datasources.get(n))
					.packages("eu.supersede.fe.model")
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
	
	public <T extends JpaRepository<?, ?>> List<T> getRepositories(Class<T> c)
	{
		List<T> tmp = new ArrayList<T>();
		
		for(Triple<JpaRepositoryFactory, EntityManagerFactory, EntityManager> factory : repositoriesFactory.values())
		{
			tmp.add(factory.a.getRepository(c));
			
			if(!TransactionSynchronizationManager.hasResource(factory.b))
			{
				EntityManagerHolder emh = new EntityManagerHolder(factory.c);
				TransactionSynchronizationManager.bindResource(factory.b, emh);
			}
		}
		
		return tmp;
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
