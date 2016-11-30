package com.awg.gpe.web.controllers.profile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMTimesheetFilters;
import com.awg.gpe.data.model.BaseEntity;
import com.awg.gpe.data.model.TGpeMTimesheet;
import com.awg.gpe.data.services.ServiceMTimesheet;
import com.awg.gpe.utils.DateUtils;
import com.awg.gpe.web.controllers.BaseController;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;
import com.awg.gpe.web.utils.validations.ValidatorBuilder;

/**
 * Controlador encargado de gestionar los horarios de los usuarios
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "timesheetController")
@ViewScoped
public class TimesheetController extends BaseController {
    
    private static final Logger log = Logger.getLogger(TimesheetController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;

    // Views --------------------------------------------------------------
    private static final String VIEW_SCHEDULES = "/views/profile/timesheet.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMTimesheet timesheetService;

    // Attributes ----------------------------------------------------------
    /**
     * Listado paginado de los horarios del usuario
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMTimesheet> timesheets;
    /**
     * Objeto que recoge los datos de un nuevo horario
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTimesheet newTimesheet;
    /**
     * Objeto que recoge los datos del horario seleccionado
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTimesheet selectedTimesheet;
    /**
     * Horario actual del usuario
     * @version 1.0
     * @since 1.0
     */
    private TGpeMTimesheet actualTimesheet;
    /**
     * Marca del día de la semana
     * @version 1.0
     * @since 1.0
     */
    private Integer dayOfWeek;

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
        goToTimesheets();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de horarios
     * @version 1.0
     * @since 1.0
     */
    public void goToTimesheets() {
        goToView(TimesheetController.VIEW_SCHEDULES);
        clear();
    }
    
    private void clear() {
        GPELazyDataModelFilterBuilder builder = GPELazyDataModelFilterBuilder.newInstance()
            .add(TGpeMTimesheetFilters.USER_FILTER, this.loggedUser);
        // Generamos los datos para la tabla, ordenándolo por fecha de forma descendiente
        this.timesheets = new GPELazyDataModel<>(this.timesheetService, BaseEntity.TIMESTAMP, SortOrder.DESCENDING, builder.build());
        this.newTimesheet = new TGpeMTimesheet();
        // Buscamos el horario actual
        try {
            this.actualTimesheet = this.timesheetService.findActual(this.loggedUser);
        } catch (ServiceException e) {
            TimesheetController.log.error("Se ha producido un error al cargar el horario actual");
        }
        this.dayOfWeek = DayOfWeek.from(LocalDate.now()).getValue();
    }
    
    // Methods --------------------------------------------------------------
    
    /**
     * Método que guarda el nuevo horario creado, previa comprobación de que todos los datos son válidos
     * Una vez guardado, se resetea la vista y se vuelven a cargar los horarios
     * 
     * @version 1.0
     * @since 1.0
     */
    public void createTimesheet() {
        if (this.newTimesheet != null) {
            if (validateTimesheet(this.newTimesheet)) {
                try {
                    this.loggedUser.addTimesheet(this.newTimesheet);
                    this.timesheetService.save(this.newTimesheet);
                    clear();
                    swal("El nuevo horario se ha guardado correctamente", null, "success");
                } catch (ServiceException e) {
                    TimesheetController.log.error("Se ha producido un error al guardar un nuevo horario: " + e.getMessage());
                    swal("Se ha producido un error al guardar el nuevo horario", null, "error");
                }
            } else {
                swal("El nuevo horario contiene errores", null, "error");
            }
        } else {
            swal("Se ha producido un error", null, "error");
        }
    }
    
    /**
     * Método que guarda el horario que el usuario haya modificado, previa comprobación de que los datos son correctos
     * @version 1.0
     * @since 1.0
     */
    public void updateTimesheet() {
        if (this.selectedTimesheet != null) {
            // Comprobamos si se está editando el horario actual para replicarlo
            // Creamos un nuevo horario en el caso de que se esté editando
            TGpeMTimesheet newTimesheet = null;
            if (DateUtils.isActual(this.selectedTimesheet.getStartDate(), this.selectedTimesheet.getEndDate())) {
                newTimesheet = new TGpeMTimesheet();
                BeanUtils.copyProperties(this.selectedTimesheet, newTimesheet, "id", "version", "timestamp");
                // Actualizamos los datos del nuevo horario
                newTimesheet.setTimestamp(LocalDateTime.now());
                newTimesheet.setStartDate(LocalDate.now());
                // Actualizamos la fecha de fin del horario anterior
                this.actualTimesheet.setEndDate(LocalDate.now().minusDays(1));
            }
            // Si se ha creado un nuevo horario también se valida junto con el horario anterior
            if (validateTimesheet(this.actualTimesheet) && (newTimesheet == null || validateTimesheet(newTimesheet))) {
                try {
                    this.timesheetService.save(this.actualTimesheet);
                    if (newTimesheet != null) {
                        this.loggedUser.addTimesheet(newTimesheet);
                        this.timesheetService.save(newTimesheet);
                    }
                    clear();
                    swal("Se ha modificado correctamente el horario", null, "success");
                } catch (ServiceException e) {
                    TimesheetController.log.error("Se ha producido un error al modificar un horario: " + e.getLocalizedMessage());
                    swal("Se ha producido un error al modificar el horario", null, "error");
                }
            } else {
                swal("El formulario contiene errores", null, "error");
            }
        } else {
            swal("Debe seleccionar un horario para editar", null, "error");
        }
    }
    
    /**
     * Método que elimina un horario del usuario
     * 
     * @version 1.0
     * @since 1.0
     */
    public void removeTimesheet(TGpeMTimesheet timesheet) {
        if (timesheet != null && timesheet.getStartDate() != null && timesheet.getEndDate() != null) {
            if (!DateUtils.isActual(timesheet.getStartDate(), timesheet.getEndDate())) {
                try {
                    this.loggedUser.removeTimesheet(timesheet);
                    this.timesheetService.delete(timesheet);
                    clear();
                    swal("Se ha eliminado correctamente el horario", null, "success");
                } catch (ServiceException e) {
                    TimesheetController.log.error("Se ha producido un error al eliminar el horario " + timesheet + ": " + e.getMessage());
                }
            } else {
                swal("No se puede eliminar un horario actual. Pruebe a editarlo", null, "error");
            }
        }
    }
    
    /**
     * Método que actualiza el horario actual. 
     * El nuevo horario tendría efecto a partir del día siguiente a la modificación
     * 
     * <p>
     * Realiza los siguientes pasos:
     * <ul>
     * <li>Marca como fecha de fin la fecha actual</li>
     * <li>Genera un nuevo horario con fecha de inicio el día siguiente y fecha de fin la misma que tuviera asignada</li>
     * <li>Guarda ambos horarios</li>
     * </ul>
     * 
     * @version 1.0
     * @since 1.0
     */
    public void updateActualTimesheet() {
        if (this.actualTimesheet != null) {
            TGpeMTimesheet nt = new TGpeMTimesheet(); 
            // Copiamos el horario hacia el nuevo
            BeanUtils.copyProperties(this.actualTimesheet, nt);
            // Modificamos las fechas de los horarios
            this.actualTimesheet.setEndDate(LocalDate.now());
            nt.setStartDate(LocalDate.now().plusDays(1));
            if (validateTimesheet(nt)) {
                try {
                    this.timesheetService.save(Arrays.asList(this.actualTimesheet, nt));
                    clear();
                    swal("Se ha modificado el horario actual con éxito", null, "success");
                } catch (ServiceException e) {
                    TimesheetController.log.error("Se ha producido un error al guardar los nuevos horarios: " + e.getMessage());
                    swal("Se ha producido un error al guardar los nuevos horarios", null, "error");
                }
            } else {
                // Mostramos un mensaje de error y volvemos al horario anterior
                swal("El nuevo horario no es correcto", null, "error");
            }
        } else {
            swal("Se ha producido un error desconocido", null, "error");
        }
    }
    
    private Boolean validateTimesheet(TGpeMTimesheet timesheet) {
        Optional<TGpeMTimesheet> collapsedTimesheet = Optional.empty();
        ValidatorBuilder validator = ValidatorBuilder.newInstance()
            .field(timesheet.getMondayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getTuesdayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getWednesdayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getTuesdayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getFridayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getSaturdayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getSundayHours())
                .required()
                .range(0F, 10F)
            .and()
            .field(timesheet.getStartDate())
                .required()
            .and()
            .field(timesheet.getEndDate())
                .required()
            .and();
        
        // Si pasa las validaciones básicas, comprobamos si el nuevo horario se solapa con alguno de los horarios existentes anteriormente
        if (validator.isValid() && this.timesheets.getDataList() != null && !this.timesheets.getDataList().isEmpty()) {
            List<TGpeMTimesheet> userTimesheets = this.timesheets.getDataList();
            collapsedTimesheet = userTimesheets.parallelStream()
                .filter(t -> DateUtils.isBetween(t.getStartDate(), timesheet.getStartDate(), timesheet.getEndDate())
                          || DateUtils.isBetween(t.getEndDate(), timesheet.getStartDate(), timesheet.getEndDate()))
                .findAny();
        }
        
        // Si los datos son válidos y no existen horarios colapsados, pasa la validación
        return validator.validate() && !collapsedTimesheet.isPresent();
    }
    
    // Getters and setters --------------------------------------------------

    public GPELazyDataModel<TGpeMTimesheet> getTimesheets() {
        return this.timesheets;
    }

    public void setTimesheets(GPELazyDataModel<TGpeMTimesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public TGpeMTimesheet getNewTimesheet() {
        return this.newTimesheet;
    }

    public void setNewTimesheet(TGpeMTimesheet newTimesheet) {
        this.newTimesheet = newTimesheet;
    }

    public TGpeMTimesheet getActualTimesheet() {
        return this.actualTimesheet;
    }

    public void setActualTimesheet(TGpeMTimesheet actualTimesheet) {
        this.actualTimesheet = actualTimesheet;
    }

    public Integer getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TGpeMTimesheet getSelectedTimesheet() {
        return this.selectedTimesheet;
    }

    public void setSelectedTimesheet(TGpeMTimesheet selectedTimesheet) {
        this.selectedTimesheet = selectedTimesheet;
    }
    
}
