package eu.supersede.fe.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@PropertySource("file:../conf/multitenancy.properties")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${application.multitenancy.default}")
	private String DEFAULT_TENANT_ID;
	
	@Override
	public String resolveCurrentTenantIdentifier()
	{
		log.debug("resolve tenant");
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) 
		{
			String identifier = (String) requestAttributes.getAttribute("CURRENT_TENANT_IDENTIFIER", RequestAttributes.SCOPE_REQUEST);
			if (identifier != null) {
				
				log.debug("returning tenant: " + identifier);
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
				log.debug("returning tenant from header: " + multiTenantId);
				return multiTenantId;
			}
		}
		catch(IllegalStateException ex)
		{
			//throw if no request were make (????)
		}

		log.debug("returning default tenant: " + DEFAULT_TENANT_ID);
		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}