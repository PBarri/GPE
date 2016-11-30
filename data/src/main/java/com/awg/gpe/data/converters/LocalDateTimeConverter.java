package com.awg.gpe.data.converters;

import java.time.Instant;
import java.time.LocalDateTime;
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
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    /**
     * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public Date convertToDatabaseColumn(LocalDateTime dateTime) {
        return (dateTime != null) ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
     * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Date value) {
        return (value != null) ? LocalDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()), ZoneId.systemDefault()) : null;
    }

}
