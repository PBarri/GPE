package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.UIData;
import org.primefaces.component.paginator.PaginatorElementRenderer;

/**
 * Clase que renderiza los Dropdown de los datatables
 *
 * @author AWG
 * @version 1.0
 * @since 1.0
 */
public class GPERowsPerPageRendered implements PaginatorElementRenderer {

    /**
     * @see org.primefaces.component.paginator.PaginatorElementRenderer#render(javax.faces.context.FacesContext, org.primefaces.component.api.UIData)
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void render(FacesContext context, UIData uidata) throws IOException {
        String template = uidata.getRowsPerPageTemplate();

        if (template != null) {
            ResponseWriter writer = context.getResponseWriter();
            int actualRows = uidata.getRows();
            String[] options = uidata.getRowsPerPageTemplate().split("[,\\s]+");
            String label = uidata.getRowsPerPageLabel();
            String clientId = uidata.getClientId(context);
            String ddId = clientId + "_rppDD";
            String labelId = null;

            if (label != null) {
                labelId = clientId + "_rppLabel";

                writer.startElement("label", null);
                writer.writeAttribute("id", labelId, null);
                writer.writeAttribute("for", ddId, null);
                writer.writeAttribute("class", UIData.PAGINATOR_RPP_LABEL_CLASS, null);
                writer.writeText(label, null);
                writer.endElement("label");
            }

            writer.startElement("div", null);
            writer.writeAttribute("id", clientId + "_paginator_rrpp", null);
            writer.writeAttribute("class", "select-parent", null);

            writer.startElement("select", null);
            writer.writeAttribute("id", ddId, null);
            writer.writeAttribute("name", ddId, null);
            if (label != null) {
                writer.writeAttribute("aria-labelledby", labelId, null);
            }
            writer.writeAttribute("class", UIData.PAGINATOR_RPP_OPTIONS_CLASS, null);
            writer.writeAttribute("autocomplete", "off", null);

            for (String option : options) {
                int rows = Integer.parseInt(option);
                writer.startElement("option", null);
                writer.writeAttribute("value", rows, null);

                if (actualRows == rows) {
                    writer.writeAttribute("selected", "selected", null);
                }

                writer.writeText(option, null);
                writer.endElement("option");
            }

            writer.endElement("select");

            writer.startElement("div", null);
            writer.writeAttribute("class", "overlay-arrow", null);
            writer.write("&#9660;");
            writer.endElement("div");

            writer.endElement("div");
        }
    }

}
