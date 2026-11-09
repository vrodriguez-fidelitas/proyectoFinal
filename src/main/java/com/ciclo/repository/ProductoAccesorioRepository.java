package com.ciclo.repository;

import com.ciclo.domain.ProductoAccesorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoAccesorioRepository extends JpaRepository<ProductoAccesorio, Long> {
}