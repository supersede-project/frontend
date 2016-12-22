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

package eu.supersede.fe.template.session;

import java.util.Collection;

public class DatabaseUser
{
    private Long userId;
    private String name;
    private String tenantId;
    private String locale;

    // Token serialized
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String accessToken;

    // inherited from org.springframework.security.core.userdetails.User
    private String password;
    private String username;
    private Collection<Authority> authorities;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;

    public DatabaseUser()
    {
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return this.tenantId;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getLocale()
    {
        return this.locale;
    }

    public void setToken(AuthorizationToken token)
    {
        if (token != null)
        {
            accessToken = token.getAccessToken();
            expiresIn = token.getExpiresIn();
            refreshToken = token.getRefreshToken();
            tokenType = token.getTokenType();
        }
    }

    public AuthorizationToken getToken()
    {
        AuthorizationToken t = null;

        if (accessToken != null)
        {
            t = new AuthorizationToken();
            t.setAccessToken(accessToken);
            t.setExpiresIn(expiresIn);
            t.setRefreshToken(refreshToken);
            t.setTokenType(tokenType);
        }

        return t;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn()
    {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Collection<Authority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities)
    {
        this.authorities = authorities;
    }

    public Boolean getAccountNonExpired()
    {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired)
    {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked()
    {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked)
    {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired)
    {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }
}