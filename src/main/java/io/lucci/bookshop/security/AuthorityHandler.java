package io.lucci.bookshop.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component(value="authorityHandler")
@ConfigurationProperties(prefix="security")
public class AuthorityHandler implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityHandler.class);

	// the authority map is automatically injected by the container (from application.yml)
	private Map<String,String> authorities;

	
	private Map<String,Set<String>> authsByFunctionMap;
	
	public boolean isAuthorized(UserDetails p, String function) {
		boolean authotized = false;
		// get user authorities from the principal
		Collection<? extends GrantedAuthority> userAuthorities = p.getAuthorities();
		
		// retrieve the authorities that support the function
		Set<String> authorizedRoles = authsByFunctionMap.get(function);
		
		for (GrantedAuthority authority : userAuthorities) {
			if (authorizedRoles.contains(authority.getAuthority())){
				authotized = true;
			} 
		}
		
		LOGGER.debug("Function {}, Authorities {} is authorized -> [{}]", 
				new Object[] {function, userAuthorities, authotized});
		
		return authotized;
	}

	public void setAuthorities(Map<String, String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.authsByFunctionMap = new HashMap<>();
		for (Entry<String,String>  entry: authorities.entrySet()) {
			String[] functionalities = entry.getValue().split("\\,");
			for (String functionality : functionalities) {
				if (authsByFunctionMap.containsKey(functionality)) {
					authsByFunctionMap.get(functionality).add(entry.getKey());
				} else {
					HashSet<String> functionalitySet = new HashSet<>();
					functionalitySet.add(entry.getKey());
					authsByFunctionMap.put(functionality, functionalitySet);
				}
			}
		}
		LOGGER.info("Authority Handler ready!");
		LOGGER.info("Roles are: {}", authorities);
		LOGGER.info("Authorities are: {}", authsByFunctionMap);
	}

	public Set<String> getAuthoritiesFor(String function) {
		return authsByFunctionMap.get(function) ;
	}

	public Map<String, String> getAuthorities() {
		return authorities;
	}
	
	
}
