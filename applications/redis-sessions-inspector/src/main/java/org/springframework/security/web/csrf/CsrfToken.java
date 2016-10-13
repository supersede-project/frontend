package org.springframework.security.web.csrf;

import java.io.Serializable;

public interface CsrfToken extends Serializable {
	
	String getHeaderName();
	
	String getParameterName();
	
	String getToken();
}
