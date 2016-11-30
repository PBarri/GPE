package com.awg.gpe.data.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMTimesheet;
import com.awg.gpe.data.model.TGpeMUser;

/**
 * Repositorio de la entidad {@link TGpeMTimesheet}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMTimesheet extends BaseRepository<TGpeMTimesheet, Long> {

    @Query("select t from TGpeMTimesheet t where t.startDate <= ?1 and t.endDate >= ?1 and t.user = ?2")
    TGpeMTimesheet findByDate(LocalDate now, TGpeMUser user);

}
