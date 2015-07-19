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

import com.absontheweb.bookshop.config.PersistenceConfig;
import com.absontheweb.bookshop.persistence.model.UserDBTO;

@ActiveProfiles(profiles = { "dbtest" })
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserDBTORepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	final static Logger logger = LoggerFactory.getLogger(UserDBTORepositoryTest.class);

    @Autowired
    private UserDBTORepository repo;
    
    //id;login;password;first_name;last_name;email;activated;lang_key
    //4;luca;$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K;luca;martellucci;luca.martellucci@absontheweb.com;true;en
    @Test
    public void testFindOne() throws Exception {
    	UserDBTO userDBTO = repo.findOne(4L);
    	assertThat(userDBTO,is(notNullValue()));
    	assertThat(userDBTO.getId(), is(4L));
    	assertThat(userDBTO.getLogin(), is(equalTo("luca")));
    	assertThat(userDBTO.getPassword(), is(equalTo("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K")));
    	assertThat(userDBTO.getFirstName(), is(equalTo("luca")));
    	assertThat(userDBTO.getLastName(), is(equalTo("martellucci")));
    	assertThat(userDBTO.getEmail(), is(equalTo("luca.martellucci@absontheweb.com")));
    	assertThat(userDBTO.getActivated(), is(true));
    	assertThat(userDBTO.getLangKey(), is("en"));
    }
    
}
