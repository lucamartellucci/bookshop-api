package io.lucci.bookshop.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component(value="authmgr")
public class AuthorityManager {
	
	@Resource
	private Environment environment;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorityManager.class);
	
	private Map<String,List<String>> authsByFunctionMap;
	
	public AuthorityManager() {
		authsByFunctionMap = new HashMap<>();
	}

	
	public boolean isAuthorized(UserDetails p, String function) {
		boolean authotized = false;
		// get user authorities from the principal
		Collection<? extends GrantedAuthority> userAuthorities = p.getAuthorities();
		
		
		// retrieve the authorities that support the function
		List<String> authorizedRoles = getAuthoritiesFor(function);
		
		for (GrantedAuthority authority : userAuthorities) {
			if (authorizedRoles.contains(authority.getAuthority())){
				authotized = true;
			} 
		}
		
		logger.debug("Function {}, Authorities {} is authorized -> [{}]", 
				new Object[] {function, userAuthorities, authotized});
		
		return authotized;
	}

	protected List<String> getAuthoritiesFor(String function) {
		if (this.authsByFunctionMap.containsKey(function)) {
			return authsByFunctionMap.get(function);
		} else {
			//access the environment
			String stringAuthorities = environment.getProperty(function);
			Preconditions.checkNotNull(stringAuthorities, "Authorities not found for ".concat(function));
			List<String> authorities = Arrays.asList(stringAuthorities.split("\\,"));
			authsByFunctionMap.put(function, authorities);
			return authorities;
		}
	}
	
}
