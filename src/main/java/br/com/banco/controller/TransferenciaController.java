package br.com.banco.controller;

import br.com.banco.model.Transferencia;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.banco.repository.TransferenciaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaController(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @GetMapping
    public List<Transferencia> getAllTransferencias() {
        return transferenciaRepository.findAll();
    }

    @PostMapping
    public Transferencia createTransferencia(@RequestBody Transferencia transferencia) {
        return transferenciaRepository.save(transferencia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> getTransferenciaById(@PathVariable Long id) {
        Optional<Transferencia> optionalTransferencia = transferenciaRepository.findById(id);
        return optionalTransferencia.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta/{contaId}")
    public List<Transferencia> getTransferenciasByContaId(@PathVariable Long contaId) {
        return transferenciaRepository.findByContaId(contaId);
    }

    @GetMapping("/between-dates")
    public List<Transferencia> getTransferenciasBetweenDatesAndNomeResponsavel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
            @RequestParam(required = false) String nomeResponsavel) {

        return transferenciaRepository.findByDataTransferenciaAndNomeResponsavel(startDateTime, endDateTime, nomeResponsavel);
    }

    @GetMapping("/count")
    public BigDecimal countTransferencias() {
        return transferenciaRepository.getTotalTransferenciaValor();
    }
}