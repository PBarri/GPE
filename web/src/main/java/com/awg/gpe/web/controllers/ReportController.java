package com.awg.gpe.web.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awg.gpe.data.enums.EnumReportStatus;
import com.awg.gpe.data.enums.EnumReportType;
import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.filters.TGpeMReportFilters;
import com.awg.gpe.data.model.TGpeMReport;
import com.awg.gpe.data.services.LoginService;
import com.awg.gpe.data.services.ServiceMReport;
import com.awg.gpe.web.primefaces.GPELazyDataModel;
import com.awg.gpe.web.utils.GPELazyDataModelFilterBuilder;

/**
 * Controlador encargado de manejar las acciones sobre los informes de la aplicación
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@ManagedBean(name = "reportController")
@ViewScoped
public class ReportController extends ExcelController {
    
    private static final Logger log = Logger.getLogger(ReportController.class);

    /**
     * Serial Version UID
     * 
     * @since 1.0
     */
    private static final long serialVersionUID = 1L;
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Views --------------------------------------------------------------
    private static final String VIEW_REPORTS = "/views/reports.xhtml";

    // Services -----------------------------------------------------------
    @Autowired
    private ServiceMReport reportService;

    // Attributes ----------------------------------------------------------
    /**
     * Lista paginada con los informes existentes en la aplicación
     * @version 1.0
     * @since 1.0
     */
    private GPELazyDataModel<TGpeMReport> reportList;
    /**
     * Lista con los tipos de informes disponibles en el sistema
     * @version 1.0
     * @since 1.0
     */
    private List<EnumReportType> reportTypes;
    /**
     * Lista con los posibles estados de los informes de la aplicación
     * @version 1.0
     * @since 1.0
     */
    private List<EnumReportStatus> reportStatus;
    
    // Filters -------------------------------------------------------------
    /**
     * Filtro del tipo de informe
     * @version 1.0
     * @since 1.0
     */
    private EnumReportType reportTypeFilter;
    /**
     * Filtro del estado del informe
     * @version 1.0
     * @since 1.0
     */
    private EnumReportStatus reportStatusFilter;
    /**
     * Filtro del usuario del informe
     * @version 1.0
     * @since 1.0
     */
    private String userFilter;
    /**
     * Filtro de la fecha del informe
     * @version 1.0
     * @since 1.0
     */
    private LocalDate dateFilter;
    
    // Init ----------------------------------------------------------------
    /**
     * @see com.awg.gpe.web.controllers.BaseController#init()
     * @version 1.0
     * @since 1.0
     */
    @Override
    @PostConstruct
    public void init() {
        initialize();
        this.reportStatus = Arrays.asList(EnumReportStatus.values());
        this.reportTypes = Arrays.asList(EnumReportType.values());
    }

    /**
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void reloadPage() {
        goToReports();
    }

    // Navigation -----------------------------------------------------------
    /**
     * Método que ejecuta la navegación hacia la pantalla de informes
     * @version 1.0
     * @since 1.0
     */
    public void goToReports() {
        goToView(ReportController.VIEW_REPORTS);
        Map<String, Object> filters = GPELazyDataModelFilterBuilder.newInstance()
            .add(TGpeMReportFilters.FILTER_USER, LoginService.getPrincipal())
            .build();
        this.reportList = new GPELazyDataModel<>(this.reportService, filters);
        clear();
    }
    
    private void clear() {
        if (this.datatable != null) {
            this.datatable.setFirst(0);
        }
        this.reportTypeFilter = null;
        this.reportStatusFilter = null;
        this.userFilter = null;
        this.dateFilter = null;
    }

    // Methods --------------------------------------------------------------

    /**
     * Método que genera el informe de proyectos, almacenándolos en BBDD y esperando a que el
     * proceso batch lo ejecute.
     * @version 1.0
     * @since 1.0
     */
    public void generateProjectReport() {
        LocalDateTime timestamp = LocalDateTime.now();
        TGpeMReport report = new TGpeMReport(EnumReportType.PROJECTS);
        report.setTimestamp(timestamp);
        report.setUser(LoginService.getPrincipal());
        report.setName(this.messages.getString("reports.names.projects") + this.formatter.format(timestamp));
        try {
            this.reportService.save(report);
            swal(this.messages.getString("reports.save.success"), null, "success");
        } catch (ServiceException e) {
            swal(this.messages.getString("reports.save.error"), null, "error");
        }
    }

    /**
     * Método que genera el informe de incurridos, almacenándolos en BBDD y esperando a que el
     * proceso batch lo ejecute.
     * @version 1.0
     * @since 1.0
     */
    public void generateIncurredReport() {
        LocalDateTime timestamp = LocalDateTime.now();
        TGpeMReport report = new TGpeMReport(EnumReportType.INCURREDS);
        report.setTimestamp(timestamp);
        report.setUser(LoginService.getPrincipal());
        report.setName(this.messages.getString("reports.names.incurreds") + this.formatter.format(timestamp));
        try {
            this.reportService.save(report);
            swal(this.messages.getString("reports.save.success"), null, "success");
        } catch (ServiceException e) {
            swal(this.messages.getString("reports.save.error"), null, "error");
        }
    }

    /**
     * Método que genera el informe de usuarios, almacenándolos en BBDD y esperando a que el
     * proceso batch lo ejecute.
     * @version 1.0
     * @since 1.0
     */
    public void generateUserReport() {
        LocalDateTime timestamp = LocalDateTime.now();
        TGpeMReport report = new TGpeMReport(EnumReportType.USERS);
        report.setTimestamp(timestamp);
        report.setUser(LoginService.getPrincipal());
        report.setName(this.messages.getString("reports.names.users") + this.formatter.format(timestamp));
        try {
            this.reportService.save(report);
            swal(this.messages.getString("reports.save.success"), null, "success");
        } catch (ServiceException e) {
            swal(this.messages.getString("reports.save.error"), null, "error");
        }
    }

    /**
     * Método que genera el informe de servidores, almacenándolos en BBDD y esperando a que el
     * proceso batch lo ejecute.
     * @version 1.0
     * @since 1.0
     */
    public void generateServerReport() {
        LocalDateTime timestamp = LocalDateTime.now();
        TGpeMReport report = new TGpeMReport(EnumReportType.SERVERS);
        report.setTimestamp(timestamp);
        report.setUser(LoginService.getPrincipal());
        report.setName(this.messages.getString("reports.names.servers") + this.formatter.format(timestamp));
        try {
            this.reportService.save(report);
            swal(this.messages.getString("reports.save.success"), null, "success");
        } catch (ServiceException e) {
            swal(this.messages.getString("reports.save.error"), null, "error");
        }
    }
    
    /**
     * Método que ejecuta la búsqueda por los filtros pasados por el usuario
     * @version 1.0
     * @since 1.0
     */
    public void search() {
        Map<String, Object> filters = GPELazyDataModelFilterBuilder.newInstance()
            .add(TGpeMReportFilters.FILTER_USER, LoginService.getPrincipal())
            .add(TGpeMReportFilters.FILTER_CREATED_BY, this.userFilter)
            .add(TGpeMReportFilters.FILTER_STATUS, this.reportStatusFilter)
            .add(TGpeMReportFilters.FILTER_TYPE, this.reportTypeFilter)
            .add(TGpeMReportFilters.FILTER_DATE, this.dateFilter)
            .build();
        this.reportList = new GPELazyDataModel<>(this.reportService, filters);
    }
    
    // Getters and setters --------------------------------------------------

	/**
	 * @return the reportService
	 */
	public ServiceMReport getReportService() {
		return this.reportService;
	}

	/**
	 * @param reportService the reportService to set
	 */
	public void setReportService(ServiceMReport reportService) {
		this.reportService = reportService;
	}

	/**
	 * @return the reportList
	 */
	public GPELazyDataModel<TGpeMReport> getReportList() {
		return this.reportList;
	}

	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(GPELazyDataModel<TGpeMReport> reportList) {
		this.reportList = reportList;
	}

	/**
	 * @return the reportTypes
	 */
	public List<EnumReportType> getReportTypes() {
		return this.reportTypes;
	}

	/**
	 * @param reportTypes the reportTypes to set
	 */
	public void setReportTypes(List<EnumReportType> reportTypes) {
		this.reportTypes = reportTypes;
	}

	/**
	 * @return the reportStatus
	 */
	public List<EnumReportStatus> getReportStatus() {
		return this.reportStatus;
	}

	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(List<EnumReportStatus> reportStatus) {
		this.reportStatus = reportStatus;
	}

	/**
	 * @return the reportTypeFilter
	 */
	public EnumReportType getReportTypeFilter() {
		return this.reportTypeFilter;
	}

	/**
	 * @param reportTypeFilter the reportTypeFilter to set
	 */
	public void setReportTypeFilter(EnumReportType reportTypeFilter) {
		this.reportTypeFilter = reportTypeFilter;
	}

	/**
	 * @return the reportStatusFilter
	 */
	public EnumReportStatus getReportStatusFilter() {
		return this.reportStatusFilter;
	}

	/**
	 * @param reportStatusFilter the reportStatusFilter to set
	 */
	public void setReportStatusFilter(EnumReportStatus reportStatusFilter) {
		this.reportStatusFilter = reportStatusFilter;
	}

	/**
	 * @return the userFilter
	 */
	public String getUserFilter() {
		return this.userFilter;
	}

	/**
	 * @param userFilter the userFilter to set
	 */
	public void setUserFilter(String userFilter) {
		this.userFilter = userFilter;
	}

	/**
	 * @return the dateFilter
	 */
	public LocalDate getDateFilter() {
		return this.dateFilter;
	}

	/**
	 * @param dateFilter the dateFilter to set
	 */
	public void setDateFilter(LocalDate dateFilter) {
		this.dateFilter = dateFilter;
	}
}