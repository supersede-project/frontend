package eu.supersede.fe.multitenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception 
	{
		String multiTenantId = null;
		
		HttpSession session = req.getSession(false);
		if(session != null && session.getAttribute("TenantId") != null)
		{
			multiTenantId = (String)session.getAttribute("TenantId");
			log.debug("Reading tenant from session: " + multiTenantId);
		}
		else
		{
			multiTenantId = req.getHeader("TenantId");
		}
		
		if(multiTenantId != null)
		{
			req.setAttribute("CURRENT_TENANT_IDENTIFIER", multiTenantId);
		}
		return true;
	}
}