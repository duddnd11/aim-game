package com.aim.config.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import com.aim.domain.YnType;

public class UsernameEmailAuthenticationToken extends AbstractAuthenticationToken{
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Object principal;

	private Object credentials;
	
	private Date expiration;
	
	private YnType status;

	public UsernameEmailAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public UsernameEmailAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
	
	public UsernameEmailAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, Date expiration) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.expiration = expiration;
		this.status = YnType.N;
		super.setAuthenticated(true); // must use super, as we override
	}

	public static UsernameEmailAuthenticationToken unauthenticated(Object principal, Object credentials) {
		return new UsernameEmailAuthenticationToken(principal, credentials);
	}

	public static UsernameEmailAuthenticationToken authenticated(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		return new UsernameEmailAuthenticationToken(principal, credentials, authorities);
	}
	
	public static UsernameEmailAuthenticationToken authenticated(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, Date expiration) {
		return new UsernameEmailAuthenticationToken(principal, credentials, authorities, expiration);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		Assert.isTrue(!isAuthenticated,
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public void changeStatusY() {
		this.status=YnType.Y;
	}
	
	public YnType getStatus() {
		return this.status;
	}

}
