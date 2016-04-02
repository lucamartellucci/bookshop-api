package com.absontheweb.bookshop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.absontheweb.bookshop.book.model.adapter.UserAdapter;
import com.absontheweb.bookshop.persistence.model.UserDBTO;
import com.absontheweb.bookshop.persistence.repository.UserDBTORepository;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserDBTORepository userRepository;
    @Autowired
    private UserAdapter userAdapter;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {

        logger.debug("Authenticating {}", username);
        String lowercaseUsername = username.toLowerCase();

        UserDBTO userDBTO;
        if(lowercaseUsername.contains("@")) {
            userDBTO = userRepository.findByEmail(lowercaseUsername);
        } else {
            userDBTO = userRepository.findByLoginCaseInsensitive(lowercaseUsername);
        }
        
        logger.debug("UserDBTO {}", userDBTO);

        if (userDBTO == null) {
            throw new UsernameNotFoundException("User " + lowercaseUsername + " was not found in the database");
        } else if (!userDBTO.isEnabled()) {
            throw new UserNotActivatedException("User " + lowercaseUsername + " is not activated");
        }

        return new SimpleUserDetails(userAdapter.toUser(userDBTO));

    }
}