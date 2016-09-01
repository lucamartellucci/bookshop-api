package io.lucci.bookshop.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.lucci.bookshop.model.User;
/*
 * Utility Object that wraps the User object.
 */
public class SimpleUserDetails implements UserDetails {
	
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Collection<GrantedAuthority> autorities;

	public SimpleUserDetails(){ 
		user = new User();
		autorities = Collections.emptyList();
	}
	
	public SimpleUserDetails(User user){
		this.user = user;
		this.autorities = buildAutorities(user.getRoles());
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorities;
	}
	public void setAutorities(Collection<GrantedAuthority> autorities) {
		this.autorities = autorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	// NOT MANAGED
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// utilities
	private Collection<GrantedAuthority> buildAutorities(List<String> roles) {
		if (roles==null || roles.isEmpty()) {
			return AuthorityUtils.NO_AUTHORITIES;
		} else {
			Collection<GrantedAuthority> autorities = new ArrayList<>();
			for (String role : user.getRoles()) {
				autorities.add(new SimpleGrantedAuthority(role));
			}
			return autorities;
		}
	}

}
