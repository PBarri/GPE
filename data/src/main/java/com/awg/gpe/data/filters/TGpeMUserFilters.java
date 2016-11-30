package com.awg.gpe.data.filters;

import com.awg.gpe.data.model.TGpeMUser;

/**
 * Clase con los filtros disponibles para la paginación de la clase {@link TGpeMUser}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public interface TGpeMUserFilters {

    // Filters ------------------------------------------------------------
    String FILTER_NAME = "nameFilter";
    String FILTER_IDENTIFIER = "identifierFilter";
    String FILTER_ROLE = "roleFilter";
    String FILTER_USER_POSITION = "userPositionFilter";
    String FILTER_EMAIL = "emailFilter";
    String FILTER_ACTIVE = "activeFilter";

}
