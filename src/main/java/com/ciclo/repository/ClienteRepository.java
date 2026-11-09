package com.ciclo.repository;

import com.ciclo.domain.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Para validar que no existan 2 clientes con la misma identificación (HU-01)
    Optional<Cliente> findByIdentificacion(String identificacion);
}