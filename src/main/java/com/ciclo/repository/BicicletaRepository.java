package com.ciclo.repository;

import com.ciclo.domain.Bicicleta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {
    
    // Para validar unicidad del número de serie (HU-02)
    Optional<Bicicleta> findByNumeroSerie(String numeroSerie);
    
    // Para listar bicicletas disponibles en inventario
    List<Bicicleta> findByDisponibleTrue();
}