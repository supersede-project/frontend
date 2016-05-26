package eu.supersede.fe.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@PropertySources({
	@PropertySource("file:../conf/multitenancy.properties"),
	@PropertySource(value = "file:../conf/multitenancy_${application.name}.properties", ignoreResourceNotFound=true)
  })
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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