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
import com.awg.gpe.data.filters.TGpeMVacationFilters;
import com.awg.gpe.data.model.BaseEntity;
import com.awg.gpe.data.model.TGpeMVacation;
import com.awg.gpe.data.services.ServiceMVacation;
import com.awg.gpe.utils.DateUtils;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador que gestiona las vacaciones de los usuarios
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "vacationsController")
@ViewScoped
public class VacationController extends BaseController {
    
    private static final Logger log = Logger.getLogger(VacationController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_VACATION = "/views/profile/vacations.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMVacation vacationService;
    
    // Attributes ----------------------------------------------------------
    /**
     * Listado paginado de las vacaciones del usuario del año actual
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMVacation> userVacations;
    /**
     * Listado paginado de las vacaciones históricas del usuario
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMVacation> histVacations;
    /**
     * Objeto que recoge los datos de un nuevo periodo vacacional
     * @version 1.0
     * @since 1.0
     */
    private TGpeMVacation newVacation;
    /**
     * Periodo vacacional seleccionado
     * @version 1.0
     * @since 1.0
     */
    private TGpeMVacation selectedVacation;
    /**
     * Objeto representando la tabla de vacaciones históricas
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
        goToVacations();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de vacaciones
     * @version 1.0
     * @since 1.0
     */
    public void goToVacations() {
        goToView(VacationController.VIEW_VACATION);
        clear();
    }
    
    private void clear() {
        GPELazyDataModelFilterBuilder builder = GPELazyDataModelFilterBuilder.newInstance()
            .add(TGpeMVacationFilters.USER_FILTER, this.loggedUser)
        	.add(TGpeMVacationFilters.YEAR_FILTER, LocalDate.now().getYear());
        this.userVacations = new GPELazyDataModel<>(this.vacationService, BaseEntity.TIMESTAMP, SortOrder.DESCENDING, builder.build());
        builder
        	.remove(TGpeMVacationFilters.YEAR_FILTER)
        	.add(TGpeMVacationFilters.HIST_YEAR_FILTER, LocalDate.now().getYear());
        this.histVacations = new GPELazyDataModel<>(this.vacationService, BaseEntity.TIMESTAMP, SortOrder.DESCENDING, builder.build());
        this.newVacation = new TGpeMVacation();
        this.selectedVacation = null;
        resetDatatable();
        if (this.histDatatable != null) {
            this.histDatatable.setFirst(0);
        }
    }
    
    // Methods --------------------------------------------------------------
    
    /**
     * Método que calcula los días laborables que consume un periodo de vacaciones
     * 
     * @param vacation
     * @return
     */
    public Integer getDays(TGpeMVacation vacation) {
    	try {
			return this.vacationService.countVacationDays(vacation);
		} catch (ServiceException e) {
			return -1;
		}
    }
    
    /**
     * Método que crea un nuevo periodo de vacaciones
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createVacation() {
        if (this.newVacation != null) {
            if (validateVacation(this.newVacation)) {
                try {
                    this.loggedUser.addVacation(this.newVacation);
                    this.vacationService.createVacation(this.newVacation);
                    clear();
                    swal("Se han modificado correctamente las vacaciones", null, "success");
                } catch (ServiceException e) {
                    VacationController.log.error("Se ha producido un error al crear unas vacaciones: " + e.getLocalizedMessage());
                    swal("Se ha producido un error al crear unas vacaciones", null, "error");
                }
            } else {
                swal("El formulario contiene errores", null, "error");
            }
        } else {
            swal("Se ha producido un error", null, "error");
        }
    }
    
    /**
     * Método que modifica unas vacaciones existentes
     * 
     * @version 1.0
     * @since 1.0
     */
    public void updateVacation() {
        if (this.selectedVacation != null) {
            if (validateVacation(this.selectedVacation)) {
                try {
                    this.selectedVacation.setApproved(false);
                    this.selectedVacation.setManaged(false);
                    this.vacationService.save(this.selectedVacation);
                    clear();
                    swal("Se han modificado correctamente las vacaciones", null, "success");
                } catch (ServiceException e) {
                    VacationController.log.error("Se ha producido un error al modificar las vacaciones: " + e.getLocalizedMessage());
                    swal("Se ha producido un error al modificar las vacaciones", null, "error");
                }
            } else {
                swal("El formulario contiene errores", null, "error");
            }
        } else {
            swal("Debe seleccionar unas vacaciones para editar", null, "error");
        }
    }
    
    /**
     * Método que elimina un periodo vacacional.
     * @param vacation Periodo vacacional a eliminar
     * @version 1.0
     * @since 1.0
     */
    public void removeVacation(TGpeMVacation vacation) {
    	try {
            this.vacationService.delete(vacation);
            clear();
            swal("Se han eliminado las vacaciones correctamente", null, "success");
        } catch (ServiceException e) {
            VacationController.log.error("Se ha producido un error al eliminar las vacaciones " + vacation + ": " + e.getMessage());
            swal("Se ha producido un error al eliminar las vacaciones. Inténtelo de nuevo en unos minutos", null, "error");
        } 
    }
    
    private Boolean validateVacation(TGpeMVacation vacation) {
        Optional<TGpeMVacation> collapsed = Optional.empty();
        ValidatorBuilder validator = ValidatorBuilder.newInstance()
             .field(vacation.getStartDate())
                 .required()
             .and()
             .field(vacation.getEndDate())
                 .required()
                 .after(vacation.getStartDate())
             .and()
             .field(vacation.getYear())
                 .required()
                 .range(1000, 9999)
             .and();
        
        if (validator.validate()) {
            List<TGpeMVacation> vacations = this.userVacations.getDataList();
            if (vacations != null && !vacations.isEmpty()) {
            	collapsed = vacations.parallelStream()
                        .filter(v -> DateUtils.isBetween(v.getStartDate(), vacation.getStartDate(), vacation.getEndDate())
                                  || DateUtils.isBetween(v.getEndDate(), vacation.getStartDate(), vacation.getEndDate()))
                        .findAny();
            }            
        }
        
        return validator.isValid() && !collapsed.isPresent();
    }
    
    /**
     * Método que calcula el número de días de vacaciones utilizado por el usuario en el año actual
     * 
     * @return Número de días de vacaciones
     * @version 1.0
     * @since 1.0
     */
    public Integer getCurrentVacationDays() {
    	if (this.loggedUser.getVacationDays() != null && this.loggedUser.getVacationDays() > 0) {
    		try {
    			return this.loggedUser.getVacationDays() - this.vacationService.countActualVacations(this.loggedUser);
    		} catch (ServiceException e) {
    			return -1;
    		}
    	} else { return -1; }    	
    }
    
    
    // Getters and setters --------------------------------------------------

	public GPELazyDataModel<TGpeMVacation> getUserVacations() {
		return this.userVacations;
	}

	public void setUserVacations(GPELazyDataModel<TGpeMVacation> userVacations) {
		this.userVacations = userVacations;
	}

	public GPELazyDataModel<TGpeMVacation> getHistVacations() {
		return this.histVacations;
	}

	public void setHistVacations(GPELazyDataModel<TGpeMVacation> histVacations) {
		this.histVacations = histVacations;
	}

	public TGpeMVacation getNewVacation() {
		return this.newVacation;
	}

	public void setNewVacation(TGpeMVacation newVacation) {
		this.newVacation = newVacation;
	}

	public TGpeMVacation getSelectedVacation() {
		return this.selectedVacation;
	}

	public void setSelectedVacation(TGpeMVacation selectedVacation) {
		this.selectedVacation = selectedVacation;
	}

	public DataTable getHistDatatable() {
		return this.histDatatable;
	}

	public void setHistDatatable(DataTable histDatatable) {
		this.histDatatable = histDatatable;
	}
}
