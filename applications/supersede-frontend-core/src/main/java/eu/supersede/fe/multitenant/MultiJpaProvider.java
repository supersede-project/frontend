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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
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
@PropertySource("classpath:wp5_application.properties")
public class MultiJpaProvider
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${application.multitenancy.models.packages}")
    private String MODELS_PACKAGES;

    @Autowired
    private DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProviderImpl;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired(required = false)
    EntityManagerFactoryBuilder builder;

    private Map<String, ContainerUtil> repositoriesFactory;

    @PostConstruct
    private void load()
    {
        Map<String, DataSource> datasources = dataSourceBasedMultiTenantConnectionProviderImpl.getDataSources();

        repositoriesFactory = new HashMap<>();

        for (String n : datasources.keySet())
        {
            try
            {
                log.info("Loading database: " + datasources.get(n).getConnection().getMetaData().getURL());
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Map<String, Object> hibernateProps = new LinkedHashMap<>();
            hibernateProps.putAll(jpaProperties.getHibernateProperties(datasources.get(n)));

            hibernateProps.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

            Set<String> packages = new HashSet<>();
            String[] tmp = MODELS_PACKAGES.split(",");

            for (String t : tmp)
            {
                packages.add(t.trim());
            }

            LocalContainerEntityManagerFactoryBean emfb = builder.dataSource(datasources.get(n))
                    .packages(packages.toArray(new String[packages.size()])).properties(hibernateProps).jta(false)
                    .build();

            emfb.afterPropertiesSet();
            EntityManagerFactory emf = emfb.getObject();
            EntityManager em = emf.createEntityManager();

            final JpaTransactionManager jpaTranMan = new JpaTransactionManager(emf);
            JpaRepositoryFactory jpaFactory = new JpaRepositoryFactory(em);
            jpaFactory.addRepositoryProxyPostProcessor(new MultiJpaRepositoryProxyPostProcessor(jpaTranMan));

            repositoriesFactory.put(n, new ContainerUtil(jpaFactory, emf, em));
        }
    }

    public <T extends JpaRepository<?, ?>> Map<String, T> getRepositories(Class<T> jpaRepositoryClass)
    {
        Map<String, T> tmp = new HashMap<>();

        for (String tenant : repositoriesFactory.keySet())
        {
            tmp.put(tenant, getRepository(jpaRepositoryClass, tenant));
        }

        return tmp;
    }

    public <T extends JpaRepository<?, ?>> T getRepository(Class<T> jpaRepositoryClass, String tenant)
    {
        T repo = null;

        if (repositoriesFactory.containsKey(tenant))
        {
            ContainerUtil factory = repositoriesFactory.get(tenant);
            repo = factory.jpaRepositoryFactory.getRepository(jpaRepositoryClass);

            if (!TransactionSynchronizationManager.hasResource(factory.entityManagerFactory))
            {
                EntityManagerHolder emh = new EntityManagerHolder(factory.entityManager);
                TransactionSynchronizationManager.bindResource(factory.entityManagerFactory, emh);
            }
        }

        return repo;
    }

    public void clearTenants()
    {
        for (String tenant : repositoriesFactory.keySet())
        {
            clearTenant(tenant);
        }
    }

    public void clearTenant(String tenant)
    {
        ContainerUtil factory = repositoriesFactory.get(tenant);

        if (factory.entityManager.getTransaction().isActive())
        {
            factory.entityManager.getTransaction().commit();
        }

        factory.entityManager.clear();
    }

    private class MultiJpaRepositoryProxyPostProcessor implements RepositoryProxyPostProcessor
    {
        private JpaTransactionManager jpaTranMan;

        private MultiJpaRepositoryProxyPostProcessor(JpaTransactionManager jpaTranMan)
        {
            this.jpaTranMan = jpaTranMan;
        }

        @Override
        public void postProcess(ProxyFactory jpaFactory, RepositoryInformation repositoryInformation)
        {
            jpaFactory.addAdvice(new TransactionInterceptor(jpaTranMan, new MatchAlwaysTransactionAttributeSource()));
        }
    }

    private class ContainerUtil
    {
        private JpaRepositoryFactory jpaRepositoryFactory;
        private EntityManagerFactory entityManagerFactory;
        private EntityManager entityManager;

        private ContainerUtil(JpaRepositoryFactory jpaRepositoryFactory, EntityManagerFactory entityManagerFactory,
                EntityManager entityManager)
        {
            this.jpaRepositoryFactory = jpaRepositoryFactory;
            this.entityManagerFactory = entityManagerFactory;
            this.entityManager = entityManager;
        }
    }
}