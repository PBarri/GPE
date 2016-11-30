package com.awg.gpe.data.repositories;

import java.io.Serializable;
import java.util.List;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interfaz que define el repositorio del que extenderán el resto de repositorios.
 * <p>
 * Además de extender a la interfaz {@link JpaRepository}, lo cuál le da acceso a todos los métodos definidos
 * en dicha interfaz, extiende de {@link QueryDslPredicateExecutor}, lo cuál le proporciona la funcionalidad de
 * crear consultas dinámicas en las distintas entidades
 * </p>
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable> extends JpaRepository<T, I>, QueryDslPredicateExecutor<T> {

	/* (non-Javadoc)
	 * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate)
	 */
	@Override
    List<T> findAll(Predicate predicate);

    /* (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate, org.springframework.data.domain.Sort)
     */
    @Override
    List<T> findAll(Predicate predicate, Sort sort);

    /* (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate, org.springframework.data.domain.Pageable)
     */
    @Override
    Page<T> findAll(Predicate predicate, Pageable page);
	
}
