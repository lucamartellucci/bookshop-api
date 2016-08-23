package io.lucci.bookshop.persistence.converter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class LocalDatePersistenceConverterTest {
	
	private LocalDatePersistenceConverter converter;
	
	@Before
	public void setUp(){
		converter = new LocalDatePersistenceConverter();
	}

	@Test
	public void testConvertToDatabaseColumn() throws Exception {
		// test for null value
		assertThat(converter.convertToDatabaseColumn(null),is(nullValue()));
		
		// test for a given date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate myLocalDate = LocalDate.of(2016, 4, 6);
		Date convertedDate = converter.convertToDatabaseColumn(myLocalDate);
		assertThat(sdf.format(convertedDate),is("06/04/2016"));
	}

	@Test
	public void testConvertToEntityAttribute() throws Exception {
		// test for null value
		assertThat(converter.convertToEntityAttribute(null),is(nullValue()));
				
		// test for a given date
		LocalDate localDate = converter.convertToEntityAttribute(
				Date.valueOf("2010-01-02"));
		assertThat(localDate.getYear(),is(2010));
		assertThat(localDate.getMonthValue(),is(1));
		assertThat(localDate.getDayOfMonth(),is(2));
	}

}
