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
    	assertThat(userDBTO.getPassword(), is(equalTo("cb7c16bdaf2f6445988230b8862290fed48650be99e808ce68f6a12f905199af0ce2659148d69b8a")));
    	assertThat(userDBTO.getFirstname(), is(equalTo("luca")));
    	assertThat(userDBTO.getLastname(), is(equalTo("martellucci")));
    	assertThat(userDBTO.getEmail(), is(equalTo("luca.martellucci@email.com")));
    	assertThat(userDBTO.isEnabled(), is(true));
    	assertThat(userDBTO.getLangKey(), is("en"));
    }
    
}
