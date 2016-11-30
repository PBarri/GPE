package com.awg.gpe.data.converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convertidor para poder usar las clases de java.time
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    /**
     * 
     * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        // Instant instant = Instant.from(date);
        // return Date.from(instant);
        return (date != null) ? Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
     * 
     * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        // Instant instant = value.toInstant();
        // return LocalDate.from(instant);
        return (value != null) ? value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }
}
