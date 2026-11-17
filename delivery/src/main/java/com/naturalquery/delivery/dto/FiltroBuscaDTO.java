package com.naturalquery.delivery.dto;

import com.naturalquery.delivery.model.enums.Restricao;
import com.naturalquery.delivery.model.enums.Textura;
import java.util.Set;

public record FiltroBuscaDTO(
        String cidade,
        Textura textura,
        Set<Restricao> restricoes,
        String categoria) {
}
