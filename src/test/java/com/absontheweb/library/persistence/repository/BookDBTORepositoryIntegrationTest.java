package com.absontheweb.library.persistence.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.absontheweb.library.config.PersistenceConfig;
import com.absontheweb.library.model.Currency;
import com.absontheweb.library.persistence.model.AuthorDBTO;
import com.absontheweb.library.persistence.model.BookDBTO;

@ActiveProfiles(profiles = { "dbtest" })
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class BookDBTORepositoryIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	final static Logger logger = LoggerFactory.getLogger(BookDBTORepositoryIntegrationTest.class);

    @Autowired
    private BookDBTORepository repo;
    
    @Test
    public final void testFindOne() throws Exception {
    	BookDBTO bookDBTO = repo.findOne(1L);
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("Gomorra")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.00)));
    	assertThat(bookDBTO.getYear(), is(equalTo(2008)));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("1234567890")));
    	List<AuthorDBTO> authors = bookDBTO.getAuthors();
		assertThat(authors.size(), is(1));
		AuthorDBTO authorDBTO = authors.get(0);
		assertThat(authorDBTO.getName(), is("Roberto"));
		assertThat(authorDBTO.getSurname(), is("Saviano"));
    }
    
    @Test
    public final void testFindByTitle() throws Exception {
    	BookDBTO bookDBTO = repo.findByTitle("Gomorra");
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("Gomorra")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.00)));
    	assertThat(bookDBTO.getYear(), is(equalTo(2008)));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("1234567890")));
    	List<AuthorDBTO> authors = bookDBTO.getAuthors();
		assertThat(authors.size(), is(1));
		AuthorDBTO authorDBTO = authors.get(0);
		assertThat(authorDBTO.getName(), is("Roberto"));
		assertThat(authorDBTO.getSurname(), is("Saviano"));
    }
	
}
