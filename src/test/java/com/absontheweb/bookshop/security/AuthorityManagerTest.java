package com.absontheweb.bookshop.security;

import static org.mockito.Mockito.reset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { AuthorityManagerTestConfig.class })
public class AuthorityManagerTest {
	
	@Autowired
	Environment env;
	
	@Before
	public void setUp(){
		reset(env);
	}

	@Test
	public void testGetAuthorityBy() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testIsAuthorized() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
