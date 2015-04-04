package com.infoinsightsllc.guardJSON;

public class GuardJSONToken 
{
	private String access_token = "";
	private String token_type = "";
	private int expires_in = 0;
	private String scope = "";

	public GuardJSONToken() {
	    // no-args constructor
	  }
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String tokeb_type) {
		this.token_type = tokeb_type;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
