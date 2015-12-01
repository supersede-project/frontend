package eu.supersede.fe.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class DatabaseUser extends  org.springframework.security.core.userdetails.User {

	private String multiTenantId;

	public DatabaseUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public void setTenantId(String multiTenantId) {
		this.multiTenantId = multiTenantId;
	}
	
	public String getTenantId()
	{
		return this.multiTenantId;
	}

}
