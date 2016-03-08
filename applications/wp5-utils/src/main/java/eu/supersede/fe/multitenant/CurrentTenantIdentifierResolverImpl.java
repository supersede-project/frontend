package eu.supersede.fe.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@PropertySource("file:../conf/multitenancy.properties")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${application.multitenancy.default}")
	private String DEFAULT_TENANT_ID;
	
	@Override
	public String resolveCurrentTenantIdentifier()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) 
		{
			String identifier = (String) requestAttributes.getAttribute("CURRENT_TENANT_IDENTIFIER", RequestAttributes.SCOPE_REQUEST);
			if (identifier != null) {
				
				log.debug("returning tenant: " + identifier);
				return identifier;
			}
		}
		
		log.debug("returning default tenant: " + DEFAULT_TENANT_ID);
		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}