package com.joaopaulo.helpdesk.api.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails{

	private static final long serialVersionUID = -6864730739027250823L;
	
	private final String id;
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authority;
	
	public JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authority) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
