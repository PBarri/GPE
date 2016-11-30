package com.awg.gpe.data.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.awg.gpe.data.model.TGpeMTask;

/**
 * Repositorio de la entidad {@link TGpeMTask}
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@Repository
public interface RepositoryMTask extends BaseRepository<TGpeMTask, Long> {
	
	@Query(value = "select st.description, count(t.id), st.color from T_Gpe_P_Task_Status st left join T_Gpe_M_Tasks t on st.id = t.TASK_STATUS_ID where st.id in ?1 group by st.description, st.color",
			nativeQuery = true)
    List<Object[]> findTaskCountByStatus(Collection<Long> status);
	
	@Query(value = "SELECT DESCRIPTION, " 
			+ "  SUM(NUM_TAREAS), " 
			+ "  COLOR " 
			+ "FROM " 
			+ "  (SELECT ts.DESCRIPTION, " 
			+ "    0 AS NUM_TAREAS, " 
			+ "    ts.COLOR " 
			+ "  FROM T_GPE_P_TASK_STATUS ts " 
			+ "  WHERE ts.id in ?2 " 
			+ "  UNION ALL " 
			+ "  SELECT ts.DESCRIPTION, " 
			+ "    COUNT(t.id) AS NUM_TAREAS, " 
			+ "    ts.COLOR " 
			+ "  FROM T_GPE_P_TASK_STATUS ts " 
			+ "  LEFT JOIN T_GPE_M_TASKS t " 
			+ "  ON t.TASK_STATUS_ID = ts.ID " 
			+ "  LEFT JOIN T_GPE_M_REQUIREMENTS req " 
			+ "  ON t.REQUIREMENT_ID = req.id " 
			+ "  LEFT JOIN T_GPE_M_PROJECTS p " 
			+ "  ON req.PROJECT_ID = p.id " 
			+ "  WHERE p.ID        = ?1 " 
			+ "  GROUP BY ts.DESCRIPTION, " 
			+ "    ts.color " 
			+ "  ) " 
			+ "GROUP BY description, " 
			+ "  color",
			nativeQuery = true)
    List<Object[]> findTaskCountByStatusAndProject(Long idProject, Collection<Long> status);
	
	@Query(value = "SELECT t.id, "
			+ "  t.CODE, "
			+ "  (SELECT parent.code FROM t_gpe_m_tasks parent WHERE parent.id = t.PARENT_ID "
			+ "  ) AS parent, "
			+ "  t.START_DATE, "
			+ "  t.END_DATE, "
			/*			+ "  NULL AS duration, "
			+ "  null AS percentaje, "
			+ "  null as dependencies "*/
			+ "  (SELECT LISTAGG(DEPENDENCY_ID, ',') "
			+ " within GROUP (ORDER BY task_id) "
			+ "  FROM T_GPE_R_TASKS_DEPENDENCIES dep "
			+ "  WHERE dep.TASK_ID = t.id "
			+ "  group by t.id "
			+ "  ) AS dependencies "
			+ "FROM t_gpe_m_tasks t "
			+ "LEFT JOIN T_GPE_M_REQUIREMENTS req "
			+ "ON t.REQUIREMENT_ID = req.id "
			+ "LEFT JOIN T_GPE_M_PROJECTS p "
			+ "ON req.PROJECT_ID = p.id "
			+ "WHERE t.PARENT_ID IS NOT NULL "
			+ "and p.id = ?1 "
			+ "ORDER BY t.id ASC",
			nativeQuery = true)
    List<Object[]> findGanttDataByProject(Long idProject);

}
