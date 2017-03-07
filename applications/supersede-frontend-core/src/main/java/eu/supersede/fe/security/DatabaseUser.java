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

/**
 * User for databases.
 */
@SuppressWarnings("serial")
public class DatabaseUser extends org.springframework.security.core.userdetails.User
{
    private Long userId;
    private String name;
    private String multiTenantId;
    private String locale;
    // Token serialized
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String accessToken;

    /**
     * Default constructor.
     * @param userId
     * @param name
     * @param username
     * @param password
     * @param token
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     * @param locale
     */
    public DatabaseUser(Long userId, String name, String username, String password, AuthorizationToken token,
            boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities, String locale)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.name = name;
        this.locale = locale;
        setToken(token);
    }

    /**
     * Return the id of the user.
     */
    public Long getUserId()
    {
        return userId;
    }

    /**
     * Set the id of the user.
     * @param userId
     */
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
     * Return the name of the user.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the name of the user.
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return the tenant identifier for the user.
     */
    public String getTenantId()
    {
        return this.multiTenantId;
    }

    /**
     * Set the tenant identifier for the user.
     * @param multiTenantId
     */
    public void setTenantId(String multiTenantId)
    {
        this.multiTenantId = multiTenantId;
    }

    /**
     * Set the given locale for the user.
     * @param locale
     */
    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    /**
     * Return the locale for the user.
     */
    public String getLocale()
    {
        return this.locale;
    }

    /**
     * Set the given token used by the user for authentication.
     * @param token
     */
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

    /**
     * Return the token used by the user for authentication.
     */
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
}