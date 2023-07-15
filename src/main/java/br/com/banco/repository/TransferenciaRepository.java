package br.com.banco.repository;

import br.com.banco.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT t FROM Transferencia t WHERE " +
            "(:startDateTime IS NULL OR t.dataTransferencia >= :startDateTime) AND " +
            "(:endDateTime IS NULL OR t.dataTransferencia <= :endDateTime) AND " +
            "(:nomeResponsavel IS NULL OR t.conta.nomeResponsavel LIKE %:nomeResponsavel%)")
    List<Transferencia> findByDataTransferenciaAndNomeResponsavel(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("nomeResponsavel") String nomeResponsavel
    );
    List<Transferencia> findByContaId(Long contaId);

    @Query("SELECT SUM(t.valor) FROM Transferencia t")
    BigDecimal getTotalTransferenciaValor();

}