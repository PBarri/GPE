package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;
import org.springframework.util.StringUtils;

import com.awg.gpe.web.primefaces.components.Card;
import com.awg.gpe.web.primefaces.components.CardList;
import com.awg.gpe.web.primefaces.components.Cards;

/**
 * @author Pablo Barrientos
 *
 */
public class CardListRenderer extends CoreRenderer {
	
	public static final String RENDERER_TYPE = "com.awg.gpe.renderers.CardListRenderer";
	public static final String TYPE_USER = "user";
	public static final String TYPE_SERVER = "server";
	
    protected ResourceBundle messages;
    
    protected CardRenderer cardRenderer;
    protected CardsRenderer cardsRenderer;    
	
    @Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		super.encodeBegin(context, component);
        this.messages = context.getApplication().getResourceBundle(context, "msg");
        this.cardRenderer = new CardRenderer();
        this.cardsRenderer = new CardsRenderer();
        CardList cardList = (CardList) component; 
        encodeBeginMarkup(context, cardList);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		super.encodeEnd(context, component);
		if (component.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			writer.endElement("div");
		}		
    }

	protected void encodeBeginMarkup(FacesContext context, CardList cardList) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = cardList.getClientId(context);
		String styleClass = "minimal-card-list";

		if (StringUtils.hasText(cardList.getStyleClass())) {
			styleClass += " " + cardList.getStyleClass();
		}
		
		if (cardList.isRendered()) {
			writer.startElement("div", null);
			writer.writeAttribute("id", clientId, null);
			writer.writeAttribute("class", styleClass, "styleClass");
			
			if (cardList.getStyle() != null) {
				writer.writeAttribute("style", cardList.getStyle(), "style");
			}
			
			renderDynamicPassThruAttributes(context, cardList);
						
		}
	}
	
	@Override
	protected void renderChild(FacesContext context, UIComponent child) throws IOException {
		
		if (!child.isRendered()) {
            return;
        }
		
		child.encodeBegin(context);

        if (child.getRendersChildren()) {
            child.encodeChildren(context);
        } else {
            renderChildren(context, child);
        }
		
		if (child instanceof Card) {
            this.cardRenderer.encodeEnd(context, child);
		} else if (child instanceof Cards) {
            this.cardsRenderer.encodeEnd(context, child);
		} else {
			child.encodeEnd(context);
		}
	}
}
