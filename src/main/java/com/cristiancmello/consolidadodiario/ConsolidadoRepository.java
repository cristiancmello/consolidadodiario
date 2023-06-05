package com.cristiancmello.consolidadodiario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsolidadoRepository extends CrudRepository<Consolidado, Long> {
    List<Consolidado> findByDataEHoraDeLancamentoBetween(LocalDateTime dataEHoraDeLancamentoInicial, LocalDateTime dataEHoraDeLancamentoFinal);
}
