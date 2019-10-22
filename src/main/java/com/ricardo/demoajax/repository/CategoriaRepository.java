package com.ricardo.demoajax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.demoajax.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
