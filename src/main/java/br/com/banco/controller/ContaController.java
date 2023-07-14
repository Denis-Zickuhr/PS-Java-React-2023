package br.com.banco.controller;

import br.com.banco.model.Conta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.banco.repository.ContaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaRepository contaRepository;

    public ContaController(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @GetMapping
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    @PostMapping
    public Conta createConta(@RequestBody Conta conta) {
        return contaRepository.save(conta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Optional<Conta> optionalConta = contaRepository.findById(id);
        return optionalConta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}