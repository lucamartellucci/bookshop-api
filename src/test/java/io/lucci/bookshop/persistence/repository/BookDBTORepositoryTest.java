package io.lucci.bookshop.persistence.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.lucci.bookshop.book.model.Currency;
import io.lucci.bookshop.model.StorageProvider;
import io.lucci.bookshop.persistence.model.AuthorDBTO;
import io.lucci.bookshop.persistence.model.BookDBTO;
import io.lucci.bookshop.test.base.AbstractDbTest;

public class BookDBTORepositoryTest extends AbstractDbTest {
	
    @Autowired
    private BookDBTORepository repo;
    
    @Autowired
    private AuthorDBTORepository repoAuthor;
    
    @Test
    public void testFindOne() throws Exception {
    	BookDBTO bookDBTO = repo.findOne(1L);
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("Gomorra")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.00)));
    	assertThat(bookDBTO.getReleaseDate(), is(equalTo(LocalDate.of(2008,11,02))));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("1234567890")));
    	List<AuthorDBTO> authors = bookDBTO.getAuthors();
		assertThat(authors.size(), is(1));
		AuthorDBTO authorDBTO = authors.get(0);
		assertThat(authorDBTO.getName(), is("Roberto"));
		assertThat(authorDBTO.getSurname(), is("Saviano"));
    }
    
    @Test
    public void testFindOneNotExisting() throws Exception {
    	BookDBTO bookDBTO = repo.findOne(1000L);
    	assertThat(bookDBTO,is(nullValue()));
    }
    
    @Test
    public void testFindByTitle() throws Exception {
    	BookDBTO bookDBTO = repo.findByTitle("Gomorra");
    	assertThat(bookDBTO,is(notNullValue()));
    	assertThat(bookDBTO.getTitle(), is(equalTo("Gomorra")));
    	assertThat(bookDBTO.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(bookDBTO.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(bookDBTO.getPrice(), is(equalTo(12.00)));
    	assertThat(bookDBTO.getReleaseDate(), is(equalTo(LocalDate.of(2008, 11, 02))));
    	assertThat(bookDBTO.getIsbn(), is(equalTo("1234567890")));
    	List<AuthorDBTO> authors = bookDBTO.getAuthors();
		assertThat(authors.size(), is(1));
		AuthorDBTO authorDBTO = authors.get(0);
		assertThat(authorDBTO.getName(), is("Roberto"));
		assertThat(authorDBTO.getSurname(), is("Saviano"));
    }
    
    @Test
    public void testCreateBook() throws Exception {
    	AuthorDBTO authorDBTO = repoAuthor.findOne(1L);
    	
    	final String title = "title01";
    	final String description = "desc01";
    	final String isbn = "11111111";
    	final String name = "name001";
    	final LocalDate releasedOn = LocalDate.of(2011, 01, 01);
    	final StorageProvider local = StorageProvider.LOCAL;
    	final Currency euro = Currency.EUR;
    	final double price = 11.0;
    	
    	BookDBTO bookDBTO = new BookDBTO();
		bookDBTO.setTitle(title);
		bookDBTO.setDescription(description);
		bookDBTO.setCurrency(euro);
		bookDBTO.setIsbn(isbn);
		bookDBTO.setPrice(price);
		bookDBTO.setReleaseDate(releasedOn);
    	bookDBTO.setAuthors(Arrays.asList(authorDBTO));
		bookDBTO.setCoverFileName(name);
		bookDBTO.setCoverFileLocation(local);
    	
    	BookDBTO savedBookDBTO = repo.save(bookDBTO);
    	
    	assertThat(savedBookDBTO,is(notNullValue()));
    	assertThat(savedBookDBTO.getTitle(), is(equalTo(title)));
    	assertThat(savedBookDBTO.getDescription(), is(equalTo(description)));
    	assertThat(savedBookDBTO.getCurrency(), is(equalTo(euro)));
    	assertThat(savedBookDBTO.getPrice(), is(equalTo(price)));
    	assertThat(savedBookDBTO.getReleaseDate(), is(equalTo(releasedOn)));
    	assertThat(savedBookDBTO.getIsbn(), is(equalTo(isbn)));
    	assertThat(savedBookDBTO.getCoverFileLocation(),is(local));
    	assertThat(savedBookDBTO.getCoverFileName(),is(name));
		assertThat(bookDBTO.getAuthors().size(), is(1));
    }
	
}
