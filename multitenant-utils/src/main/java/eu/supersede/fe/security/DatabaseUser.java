package eu.supersede.fe.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class DatabaseUser extends  org.springframework.security.core.userdetails.User {

	private Long userId;
	private String multiTenantId;

	public DatabaseUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setTenantId(String multiTenantId) {
		this.multiTenantId = multiTenantId;
	}
	
	public String getTenantId()
	{
		return this.multiTenantId;
	}
}
