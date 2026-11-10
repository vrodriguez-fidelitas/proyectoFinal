
package com.ciclo.repository;

import com.ciclo.domain.ProductoAccesorio;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoAccesorioRepository
        extends JpaRepository<ProductoAccesorio, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT a FROM ProductoAccesorio a
        WHERE a.idAccesorio = :id
    """)
    Optional<ProductoAccesorio> findByIdForUpdate(
        @Param("id") Long id
    );
}
