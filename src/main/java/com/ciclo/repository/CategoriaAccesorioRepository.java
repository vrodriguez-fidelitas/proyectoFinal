package com.ciclo.repository;

import com.ciclo.domain.CategoriaAccesorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaAccesorioRepository extends JpaRepository<CategoriaAccesorio, Long> {
}