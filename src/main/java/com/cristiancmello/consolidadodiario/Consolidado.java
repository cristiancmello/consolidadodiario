package com.cristiancmello.consolidadodiario;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consolidado")
public class Consolidado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal valor;

    @Column(name = "tipo_movimentacao")
    @Enumerated(EnumType.ORDINAL)
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "data_hora_lancamento")
    private LocalDateTime dataEHoraDeLancamento;

    @Column(name = "lancamento_id")
    private Long lancamentoId;
}
