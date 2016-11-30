/**
 * 
 */
package com.awg.gpe.data.services;

import java.time.LocalDateTime;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awg.gpe.data.exceptions.ServiceException;
import com.awg.gpe.data.model.TGpeMTaskComment;
import com.awg.gpe.data.repositories.BaseRepository;
import com.awg.gpe.data.repositories.RepositoryMTaskComment;
import com.querydsl.core.types.Predicate;

/**
 * @author Pablo
 *
 */
@Service
@Transactional
public class ServiceMTaskComment extends BaseService<TGpeMTaskComment, Long> {

	@Autowired
	private RepositoryMTaskComment repository;
	
	@Override
	public BaseRepository<TGpeMTaskComment, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Predicate createPredicateFromFilters(Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Método que guarda un comentario en la base de datos
	 * 
	 * @param comment Comentario a guardar
	 * @throws ServiceException En el caso de que exista una excepción
	 * @version 1.0
	 * @since 1.0
	 */
	public void createComment(TGpeMTaskComment comment) throws ServiceException {
		comment.setTimestamp(LocalDateTime.now());
		save(comment);
	}

}
