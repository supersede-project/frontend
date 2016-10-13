package org.springframework.security.web.csrf;

import org.springframework.util.Assert;

@SuppressWarnings("serial")
public final class DefaultCsrfToken implements CsrfToken {

	private final String token;

	private final String parameterName;

	private final String headerName;

	public DefaultCsrfToken(String headerName, String parameterName, String token) {
		Assert.hasLength(headerName, "headerName cannot be null or empty");
		Assert.hasLength(parameterName, "parameterName cannot be null or empty");
		Assert.hasLength(token, "token cannot be null or empty");
		this.headerName = headerName;
		this.parameterName = parameterName;
		this.token = token;
	}

	public String getHeaderName() {
		return headerName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public String getToken() {
		return token;
	}
}