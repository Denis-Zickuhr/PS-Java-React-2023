package br.com.banco.controller;

import br.com.banco.model.Conta;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.ContaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import br.com.banco.repository.TransferenciaRepository;

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


    @GetMapping("/between-dates")
    public List<Transferencia> getTransferenciasBetweenDates(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {

        if (startDateTime == null && endDateTime == null) {
            return transferenciaRepository.findAll();
        } else if (startDateTime == null) {
            return transferenciaRepository.findByDataTransferenciaBefore(endDateTime);
        } else if (endDateTime == null) {
            return transferenciaRepository.findByDataTransferenciaAfter(startDateTime);
        } else {
            return transferenciaRepository.findByDataTransferenciaBetween(startDateTime, endDateTime);
        }
    }

}