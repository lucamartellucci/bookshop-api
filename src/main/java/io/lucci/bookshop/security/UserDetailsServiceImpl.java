package io.lucci.bookshop.security;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.lucci.bookshop.book.model.adapter.UserAdapter;
import io.lucci.bookshop.persistence.model.UserDBTO;
import io.lucci.bookshop.persistence.repository.UserDBTORepository;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDBTORepository userRepository;
    @Autowired
    private UserAdapter userAdapter;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        LOGGER.debug("Authenticating {}", username);
        String lowercaseUsername = username.toLowerCase();

        
        UserDBTO userDBTO = null;
        try {
	        if(lowercaseUsername.contains("@")) {
	            userDBTO = userRepository.findByEmail(lowercaseUsername);
	        } else {
	            userDBTO = userRepository.findByLoginCaseInsensitive(lowercaseUsername);
	        }
        } catch(Exception e) {
        	throw new UsernameNotFoundException(String.format("Unable to find user %s", lowercaseUsername), e);
        }
        
        
        LOGGER.debug("UserDBTO {}", userDBTO);
        if (userDBTO == null) {
        	throw new UsernameNotFoundException(String.format("User %s was not found in the database", lowercaseUsername));
        } 

        return new SimpleUserDetails(userAdapter.toUser(userDBTO));

    }
}