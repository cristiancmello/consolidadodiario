package com.cristiancmello.consolidadodiario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RegistroConsolidadoDiarioRequest {
    private String valor;

    private String tipoMovimentacao;

    @JsonProperty(value = "data_hora_lancamento")
    private String dataHoraLancamento;

    @JsonProperty(value = "lancamento_id")
    private String lancamentoId;
}
