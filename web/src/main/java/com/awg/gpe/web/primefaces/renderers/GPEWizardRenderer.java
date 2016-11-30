package com.awg.gpe.web.primefaces.renderers;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.component.wizard.WizardRenderer;
import org.primefaces.util.ComponentTraversalUtils;
import org.primefaces.util.HTML;

/**
 * Clase que permite pulsar sobre los paneles dentro del wizard de primefaces, así como añadir una animación
 * y cambiar el estilo de los botones de navegación
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class GPEWizardRenderer extends WizardRenderer {

    @Override
    protected void encodeScript(FacesContext context, Wizard wizard) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = wizard.getClientId(context);

        UIComponent form = ComponentTraversalUtils.closestForm(context, wizard);
        if (form == null) {
            throw new FacesException("Wizard : \"" + clientId + "\" must be inside a form element");
        }

        startScript(writer, clientId);

        writer.write("$(function() {");
        
        writer.write("PrimeFaces.cw('Wizard','" + wizard.resolveWidgetVar() + "',{");
        writer.write("id:'" + clientId + "'");
        writer.write(",showStepStatus:" + wizard.isShowStepStatus());
        writer.write(",showNavBar:" + wizard.isShowNavBar());

        if (wizard.getOnback() != null) {
            writer.write(",onback:function(){" + wizard.getOnback() + "}");
        }
        if (wizard.getOnnext() != null) {
            writer.write(",onnext:function(){" + wizard.getOnnext() + "}");
        }

        //all steps
        writer.write(",steps:[");
        boolean firstStep = true;
        String defaultStep = null;
        for (Iterator<UIComponent> children = wizard.getChildren().iterator(); children.hasNext();) {
            UIComponent child = children.next();

            if (child instanceof Tab && child.isRendered()) {
                Tab tab = (Tab) child;
                if (defaultStep == null) {
                    defaultStep = tab.getId();
                }

                if (!firstStep) {
                    writer.write(",");
                } else {
                    firstStep = false;
                }

                writer.write("'" + tab.getId() + "'");
            }
        }
        writer.write("]");

        //current step
        if (wizard.getStep() == null) {
            wizard.setStep(defaultStep);
        }

        writer.write(",initialStep:'" + wizard.getStep() + "'");

        writer.write("});});");

        endScript(writer);
    }

    @Override
    protected void encodeStepStatus(FacesContext context, Wizard wizard) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String currentStep = wizard.getStep();
        boolean currentFound = false;

        writer.startElement("ul", null);
        writer.writeAttribute("class", Wizard.STEP_STATUS_CLASS, null);

        for (int i = 0; i < wizard.getChildCount(); i++) {
            UIComponent child = wizard.getChildren().get(i);
            if (child instanceof Tab && child.isRendered()) {
                Tab tab = (Tab) child;
                String title = tab.getTitle();
                UIComponent titleFacet = tab.getFacet("title");
                boolean active = !currentFound && (currentStep == null || tab.getId().equals(currentStep));
                String titleStyleClass = active ? Wizard.ACTIVE_STEP_CLASS : Wizard.STEP_CLASS;
                if (tab.getTitleStyleClass() != null) {
                    titleStyleClass = titleStyleClass + " " + tab.getTitleStyleClass();
                }
                
                if (active) {
                    currentFound = true;
                }

                writer.startElement("li", null);
                writer.writeAttribute("class", titleStyleClass, null);
                if (tab.getTitleStyle() != null) writer.writeAttribute("style", tab.getTitleStyle(), null);
                if (tab.getTitletip() != null) writer.writeAttribute("title", tab.getTitletip(), null);
                
                // Creamos un anchor para poder navegar pulsando en las tablas
                writer.startElement("a", null);
                final String widgetVar = wizard.getWidgetVar();
                writer.writeAttribute("href", "javascript: PF('" + widgetVar + "').loadStep(PF('" + widgetVar + "').cfg.steps[" + i + "], false)", null);
                if (titleFacet != null)
                    titleFacet.encodeAll(context);
                else if (title != null)
                    writer.writeText(title, null);
                writer.endElement("a");
                
                writer.endElement("li");
            }
        }

        writer.endElement("ul");
    }

    @Override
    protected void encodeNavigator(FacesContext facesContext, Wizard wizard, String id, String label, String buttonClass, String icon) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement("button", null);
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("name", id, null);
        writer.writeAttribute("type", "button", null);
        
        // Comprobamos que botón se va a renderizar
        String iconClass = HTML.BUTTON_LEFT_ICON_CLASS + " " + icon;
        if (buttonClass.equals(Wizard.BACK_BUTTON_CLASS)) {
            writer.writeAttribute("class", "btn-primary " + HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS + " " + buttonClass, null);
        } else if (buttonClass.equals(Wizard.NEXT_BUTTON_CLASS)) {
            writer.writeAttribute("class", "btn-primary " + HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS + " " + buttonClass, null);
            iconClass = HTML.BUTTON_RIGHT_ICON_CLASS + " " + icon;
        } else {
            writer.writeAttribute("class", HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS + " " + buttonClass, null);
        }        

        //button icon
        writer.startElement("span", null);
        writer.writeAttribute("class", iconClass, null);
        writer.endElement("span");
        
        //text
        writer.startElement("span", null);
        writer.writeAttribute("class", HTML.BUTTON_TEXT_CLASS, null);
        writer.writeText(label, "value");
        writer.endElement("span");

        writer.endElement("button");
    }
}
