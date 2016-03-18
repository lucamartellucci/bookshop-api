package com.absontheweb.bookshop.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component(value="roleResolver")
public class RolesByFunctionResolver {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RolesByFunctionResolver.class);
	
	private Map<String,List<String>> rolesByFunctionMap;
	
	public RolesByFunctionResolver() {
		rolesByFunctionMap = new HashMap<>();
		rolesByFunctionMap.put("GET_BOOK", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
		rolesByFunctionMap.put("ADD_BOOK", Arrays.asList("ROLE_ADMIN"));
	}
	
	public List<String> getRolesFor(String function) {
		return this.rolesByFunctionMap.get(function);
	}
	
	public boolean isAuthorized(String function, List<String> roles) {
		logger.debug("verify roles {} for function {}", roles, function);
		List<String> authorizedRoles = this.rolesByFunctionMap.get(function);
		for (String role : roles) {
			if (authorizedRoles.contains(role)){
				return true;
			} 
		}
		return false;
	}
	
	public boolean isAuthorityAuthorized(String function, Collection<GrantedAuthority> authorities) {
		logger.debug("verify authorities {} for function {}", authorities, function);
		List<String> authorizedRoles = this.rolesByFunctionMap.get(function);
		for (GrantedAuthority authority : authorities) {
			if (authorizedRoles.contains(authority.getAuthority())){
				return true;
			} 
		}
		return false;
	}
	
}
