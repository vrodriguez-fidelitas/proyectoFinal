
package com.ciclo.repository;

import com.ciclo.domain.Bicicleta;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BicicletaRepository
        extends JpaRepository<Bicicleta, Long> {

    Optional<Bicicleta> findByNumeroSerie(
        String numeroSerie
    );

    List<Bicicleta> findByDisponibleTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT b FROM Bicicleta b
        WHERE b.idBicicleta = :id
    """)
    Optional<Bicicleta> findByIdForUpdate(
        @Param("id") Long id
    );
}
