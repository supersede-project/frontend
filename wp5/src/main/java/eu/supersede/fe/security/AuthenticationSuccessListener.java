package eu.supersede.fe.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
  
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
    	
    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	DatabaseUser userDetails = (DatabaseUser)event.getAuthentication().getPrincipal();
        
        HttpServletRequest req = attr.getRequest();
        String multiTenantId = req.getHeader("TenantId");
        userDetails.setTenantId(multiTenantId);
        
        log.debug("Setting tenant in userDetails: " + multiTenantId);
    }
}