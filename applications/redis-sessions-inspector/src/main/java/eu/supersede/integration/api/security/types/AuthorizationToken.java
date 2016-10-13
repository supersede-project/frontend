package eu.supersede.integration.api.security.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationToken {
	@JsonProperty ("token_type")
	String tokenType;
	@JsonProperty ("expires_in")
	int expiresIn;
	@JsonProperty ("refresh_token")
	String refreshToken;
	@JsonProperty ("access_token")
	String accessToken;
	
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}