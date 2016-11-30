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
public class GPENextPageLinkRenderer extends GPEPageLinkRenderer implements PaginatorElementRenderer {

    @Override
    public void render(FacesContext context, UIData uidata) throws IOException {
        int currentPage = uidata.getPage();
        int pageCount = uidata.getPageCount();

        boolean disabled = (currentPage == (pageCount - 1)) || (currentPage == 0 && pageCount == 0);

        render(context, uidata, UIData.PAGINATOR_NEXT_PAGE_LINK_CLASS, "fa fa-angle-right fa-lg", disabled);

    }

}
