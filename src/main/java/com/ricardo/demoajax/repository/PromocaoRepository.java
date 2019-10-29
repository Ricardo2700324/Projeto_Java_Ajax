package com.ricardo.demoajax.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.demoajax.domain.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
	
	@Query("select p from Promocao p where p.titulo like %:search% "
			+ "or p.site like %:search% "
			+ "or p.categoria.titulo like %:search%")
	Page<Promocao> findByTituloOrSiteOrCategoria(@Param("search") String search, org.springframework.data.domain.Pageable pageable);

	@Query("select p from Promocao p where p.site like :site")
	Page<Promocao> findBySite(@Param("site") String site, org.springframework.data.domain.Pageable pageable);
	
	//autocomplete
	@Query("select distinct p.site from Promocao p where p.site like %:site%")
	List<String> findSiteByTermo(@Param("site") String site );
	
	//likes
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Promocao p set p.likes = p.likes +1 where p.id = :id")
	void updateSomarLikes(@Param("id") Long id);
	
	
	@Query("select p.likes from Promocao p where p.id = :id")
	int findLikesById(@Param("id") Long id);
	
	
	
}
