package com.naturalquery.delivery.dto;

import com.naturalquery.delivery.model.enums.Restricao;
import com.naturalquery.delivery.model.enums.Textura;
import java.math.BigDecimal;
import java.util.Set;

public record PratoResultadoDTO(
        Long id,
        String nome,
        String descricao,
        String categoria,
        BigDecimal preco,
        Textura textura,
        Set<Restricao> restricoes,
        String estabelecimento,
        String cidade,
        String uf,
        double notaContexto) {
}
