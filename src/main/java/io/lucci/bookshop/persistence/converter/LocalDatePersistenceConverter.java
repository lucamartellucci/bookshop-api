package io.lucci.bookshop.persistence.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		if (attribute == null) {
			return null;
		} else {
			return Date.valueOf(attribute);
		}
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		if (dbData == null) {
			return null;
		} else {
			return dbData.toLocalDate();
		}
	}

}
