package com.cristiancmello.consolidadodiario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("consolidado-diario")
public class ConsolidadoDiarioController {
    @Autowired
    ConsolidadoRepository consolidadoRepository;

    @PostMapping
    RegistroConsolidadoDiarioResponse registraLancamentoDiario(@RequestBody RegistroConsolidadoDiarioRequest request) {
        var valor = new BigDecimal(request.getValor());

        if (TipoMovimentacao.valueOf(request.getTipoMovimentacao()).equals(TipoMovimentacao.DEBITO)) {
            valor = valor.negate();
        }

        var consolidado = Consolidado.builder()
            .dataEHoraDeLancamento(LocalDateTime.parse(request.getDataHoraLancamento()))
            .valor(valor)
            .tipoMovimentacao(TipoMovimentacao.valueOf(request.getTipoMovimentacao()))
            .lancamentoId(Long.valueOf(request.getLancamentoId()))
            .build();

        consolidadoRepository.save(consolidado);

        consolidadoRepository.findAll().forEach(System.out::println);

        return RegistroConsolidadoDiarioResponse.builder()
            .mensagem("Registrado com sucesso! Contabilizando...")
            .build();
    }

    @GetMapping
    String obtemConsolidadoDiario() {
        var consolidados = consolidadoRepository.findAll();
        var saldo = BigDecimal.valueOf(0);

        for (var consolidado : consolidados) {
            saldo = saldo.add(consolidado.getValor());
        }

        return saldo.toString();
    }
}
