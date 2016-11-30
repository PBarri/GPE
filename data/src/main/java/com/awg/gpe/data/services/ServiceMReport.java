package com.awg.gpe.data.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.enums.EnumReportStatus;
import com.awg.gpe.data.enums.EnumReportType;
import com.awg.gpe.data.filters.TGpeMReportFilters;
import com.awg.gpe.data.model.QTGpeMReport;
import com.awg.gpe.data.model.TGpeMReport;
import com.awg.gpe.data.model.TGpeMUser;
import com.awg.gpe.data.model.TGpePReportStatus;
import com.awg.gpe.data.model.TGpePReportType;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMReport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ServiceMReport extends BaseService<TGpeMReport, Long> {

    /**
     * Repositorio correspondiente a la entidad
     * 
     * @since 1.0
     */
    @Autowired
    private RepositoryMReport repository;

    /**
     * @see com.awg.gpe.data.services.BaseService#getRepository()
     * @version 1.0
     * @since 1.0
     */
    @Override
    public BaseRepository<TGpeMReport, Long> getRepository() {
        return this.repository;
    }
    
    /**
     * @see com.awg.gpe.data.services.BaseService#createPredicateFromFilters(java.util.Map)
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
        QTGpeMReport report = QTGpeMReport.tGpeMReport;
        BooleanBuilder predicate = new BooleanBuilder();

        filters.forEach((key, value) -> {
            switch (key) {
                case TGpeMReportFilters.FILTER_USER:
                    TGpeMUser user = (TGpeMUser) value;
                    if (!user.isAdministrator()) {
                        predicate.and(report.user.eq(user));   
                    }                    
                    break;
                case TGpeMReportFilters.FILTER_CREATED_BY:
                    predicate.and(report.user.eq((TGpeMUser) value));
                    break;
                case TGpeMReportFilters.FILTER_DATE:
                    LocalDateTime before = ((LocalDate) value).atStartOfDay();
                    LocalDateTime after = ((LocalDate) value).plusDays(1).atStartOfDay();
                    predicate.and(report.timestamp.between(before, after));
                    break;
                case TGpeMReportFilters.FILTER_STATUS:
                    predicate.and(report.status.eq(new TGpePReportStatus((EnumReportStatus) value)));
                    break;
                case TGpeMReportFilters.FILTER_TYPE:
                    predicate.and(report.type.eq(new TGpePReportType((EnumReportType) value)));
                    break;
                default:
                    break;
            }
        });
        return predicate;
    }

}
