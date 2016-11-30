package com.awg.gpe.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMVacation;

/**
 * Repositorio de la entidad {@link TGpeMVacation}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMVacation extends BaseRepository<TGpeMVacation, Long> {
	
	@Query(value = "SELECT v.* "
			+ "FROM T_GPE_M_USERS u "
			+ "INNER JOIN T_GPE_M_VACATIONS v "
			+ "ON v.USER_ID = u.id "
			+ "WHERE u.id  IN "
			+ "  ( SELECT DISTINCT(pm.ID_USER) "
			+ "  FROM T_GPE_M_PROJECTS p "
			+ "  LEFT JOIN T_GPE_M_REQUIREMENTS r "
			+ "  ON p.ID = r.PROJECT_ID "
			+ "  LEFT JOIN T_GPE_R_REQUIREMENTS_DEVELOPED rd "
			+ "  ON rd.ID_REQUIREMENT = r.ID "
			+ "  LEFT JOIN T_GPE_R_PROJECTS_MANAGED pm "
			+ "  ON pm.ID_PROJECT       = p.ID "
			+ "  WHERE p.PROJECT_LEADER = ?1 "
			+ "  AND pm.ID_USER        IS NOT NULL "
			+ "  ) "
			+ "AND v.MANAGED   = 0 "
			+ "AND v.START_DATE > sysdate",
			nativeQuery = true)
	List<TGpeMVacation> getVacationsToApproveByLeader(Long id);
	
	@Query(value = "SELECT v.* "
			+ "FROM T_GPE_M_USERS u "
			+ "INNER JOIN T_GPE_M_VACATIONS v "
			+ "ON v.USER_ID = u.id "
			+ "WHERE u.id  IN "
			+ "  ( SELECT DISTINCT(rd.ID_USER) "
			+ "  FROM T_GPE_M_USERS u "
			+ "  LEFT JOIN T_GPE_R_PROJECTS_MANAGED pm "
			+ "  ON u.ID = pm.ID_USER "
			+ "  LEFT JOIN T_GPE_M_PROJECTS p "
			+ "  ON pm.ID_PROJECT = p.ID "
			+ "  LEFT JOIN T_GPE_M_REQUIREMENTS r "
			+ "  ON p.ID = r.PROJECT_ID "
			+ "  LEFT JOIN T_GPE_R_REQUIREMENTS_DEVELOPED rd "
			+ "  ON rd.ID_REQUIREMENT = r.ID "
			+ "  WHERE u.ROLE_ID      = 3 "
			+ "  AND pm.ID_USER       = ?1 "
			+ "  ) "
			+ "AND v.MANAGED   = 0 "
			+ "AND v.START_DATE > sysdate ",
			nativeQuery = true)
	List<TGpeMVacation> getVacationsToApproveByManager(Long id);

}
