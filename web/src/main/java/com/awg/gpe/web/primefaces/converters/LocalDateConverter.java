package com.awg.gpe.web.primefaces.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@FacesConverter(value = "localDateConverter")
public class LocalDateConverter implements Converter {

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LocalDate date = (LocalDate) value;
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
