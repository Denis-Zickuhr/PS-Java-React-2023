package br.com.banco.repository;

import br.com.banco.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT t FROM Transferencia t WHERE t.dataTransferencia <= :endDateTime")
    List<Transferencia> findByDataTransferenciaBefore(@Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT t FROM Transferencia t WHERE t.dataTransferencia >= :startDateTime")
    List<Transferencia> findByDataTransferenciaAfter(@Param("startDateTime") LocalDateTime startDateTime);

    @Query("SELECT t FROM Transferencia t WHERE t.dataTransferencia >= :startDateTime AND t.dataTransferencia <= :endDateTime")
    List<Transferencia> findByDataTransferenciaBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
