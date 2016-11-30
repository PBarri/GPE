package com.awg.gpe.web.primefaces.converters;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.awg.gpe.data.model.BaseEntity;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@FacesConverter("baseEntityConverter")
public class BaseEntityConverter implements Converter {

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object res = null;
        if (component != null && component instanceof SelectOneMenu) {
            SelectOneMenu select = (SelectOneMenu) component;
            parent_loop: for (UIComponent child : select.getChildren()) {
                if (child instanceof UISelectItems) {
                    UISelectItems items = (UISelectItems) child;
                    Object itemsValue = items.getValue();
                    if (itemsValue instanceof Iterable) {
                        for (Object be : (Iterable<?>) itemsValue) {
                            if (be instanceof BaseEntity) {
                                BaseEntity entity = (BaseEntity) be;
                                if (entity.toString().equals(value)) {
                                    res = entity;
                                    break parent_loop;
                                }
                            }

                        }
                    }
                    break;
                }
            }
        } else if (component != null && component instanceof SelectCheckboxMenu) {
            SelectCheckboxMenu select = (SelectCheckboxMenu) component;
            parent_loop: for (UIComponent child : select.getChildren()) {
                if (child instanceof UISelectItems) {
                    UISelectItems items = (UISelectItems) child;
                    Object itemsValue = items.getValue();
                    if (itemsValue instanceof Iterable) {
                        for (Object be : (Iterable<?>) itemsValue) {
                            if (be instanceof BaseEntity) {
                                BaseEntity entity = (BaseEntity) be;
                                if (entity.toString().equals(value)) { 
                                    res = entity;
                                    break parent_loop;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return res;
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String res = "";
        if (value != null && value instanceof BaseEntity) {
            res = value.toString();
        }
        return res;
    }

}
