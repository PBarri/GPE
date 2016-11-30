package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIData;
import org.primefaces.component.datatable.DataTableRenderer;
import org.primefaces.util.WidgetBuilder;

/**
 *
 * @author AWG
 * @version 1.0
 * @since 1.0
 */
public class GPEDataTableRenderer extends DataTableRenderer {

    @Override
    protected void encodePaginatorConfig(FacesContext context, UIData uidata, WidgetBuilder wb) throws IOException {
        String clientId = uidata.getClientId(context);
        String paginatorPosition = uidata.getPaginatorPosition();
        String paginatorContainers = null;
        String currentPageTemplate = uidata.getCurrentPageReportTemplate();

        if (paginatorPosition.equalsIgnoreCase("both")) {
            paginatorContainers = "'" + clientId + "_paginator_top','" + clientId + "_paginator_bottom','" + clientId + "_paginator_rrpp'";
        } else {
            paginatorContainers = "'" + clientId + "_paginator_" + paginatorPosition + "','" + clientId + "_paginator_rrpp'";
        }
        wb.append(",paginator:{").append("id:[").append(paginatorContainers).append("]").append(",rows:").append(uidata.getRows()).append(",rowCount:").append(uidata.getRowCount())
                        .append(",page:").append(uidata.getPage());

        if (currentPageTemplate != null) {
            wb.append(",currentPageTemplate:'").append(currentPageTemplate).append("'");
        }
        if (uidata.getPageLinks() != 10) {
            wb.append(",pageLinks:").append(uidata.getPageLinks());
        }
        if (!uidata.isPaginatorAlwaysVisible()) {
            wb.append(",alwaysVisible:false");
        }
        wb.append("}");
    }
}