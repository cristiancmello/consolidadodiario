package com.cristiancmello.consolidadodiario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
    ConsolidadoDiarioResponse obtemConsolidadoDiario(@RequestParam("data") String data) {
        var formatoPadraoDeData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var localDateSolicitada = LocalDate.parse(data, formatoPadraoDeData);
        var inicioDoDiaSolicitada = localDateSolicitada.atStartOfDay();
        var fimDoDiaSolicitada = localDateSolicitada.atTime(LocalTime.MAX);

        var saldo = BigDecimal.valueOf(0);

        var consolidados = consolidadoRepository
            .findByDataEHoraDeLancamentoBetween(inicioDoDiaSolicitada, fimDoDiaSolicitada)
            .stream()
            .toList();

        for (var consolidado : consolidados) {
            saldo = saldo.add(consolidado.getValor());
        }

        var dataFormatada = inicioDoDiaSolicitada.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

        return ConsolidadoDiarioResponse.builder()
            .mensagem("No dia %s seu saldo foi de R$ %.2f".formatted(dataFormatada, saldo))
            .build();
    }
}
