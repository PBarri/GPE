package com.awg.gpe.web.primefaces.components;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import org.primefaces.util.Constants;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ResourceDependencies({ @ResourceDependency(library = "primefaces", name = "primefaces.css"), @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
                @ResourceDependency(library = "primefaces", name = "jquery/jquery-plugins.js"), @ResourceDependency(library = "primefaces", name = "primefaces.js") })
@Deprecated
public class OverlayPanel extends UIPanel implements org.primefaces.component.api.Widget {

    public static final String COMPONENT_TYPE = "org.primefaces.component.OverlayPanel";
    public static final String COMPONENT_FAMILY = "org.primefaces.component";
    public static final String DEFAULT_RENDERER = "com.awg.gpe.web.components.ExtendedOverlayPanelRenderer";

    protected enum PropertyKeys {

        widgetVar, style, styleClass, forValue(
                        "for"), showEvent, hideEvent, showEffect, hideEffect, appendToBody, appendTo, onShow, onHide, my, at, dynamic, dismissable, showCloseIcon;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return toString != null ? toString : super.toString();
        }
    }

    public OverlayPanel() {
        setRendererType(OverlayPanel.DEFAULT_RENDERER);
    }

    @Override
    public String getFamily() {
        return OverlayPanel.COMPONENT_FAMILY;
    }

    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(OverlayPanel.PropertyKeys.widgetVar, _widgetVar);
    }

    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.style, null);
    }

    public void setStyle(java.lang.String _style) {
        getStateHelper().put(OverlayPanel.PropertyKeys.style, _style);
    }

    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.styleClass, null);
    }

    public void setStyleClass(java.lang.String _styleClass) {
        getStateHelper().put(OverlayPanel.PropertyKeys.styleClass, _styleClass);
    }

    public java.lang.String getFor() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.forValue, null);
    }

    public void setFor(java.lang.String _for) {
        getStateHelper().put(OverlayPanel.PropertyKeys.forValue, _for);
    }

    public java.lang.String getShowEvent() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.showEvent, null);
    }

    public void setShowEvent(java.lang.String _showEvent) {
        getStateHelper().put(OverlayPanel.PropertyKeys.showEvent, _showEvent);
    }

    public java.lang.String getHideEvent() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.hideEvent, null);
    }

    public void setHideEvent(java.lang.String _hideEvent) {
        getStateHelper().put(OverlayPanel.PropertyKeys.hideEvent, _hideEvent);
    }

    public java.lang.String getShowEffect() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.showEffect, null);
    }

    public void setShowEffect(java.lang.String _showEffect) {
        getStateHelper().put(OverlayPanel.PropertyKeys.showEffect, _showEffect);
    }

    public java.lang.String getHideEffect() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.hideEffect, null);
    }

    public void setHideEffect(java.lang.String _hideEffect) {
        getStateHelper().put(OverlayPanel.PropertyKeys.hideEffect, _hideEffect);
    }

    public boolean isAppendToBody() {
        return (java.lang.Boolean) getStateHelper().eval(OverlayPanel.PropertyKeys.appendToBody, false);
    }

    public void setAppendToBody(boolean _appendToBody) {
        getStateHelper().put(OverlayPanel.PropertyKeys.appendToBody, _appendToBody);
    }

    public java.lang.String getAppendTo() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.appendTo, null);
    }

    public void setAppendTo(java.lang.String _appendTo) {
        getStateHelper().put(OverlayPanel.PropertyKeys.appendTo, _appendTo);
    }

    public java.lang.String getOnShow() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.onShow, null);
    }

    public void setOnShow(java.lang.String _onShow) {
        getStateHelper().put(OverlayPanel.PropertyKeys.onShow, _onShow);
    }

    public java.lang.String getOnHide() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.onHide, null);
    }

    public void setOnHide(java.lang.String _onHide) {
        getStateHelper().put(OverlayPanel.PropertyKeys.onHide, _onHide);
    }

    public java.lang.String getMy() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.my, null);
    }

    public void setMy(java.lang.String _my) {
        getStateHelper().put(OverlayPanel.PropertyKeys.my, _my);
    }

    public java.lang.String getAt() {
        return (java.lang.String) getStateHelper().eval(OverlayPanel.PropertyKeys.at, null);
    }

    public void setAt(java.lang.String _at) {
        getStateHelper().put(OverlayPanel.PropertyKeys.at, _at);
    }

    public boolean isDynamic() {
        return (java.lang.Boolean) getStateHelper().eval(OverlayPanel.PropertyKeys.dynamic, false);
    }

    public void setDynamic(boolean _dynamic) {
        getStateHelper().put(OverlayPanel.PropertyKeys.dynamic, _dynamic);
    }

    public boolean isDismissable() {
        return (java.lang.Boolean) getStateHelper().eval(OverlayPanel.PropertyKeys.dismissable, true);
    }

    public void setDismissable(boolean _dismissable) {
        getStateHelper().put(OverlayPanel.PropertyKeys.dismissable, _dismissable);
    }

    public boolean isShowCloseIcon() {
        return (java.lang.Boolean) getStateHelper().eval(OverlayPanel.PropertyKeys.showCloseIcon, false);
    }

    public void setShowCloseIcon(boolean _showCloseIcon) {
        getStateHelper().put(OverlayPanel.PropertyKeys.showCloseIcon, _showCloseIcon);
    }

    public static final String STYLE_CLASS = "ui-overlaypanel ui-widget ui-widget-content ui-overlay-hidden ui-corner-all ui-shadow";
    public static final String CONTENT_CLASS = "ui-overlaypanel-content";

    @Override
    public void processDecodes(FacesContext context) {
        if (isRequestSource(context)) {
            decode(context);
        } else {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if (!isRequestSource(context)) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if (!isRequestSource(context)) {
            super.processUpdates(context);
        }
    }

    private boolean isRequestSource(FacesContext context) {
        return getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(getClientId(context) + "_contentLoad");
    }

    @Override
    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null)
            return userWidgetVar;
        else
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
    }

}
