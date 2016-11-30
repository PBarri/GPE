package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;

import com.awg.gpe.data.model.TGpeMServer;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.web.primefaces.components.Card;
import com.awg.gpe.web.primefaces.components.Cards;

/**
 * @author Pablo Barrientos
 *
 */
public class CardsRenderer extends CoreRenderer {
	
	public static final String RENDERER_TYPE = "com.awg.gpe.renderers.CardsRenderer";
	public static final String TYPE_USER = "user";
	public static final String TYPE_SERVER = "server";
	
    protected ResourceBundle messages;
    
    protected CardRenderer childRenderer;
	
	@Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        this.messages = context.getApplication().getResourceBundle(context, "msg");
        this.childRenderer = new CardRenderer();
        Cards cards = (Cards) component; 
        encodeMarkup(context, cards);
    }

	@SuppressWarnings("unchecked")
	protected void encodeMarkup(FacesContext context, Cards cards) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		
		if (cards.isRendered()) {
			
			if (cards.getType().equals(CardsRenderer.TYPE_USER)) {
				Collection<TGpeMUser> users = (Collection<TGpeMUser>) cards.getValue();
				encodeUsers(context, cards, users);
			} else if (cards.getType().equals(CardsRenderer.TYPE_SERVER)) {
				Collection<TGpeMServer> servers = (Collection<TGpeMServer>) cards.getValue();
				encodeServers(context, cards, servers);
			}
			
			renderChildren(context, cards);
			
			writer.endElement("div");
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
            this.childRenderer.encodeEnd(context, child);
		} else {
			child.encodeEnd(context);
		}
	}

	private List<UIComponentBase> encodeUsers(FacesContext context, Cards cards, Collection<TGpeMUser> users) throws IOException {
		List<UIComponentBase> res = new ArrayList<>();
		if (!users.isEmpty()) {
			for (TGpeMUser user : users) {
				Card card = new Card();
				card.setValue(user);
				card.setType(CardsRenderer.TYPE_USER);
				card.setStyleClass(cards.getCardStyle());
				cards.getChildren().add(card);
				res.add(card);
			}
		}
		return res;
	}
	
	private void encodeServers(FacesContext context, Cards cards, Collection<TGpeMServer> servers) throws IOException {
		if (!servers.isEmpty()) {
			for (TGpeMServer server : servers) {
				Card card = new Card();
				card.setValue(server);
				card.setType(CardsRenderer.TYPE_SERVER);
				card.setStyleClass(cards.getCardStyle());
				cards.getChildren().add(card);
			}
		}
		
	}
}
