package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.UIData;

/**
 *
 * @author AWG
 * @version 1.0
 * @since 1.0
 */
public class GPEPageLinkRenderer {

    /**
     * Método que se encarga de renderizar los enlaces de la paginación de los listados
     * @param context Contexto de JSF
     * @param uidata Datos proporcionados por JSF
     * @param linkClass Atributo class del elemento
     * @param iconClass Atributo class del icono
     * @param disabled Si la paginación se encuentra deshabilitada
     * @throws IOException En el caso de que se produzca una excepción
     * @version 1.0
     * @since 1.0
     */
    public void render(FacesContext context, UIData uidata, String linkClass, String iconClass, boolean disabled) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String styleClass = disabled ? linkClass + " ui-state-disabled" : linkClass;

        writer.startElement("span", null);
        writer.writeAttribute("class", styleClass, null);
        if (!disabled) {
            writer.writeAttribute("tabindex", 0, null);
        }

        writer.startElement("span", null);
        writer.writeAttribute("class", iconClass, null);
        writer.endElement("span");

        writer.endElement("span");
    }

}
