package com.absontheweb.bookshop.persistence.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.absontheweb.bookshop.application.PersistenceConfig;
import com.absontheweb.bookshop.persistence.model.UserDBTO;

@ActiveProfiles(profiles = { "dbtest" })
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserDBTORepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	final static Logger logger = LoggerFactory.getLogger(UserDBTORepositoryTest.class);

    @Autowired
    private UserDBTORepository repo;
    
    @Test
    public void testFindOne() throws Exception {
    	UserDBTO userDBTO = repo.findOne(4L);
    	assertThat(userDBTO,is(notNullValue()));
    	assertThat(userDBTO.getId(), is(4L));
    	assertThat(userDBTO.getUsername(), is(equalTo("luca")));
    	assertThat(userDBTO.getPassword(), is(equalTo("b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1")));
    	assertThat(userDBTO.getFirstname(), is(equalTo("luca")));
    	assertThat(userDBTO.getLastname(), is(equalTo("martellucci")));
    	assertThat(userDBTO.getEmail(), is(equalTo("luca.martellucci@absontheweb.com")));
    	assertThat(userDBTO.isEnabled(), is(true));
    	assertThat(userDBTO.getLangKey(), is("en"));
    }
    
}
