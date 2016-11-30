package com.awg.gpe.web.primefaces.components;

import javax.faces.component.UIComponentBase;

/**
 * @author Pablo Barrientos
 *
 */
public class Card extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "com.awg.gpe.components.Card";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return Card.COMPONENT_FAMILY;
	}
	
	public void setValue(Object value) {
		getStateHelper().put(Card.PropertyKeys.value, value);
	}

	public Object getValue() {
		return getStateHelper().eval(Card.PropertyKeys.value);
	}

	public void setStyle(String style) {
		getStateHelper().put(Card.PropertyKeys.style, style);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(Card.PropertyKeys.style);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(Card.PropertyKeys.styleClass, styleClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(Card.PropertyKeys.styleClass);
	}

	public void setType(String type) {
		getStateHelper().put(Card.PropertyKeys.type, type);
	}

	public String getType() {
		return (String) getStateHelper().eval(Card.PropertyKeys.type);
	}
	
	protected enum PropertyKeys {
        value,
        style,
        styleClass,
        type
    }

}
