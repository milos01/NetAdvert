package com.mmmp.NetAdvert;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class MockSecurityContext implements SecurityContext {

	private static final long serialVersionUID = 5983993172649733991L;
	
	private Authentication authentication;
	
	public MockSecurityContext(Authentication authentication) {
		this.authentication = authentication;
	}
	
	@Override
	public Authentication getAuthentication() {
		return this.authentication;
	}

	@Override
	public void setAuthentication(Authentication arg0) {
		this.authentication = arg0;
	}

}
