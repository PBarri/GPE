package com.awg.gpe.web.primefaces.components;

import javax.faces.component.UIComponentBase;

/**
 * @author Pablo Barrientos
 *
 */
public class CardList extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "com.awg.gpe.components.CardList";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return CardList.COMPONENT_FAMILY;
	}
	
	public void setStyle(String style) {
		getStateHelper().put(CardList.PropertyKeys.style, style);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(CardList.PropertyKeys.style);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(CardList.PropertyKeys.styleClass, styleClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(CardList.PropertyKeys.styleClass);
	}
	
	protected enum PropertyKeys {
        style,
        styleClass
    }
}
