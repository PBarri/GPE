package com.awg.gpe.data.repositories;

import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMReport;
import com.awg.gpe.data.model.TGpePReportType;

/**
 * Repositorio del a entidad {@link TGpeMReport}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMReport extends BaseRepository<TGpeMReport, Long> {
	
	TGpeMReport findFirstByTypeOrderByTimestamp(TGpePReportType type);

}
