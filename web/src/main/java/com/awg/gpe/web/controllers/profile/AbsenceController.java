package com.awg.gpe.web.controllers.profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMAbsenceFilters;
import com.awg.gpe.data.model.BaseEntity;
import com.awg.gpe.data.model.TGpeMAbsence;
import com.awg.gpe.data.services.ServiceMAbsence;
import com.awg.gpe.utils.DateUtils;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador encargado de manejar las ausencias y las acciones que los usuarios pueden realizar sobre ellas
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "absencesController")
@ViewScoped
public class AbsenceController extends BaseController {
    
    private static final Logger log = Logger.getLogger(AbsenceController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_ABSENCE = "/views/profile/absences.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMAbsence absencesService;
    
    // Attributes ----------------------------------------------------------
    /**
     * Listado paginado de las ausencias del usuario en el año actual
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMAbsence> userAbsences;
    /**
     * Listado paginado de las ausencias históricas del usuario
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMAbsence> histAbsences;
    /**
     * Objeto que recoge los datos de la nueva ausencia
     * @version 1.0
     * @since 1.0
     */
    private TGpeMAbsence newAbsence;
    /**
     * Objeto que recoge los datos de la ausencia seleccionada
     * @version 1.0
     * @since 1.0
     */
    private TGpeMAbsence selectedAbsence;
    
    /**
     * Datatable conteniendo el histórico de ausencias
     * @version 1.0
     * @since 1.0
     */
    private DataTable histDatatable;
    
    // Init ---------------------------------------------------------------
    /**
     * @see com.awg.gpe.web.controllers.BaseController#init()
     * @version 1.0
     * @since 1.0
     */
    @Override
    @PostConstruct
    public void init() {
        initialize();
    }

    /**
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        goToAbsences();
    }

    // Navigation -----------------------------------------------------------
    public void goToAbsences() {
        goToView(AbsenceController.VIEW_ABSENCE);
        clear();
    }
    
    /**
     * Método que crea una nueva ausencia para el usuario
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createAbsence() {
        if (this.newAbsence != null) {
            if (validateAbsence(this.newAbsence)) {
                try {
                    this.loggedUser.addAbsence(this.newAbsence);
                    this.absencesService.save(this.newAbsence);
                    clear();
                    swal("Se ha guardado la nueva ausencia correctamente", null, "success");
                } catch (ServiceException e) {
                    AbsenceController.log.error("Se ha producido un error al guardar una nueva ausencia: " + e.getMessage());
                }
            } else {
                swal("El formulario contiene errores", null, "error");
            }
        } else {
            swal("Se ha producido un error", null, "error");
        }
    }
    
    /**
     * Método que modifica la ausencia seleccionada
     * 
     * @version 1.0
     * @since 1.0
     */
    public void updateAbsence() {
        if (this.selectedAbsence != null) {
            if (validateAbsence(this.selectedAbsence)) {
                try {
                    this.absencesService.save(this.selectedAbsence);
                    clear();
                    swal("Se ha modificado la ausencia correctamente", null, "success");
                } catch (ServiceException e) {
                    AbsenceController.log.error("Se ha producido un error al modificar la ausencia " + this.selectedAbsence + ": " + e.getMessage());
                    swal("Se ha producido un error al modificar la ausencia. Inténtelo de nuevo en unos minutos", null, "error");
                }                
            } else {
                swal("El formulario contiene errores", null, "error");
            }
        } else {
            swal("Debe seleccionar una ausencia para modificar", null, "error");
        }
    }
    
    /**
     * Método que elimina una ausencia.
     * Este método comprobará que la ausencia no haya pasado ya
     * @param absence Ausencia a eliminar
     */
    public void removeAbsence(TGpeMAbsence absence) {
    	try {
            this.absencesService.delete(absence);
            clear();
            swal("Se ha eliminado la ausencia correctamente", null, "success");
        } catch (ServiceException e) {
            AbsenceController.log.error("Se ha producido un error al eliminar la ausencia " + absence + ": " + e.getMessage());
            swal("Se ha producido un error al eliminar la ausencia. Inténtelo de nuevo en unos minutos", null, "error");
        } 
    }
    
    private Boolean validateAbsence(TGpeMAbsence absence) {
        Optional<TGpeMAbsence> collapsed = Optional.empty();
        ValidatorBuilder validator = ValidatorBuilder.newInstance()
            .field(absence.getStartDate())
                .required()
            .and()
            .field(absence.getEndDate())
                .required()
                .after(absence.getStartDate())
            .and()
            .field(absence.getCommentary())
                .maxLength(100)
            .and();
        
        if (validator.validate()) {
            List<TGpeMAbsence> absences = this.userAbsences.getDataList();
            if (absences != null && !absences.isEmpty()) {
            	collapsed = absences.parallelStream()
                        .filter(a -> DateUtils.isBetween(a.getStartDate(), absence.getStartDate(), absence.getEndDate())
                                  || DateUtils.isBetween(a.getEndDate(), absence.getStartDate(), absence.getEndDate()))
                        .findAny();
            }
            
        }
        
        return validator.isValid() && !collapsed.isPresent();
    }
    
    private void clear() {
        GPELazyDataModelFilterBuilder builder = GPELazyDataModelFilterBuilder.newInstance()
            .add(TGpeMAbsenceFilters.USER_FILTER, this.loggedUser)
        	.add(TGpeMAbsenceFilters.YEAR_FILTER, LocalDate.now().getYear());
        this.userAbsences = new GPELazyDataModel<>(this.absencesService, BaseEntity.TIMESTAMP, SortOrder.DESCENDING, builder.build());
        builder
        	.add(TGpeMAbsenceFilters.HIST_YEAR_FILTER, LocalDate.now().getYear())
        	.remove(TGpeMAbsenceFilters.YEAR_FILTER);
        this.histAbsences = new GPELazyDataModel<>(this.absencesService, BaseEntity.TIMESTAMP, SortOrder.DESCENDING, builder.build());
        this.selectedAbsence = null;
        this.newAbsence = new TGpeMAbsence();
        resetDatatable();
        if (this.histDatatable != null) {
            this.histDatatable.setFirst(0);
        }
    }
    
    // Getters and setters --------------------------------------------------

	/**
	 * @return the userAbsences
	 */
	public GPELazyDataModel<TGpeMAbsence> getUserAbsences() {
		return this.userAbsences;
	}

	/**
	 * @param userAbsences the userAbsences to set
	 */
	public void setUserAbsences(GPELazyDataModel<TGpeMAbsence> userAbsences) {
		this.userAbsences = userAbsences;
	}

	/**
	 * @return the histAbsences
	 */
	public GPELazyDataModel<TGpeMAbsence> getHistAbsences() {
		return this.histAbsences;
	}

	/**
	 * @param histAbsences the histAbsences to set
	 */
	public void setHistAbsences(GPELazyDataModel<TGpeMAbsence> histAbsences) {
		this.histAbsences = histAbsences;
	}

	/**
	 * @return the newAbsence
	 */
	public TGpeMAbsence getNewAbsence() {
		return this.newAbsence;
	}

	/**
	 * @param newAbsence the newAbsence to set
	 */
	public void setNewAbsence(TGpeMAbsence newAbsence) {
		this.newAbsence = newAbsence;
	}

	/**
	 * @return the selectedAbsence
	 */
	public TGpeMAbsence getSelectedAbsence() {
		return this.selectedAbsence;
	}

	/**
	 * @param selectedAbsence the selectedAbsence to set
	 */
	public void setSelectedAbsence(TGpeMAbsence selectedAbsence) {
		this.selectedAbsence = selectedAbsence;
	}

	/**
	 * @return the histDatatable
	 */
	public DataTable getHistDatatable() {
		return this.histDatatable;
	}

	/**
	 * @param histDatatable the histDatatable to set
	 */
	public void setHistDatatable(DataTable histDatatable) {
		this.histDatatable = histDatatable;
	}

}