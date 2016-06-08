/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import eu.supersede.integration.api.security.types.AuthorizationToken;

@SuppressWarnings("serial")
public class DatabaseUser extends  org.springframework.security.core.userdetails.User {

	private Long userId;
	private String name;
	private String multiTenantId;
	private String locale;
	//Token serialized
	private String tokenType;
	private Integer expiresIn;
	private String refreshToken;
	private String accessToken;


	public DatabaseUser(Long userId, String name, String username, String password, AuthorizationToken token, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String locale) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.name = name;
		this.locale = locale;

		setToken(token);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setTenantId(String multiTenantId) {
		this.multiTenantId = multiTenantId;
	}
	
	public String getTenantId()
	{
		return this.multiTenantId;
	}
	
	public void setLocale(String locale)
	{
		this.locale = locale;
	}
	
	public String getLocale()
	{
		return this.locale;
	}
	
	public void setToken(AuthorizationToken token) {
		accessToken = token.getAccessToken();
		expiresIn = token.getExpiresIn();
		refreshToken = token.getRefreshToken();
		tokenType = token.getTokenType();
	}
	
	public AuthorizationToken getToken() {
		AuthorizationToken t = new AuthorizationToken();
		t.setAccessToken(accessToken);
		t.setExpiresIn(expiresIn);
		t.setRefreshToken(refreshToken);
		t.setTokenType(tokenType);
		
		return t;
	}
}
