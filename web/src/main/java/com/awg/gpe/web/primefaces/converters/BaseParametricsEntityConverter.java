package com.awg.gpe.web.primefaces.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.awg.gpe.data.model.BaseParametricsEntity;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@FacesConverter("baseParamEntityConverter")
public class BaseParametricsEntityConverter implements Converter {

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String res = "";
        if (value != null && value instanceof BaseParametricsEntity) {
            res = value.toString();
        }
        return res;
    }

}
