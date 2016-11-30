/**
 * 
 */
package com.awg.gpe.web.primefaces.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.awg.gpe.data.model.TGpeMUser;

/**
 * @author Pablo
 *
 */
@FacesConverter(value = "pickListAsignUsersConverter")
public class PickListAsignUsersConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TGpeMUser res = null;
		if(component instanceof PickList) {
			@SuppressWarnings("unchecked")
			DualListModel<TGpeMUser> dualList = (DualListModel<TGpeMUser>) ((PickList) component).getValue();
			for(TGpeMUser u : dualList.getSource()) {
				if(value.equals(u.getId().toString())) {
					res = u;
					break;
				}
			}
			if(res == null) {
				for(TGpeMUser u : dualList.getTarget()) {
					if(value.equals(u.getId().toString())) {
						res = u;
						break;
					}
				}
			}
		}
		return res;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String res = "";
		if(value != null && value instanceof TGpeMUser) {
			TGpeMUser user = (TGpeMUser) value;
			res = user.getId().toString();
		}
		return res;
	}

}
