package eu.supersede.fe.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfRequestMatcher implements RequestMatcher {

	private String[] permitUrls;
	private String[] allowedMethods = {"GET", "HEAD", "TRACE", "OPTIONS"};
	
	private static final List<RequestMatcher> matchers = new ArrayList<RequestMatcher>();
	
	public CsrfRequestMatcher(String[] permitUrls)
	{
		this.permitUrls = permitUrls;
		for(String m : allowedMethods)
		{
			matchers.add(new AntPathRequestMatcher("/**" , m));
		}
		for(String u : this.permitUrls)
		{
			matchers.add(new AntPathRequestMatcher(u , null));
		}
	}
	
	public boolean matches(HttpServletRequest request) {
		for(RequestMatcher rm : matchers)
		{
			if(rm.matches(request))
			{
				return false;
			}
		}

        // CSRF for everything else that is not an API call or an allowedMethod
        return true;
	}
	
}
