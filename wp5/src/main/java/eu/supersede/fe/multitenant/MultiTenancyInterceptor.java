package eu.supersede.fe.multitenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception 
	{
		String multiTenantId = req.getHeader("TenantId");

		if(multiTenantId != null)
		{
			req.setAttribute("CURRENT_TENANT_IDENTIFIER", multiTenantId);
		}
		return true;
	}
}