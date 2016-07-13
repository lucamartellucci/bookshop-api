package io.lucci.bookshop.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.lucci.bookshop.controller.exception.InternalServerErrorException;
import io.lucci.bookshop.controller.resolver.CurrentUser;
import io.lucci.bookshop.model.User;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/me", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getBook(@CurrentUser User user) throws InternalServerErrorException {
		
		logger.debug("Current user is: {}", user);
		return ResponseEntity.ok(user);
	}
	
}
