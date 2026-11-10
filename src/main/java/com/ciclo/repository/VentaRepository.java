
package com.ciclo.repository;

import com.ciclo.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository
        extends JpaRepository<Venta, Long> {

    @EntityGraph(attributePaths = {"cliente"})
    List<Venta> findAllByOrderByFechaDesc();

    @EntityGraph(attributePaths = {
        "cliente", "detalles"
    })
    Optional<Venta> findWithDetallesById(Long id);
}
