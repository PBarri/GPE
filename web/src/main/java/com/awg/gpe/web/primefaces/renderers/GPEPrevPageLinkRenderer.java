package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIData;
import org.primefaces.component.paginator.PaginatorElementRenderer;

/**
 *
 * @author AWG
 * @version 1.0
 * @since 1.0
 */
public class GPEPrevPageLinkRenderer extends GPEPageLinkRenderer implements PaginatorElementRenderer {

    @Override
    public void render(FacesContext context, UIData uidata) throws IOException {
        boolean disabled = uidata.getPage() == 0;

        render(context, uidata, UIData.PAGINATOR_PREV_PAGE_LINK_CLASS, "fa fa-angle-left fa-lg", disabled);

    }

}
