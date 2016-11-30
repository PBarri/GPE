package com.awg.gpe.web.primefaces.components;

import javax.faces.component.UIComponentBase;

/**
 * @author Pablo Barrientos
 *
 */
public class Cards extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "com.awg.gpe.components.Cards";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return Cards.COMPONENT_FAMILY;
	}
	
	public void setValue(Object value) {
		getStateHelper().put(Cards.PropertyKeys.value, value);
	}

	public Object getValue() {
		return getStateHelper().eval(Cards.PropertyKeys.value);
	}

	public void setStyle(String style) {
		getStateHelper().put(Cards.PropertyKeys.style, style);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(Cards.PropertyKeys.style);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(Cards.PropertyKeys.styleClass, styleClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(Cards.PropertyKeys.styleClass);
	}

	public void setType(String type) {
		getStateHelper().put(Cards.PropertyKeys.type, type);
	}

	public String getType() {
		return (String) getStateHelper().eval(Cards.PropertyKeys.type);
	}

	public void setCardStyle(String cardStyle) {
		getStateHelper().put(Cards.PropertyKeys.cardStyle, cardStyle);
	}

	public String getCardStyle() {
		return (String) getStateHelper().eval(Cards.PropertyKeys.cardStyle);
	}
	
	protected enum PropertyKeys {
        value,
        style,
        styleClass,
        type,
        cardStyle
    }

}
