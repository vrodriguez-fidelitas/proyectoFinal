package com.ciclo.repository;

import com.ciclo.domain.Compra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    @EntityGraph(attributePaths = {"proveedor"})
    List<Compra> findAllByOrderByFechaDesc();

    @EntityGraph(attributePaths = {"proveedor", "detalles", "detalles.accesorio"})
    Optional<Compra> findWithDetallesById(Long id);
}