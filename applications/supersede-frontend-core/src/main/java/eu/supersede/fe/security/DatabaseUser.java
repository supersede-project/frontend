package eu.supersede.fe.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class DatabaseUser extends  org.springframework.security.core.userdetails.User {

	private Long userId;
	private String name;
	private String multiTenantId;
	private String locale;

	public DatabaseUser(Long userId, String name, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String locale) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.name = name;
		this.locale = locale;
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
}
