package com.naturalquery.delivery.mapper;

import com.naturalquery.delivery.dto.PratoResultadoDTO;
import com.naturalquery.delivery.model.Estabelecimento;
import com.naturalquery.delivery.model.Prato;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PratoMapper {

    public PratoResultadoDTO toDto(Prato prato, double notaContexto) {
        Estabelecimento estabelecimento = prato.getEstabelecimento();

        return new PratoResultadoDTO(
                prato.getId(),
                prato.getNome(),
                prato.getDescricao(),
                prato.getCategoria(),
                prato.getPreco(),
                prato.getTextura(),
                Set.copyOf(prato.getRestricoes()),
                estabelecimento.getNome(),
                estabelecimento.getCidade(),
                estabelecimento.getUf(),
                Math.round(notaContexto * 10) / 10.0);
    }
}
