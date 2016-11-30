package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;
import org.springframework.util.StringUtils;

import com.awg.gpe.data.model.TGpeMServer;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.web.primefaces.components.Card;

/**
 * @author Pablo Barrientos
 *
 */
public class CardRenderer extends CoreRenderer {
	
	public static final String RENDERER_TYPE = "com.awg.gpe.renderers.CardRenderer";
	public static final String TYPE_USER = "user";
	public static final String TYPE_SERVER = "server";
	
    protected ResourceBundle messages;
	
	@Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        this.messages = context.getApplication().getResourceBundle(context, "msg");
		Card card = (Card) component; 
        encodeMarkup(context, card);
    }

	protected void encodeMarkup(FacesContext context, Card card) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = card.getClientId(context);
		String styleClass = "minimal-card " + card.getStyleClass();

		if (card.isRendered()) {
			writer.startElement("div", null);
			writer.writeAttribute("id", clientId, null);
			writer.writeAttribute("class", styleClass, "styleClass");
			
			if (card.getStyle() != null) {
				writer.writeAttribute("style", card.getStyle(), "style");
			}
			
			renderDynamicPassThruAttributes(context, card);
			
			writer.startElement("div", null);
			writer.writeAttribute("class", "minimal-card-wrapper", "class");
			
			if (card.getType().equals(CardRenderer.TYPE_USER)) {
				TGpeMUser user = (TGpeMUser) card.getValue();
				encodeUser(context, user);
			} else if (card.getType().equals(CardRenderer.TYPE_SERVER)) {
				TGpeMServer server = (TGpeMServer) card.getValue();
				encodeServer(context, server);
			}
			writer.endElement("div");
			writer.endElement("div");
		}		
	}
	
	private void encodeUser(FacesContext context, TGpeMUser user) throws IOException {
		String completeName = String.format("%s %s", user.getName(), user.getSurname());
		String userPos = this.messages.getString(user.getUserPosition().getDescription());
		
		encodeAvatar(context, user.getPhotoUrl(), user.getAvatar());
		encodeInfo(context, completeName, user.getIdentifier(), userPos);		
	}
	
	private void encodeServer(FacesContext context, TGpeMServer server) throws IOException {
		encodeAvatar(context, null, "S");
		String serverType = this.messages.getString(server.getServerType().getDescription());
		encodeInfo(context, server.getHostname(), serverType, server.getIp());
	}

	private void encodeAvatar(FacesContext context, String url, String altText) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String avatarClass = "ui-panel ui-widget ui-widget-content ui-corner-all icon img-circle";
		String avatarImgClass = "icon img-responsive img-circle avatar";
		
		if (StringUtils.hasText(url)) {
			writer.startElement("img", null);
			writer.writeAttribute("class", avatarImgClass, "class");
			writer.writeAttribute("src", url, null);
			writer.endElement("img");
		} else {
			writer.startElement("div", null);
			writer.writeAttribute("class", avatarClass, "class");
			writer.startElement("div", null);
			writer.writeAttribute("class", "ui-panel-content ui-widget-content", "class");
			writer.write(altText);
			writer.endElement("div");
			writer.endElement("div");
		}
	}

	private void encodeInfo(FacesContext context, String label1, String label2, String label3) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String labelClass = "ui-outputlabel ui-widget";
		
		writer.startElement("div", null);
		writer.writeAttribute("class", "info", "class");
		
		writer.startElement("div", null);
		writer.writeAttribute("class", "name", "class");
		writer.startElement("label", null);
		writer.writeAttribute("class", labelClass, "class");
		writer.write(label1);
		writer.endElement("label");
		writer.endElement("div");
		
		writer.startElement("div", null);
		writer.writeAttribute("class", "identifier", "class");
		writer.startElement("label", null);
		writer.writeAttribute("class", labelClass, "class");
		writer.write(label2);
		writer.endElement("label");
		writer.endElement("div");
		
		writer.startElement("div", null);
		writer.writeAttribute("class", "category", "class");
		writer.startElement("label", null);
		writer.writeAttribute("class", labelClass, "class");
		writer.write(label3);
		writer.endElement("label");
		writer.endElement("div");
		
		writer.endElement("div");
	}
}
