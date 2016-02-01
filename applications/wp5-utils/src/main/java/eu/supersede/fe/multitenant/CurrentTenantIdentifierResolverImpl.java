package eu.supersede.fe.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@PropertySource("file:../conf/multitenancy.properties")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

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
		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}