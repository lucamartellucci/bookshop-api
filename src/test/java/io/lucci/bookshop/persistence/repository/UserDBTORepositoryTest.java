package io.lucci.bookshop.persistence.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.lucci.bookshop.persistence.model.UserDBTO;
import io.lucci.bookshop.persistence.repository.UserDBTORepository;
import io.lucci.bookshop.test.base.AbstractDbTest;

public class UserDBTORepositoryTest extends AbstractDbTest {
	
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
