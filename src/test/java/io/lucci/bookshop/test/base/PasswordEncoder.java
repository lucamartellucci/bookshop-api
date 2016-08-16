package io.lucci.bookshop.test.base;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoder {
	
	private static Logger logger = LoggerFactory.getLogger(PasswordEncoder.class);
	
	@Test
	public void encodePassword() throws Exception {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String result = encoder.encode("password01");
		assertTrue(encoder.matches("password01", result));
		logger.info("Encoded password is {}", result);
		assertThat(result,is(notNullValue()));
	}

}
