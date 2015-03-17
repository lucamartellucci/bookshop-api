package com.absontheweb.library.persistence.repository;

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

import com.absontheweb.library.config.PersistenceConfig;
import com.absontheweb.library.model.Currency;
import com.absontheweb.library.persistence.model.BookDBTO;

@ActiveProfiles(profiles = { "dbtest" })
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class BookDBTORepositoryIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	final static Logger logger = LoggerFactory.getLogger(BookDBTORepositoryIntegrationTest.class);

    @Autowired
    private BookDBTORepository repo;
    
    @Test
    public final void testFindOne() throws Exception {
    	executeSqlScript("file:src/test/resources/db/prepareDB.sql", false);
    	BookDBTO bookDBTO = repo.findOne(1L);
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("title_1")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("description_1")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.63)));
    	assertThat(bookDBTO.getYear(), is(equalTo(1978)));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("11111111111")));
    	assertThat(bookDBTO.getAuthors().size(), is(2));
    }
    
    @Test
    public final void testFindByTitle() throws Exception {
    	executeSqlScript("file:src/test/resources/db/prepareDB.sql", false);
    	BookDBTO bookDBTO = repo.findByTitle("title_1");
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("title_1")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("description_1")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.63)));
    	assertThat(bookDBTO.getYear(), is(equalTo(1978)));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("11111111111")));
    	assertThat(bookDBTO.getAuthors().size(), is(2));
    }
	
}
