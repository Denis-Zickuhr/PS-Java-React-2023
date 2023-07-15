package br.com.banco;

import br.com.banco.model.Conta;
import br.com.banco.model.TIPO_TRANSACAO;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.ContaRepository;
import br.com.banco.repository.TransferenciaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@EnableAutoConfiguration
@ContextConfiguration(classes = {TransferenciaRepository.class, ContaRepository.class})
@DataJpaTest
public class BancoApplicationTests {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Test
    public void testContaInsertion() {

        int expectedOutput = contaRepository.findAll().size() + 1;

        Conta conta = new Conta("Antonio Carlos");
        contaRepository.save(conta);

        int actualOutput = contaRepository.findAll().size();
        contaRepository.delete(conta);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testTransferenciaInsertion() {

        int initialTransferenciaSize = transferenciaRepository.findAll().size();

        Conta conta = new Conta("Caio Castro");
        contaRepository.save(conta);

        Transferencia transferencia1 = new Transferencia(LocalDateTime.now(), new BigDecimal(100), TIPO_TRANSACAO.DEPOSITO.name(), "Operator 1", conta);
        transferenciaRepository.save(transferencia1);
        Transferencia transferencia2 = new Transferencia(LocalDateTime.now(), new BigDecimal(200), TIPO_TRANSACAO.SAQUE.name(), "Operator 2", conta);
        transferenciaRepository.save(transferencia2);

        List<Transferencia> transferencias = transferenciaRepository.findAll();

        transferenciaRepository.delete(transferencia1);
        transferenciaRepository.delete(transferencia2);
        contaRepository.delete(conta);

        Assertions.assertEquals((initialTransferenciaSize + 2), transferencias.size());
    }

    @Test
    public void testTransferenciasBetweenTime() {

        int expectedOutput = 2;

        LocalDateTime initDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(10);

        Conta conta = new Conta("Caio Castro");
        contaRepository.save(conta);

        /* Adiciona  duas transferências até a data limite, a primeira sendo ao mesmo tempo (no limite) e a segunda um
         * valor de folga
         * */
        Transferencia transferencia1 = new Transferencia(initDate, new BigDecimal(100), TIPO_TRANSACAO.DEPOSITO.name(), "Operator 1", conta);
        transferenciaRepository.save(transferencia1);
        Transferencia transferencia2 = new Transferencia(initDate.plusDays(2), new BigDecimal(200), TIPO_TRANSACAO.SAQUE.name(), "Operator 2", conta);
        transferenciaRepository.save(transferencia2);

        /* Adiciona  uma transferência fora da data limite */
        Transferencia transferencia3 = new Transferencia(initDate.plusDays(11), new BigDecimal(100), TIPO_TRANSACAO.DEPOSITO.name(), "Operator 1", conta);
        transferenciaRepository.save(transferencia3);

        int actualOutput = transferenciaRepository.findByDataTransferenciaBetween(initDate, endDate).size();

        transferenciaRepository.delete(transferencia1);
        transferenciaRepository.delete(transferencia2);
        transferenciaRepository.delete(transferencia3);
        contaRepository.delete(conta);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testTransferenciaDeletion() {

        int expectedOutput = transferenciaRepository.findAll().size();

        Conta conta = new Conta("Caio Castro");
        contaRepository.save(conta);

        Transferencia transferencia1 = new Transferencia(LocalDateTime.now(), new BigDecimal(100), TIPO_TRANSACAO.DEPOSITO.name(), "Operator 1", conta);
        transferenciaRepository.save(transferencia1);

        transferenciaRepository.delete(transferencia1);
        contaRepository.delete(conta);

        int actualOutput = transferenciaRepository.findAll().size();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testContaDeletion() {
        Conta conta = new Conta("Marcelo de Souza");
        contaRepository.save(conta);

        contaRepository.delete(conta);

        Conta retrievedConta = contaRepository.findById(conta.getId()).orElse(null);
        Assertions.assertNull(retrievedConta);
    }

    @Test
    public void testContaEdit() {

        Conta conta = new Conta("Olavo de carvalho");
        contaRepository.save(conta);

        String expectedOutput = "Vasco";
        conta.setNomeResponsavel(expectedOutput);
        contaRepository.save(conta);

        Conta retrievedConta = contaRepository.findById(conta.getId()).orElse(null);
        Assertions.assertNotNull(retrievedConta);
        Assertions.assertEquals(expectedOutput, retrievedConta.getNomeResponsavel());

        contaRepository.delete(conta);
    }

    @Test
    public void testTransferenciaEdit() {

        Conta conta = new Conta("Caio Castro");
        contaRepository.save(conta);

        Transferencia transferencia = new Transferencia(LocalDateTime.now(), new BigDecimal(100), TIPO_TRANSACAO.DEPOSITO.name(), "Operator 1", conta);
        transferenciaRepository.save(transferencia);

        String expectedOutput = TIPO_TRANSACAO.SAQUE.name();
        transferencia.setTipo(expectedOutput);
        transferenciaRepository.save(transferencia);

        Transferencia retrievedTransferencia = transferenciaRepository.findById(transferencia.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTransferencia);
        Assertions.assertEquals(expectedOutput, retrievedTransferencia.getTipo());

        transferenciaRepository.delete(transferencia);
        contaRepository.delete(conta);
    }



    @Test
    public void testGetContaById() {

        Conta conta = new Conta("Lucas de Souza");
        contaRepository.save(conta);

        Optional<Conta> optionalConta = contaRepository.findById(conta.getId());
        Assertions.assertTrue(optionalConta.isPresent());

        contaRepository.delete(conta);
    }
}
