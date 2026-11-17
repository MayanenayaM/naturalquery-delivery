package com.naturalquery.delivery.repository;

import com.naturalquery.delivery.dto.FiltroBuscaDTO;
import com.naturalquery.delivery.model.Estabelecimento;
import com.naturalquery.delivery.model.Prato;
import com.naturalquery.delivery.model.enums.Restricao;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class PratoSpecifications {

    private PratoSpecifications() {
    }

    public static Specification<Prato> comFiltro(FiltroBuscaDTO filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            // Cidade (ignora maiúsculas/minúsculas)
            if (temTexto(filtro.cidade())) {
                Join<Prato, Estabelecimento> estabelecimento = root.join("estabelecimento");
                predicados.add(cb.equal(
                        cb.lower(estabelecimento.<String>get("cidade")),
                        filtro.cidade().trim().toLowerCase()));
            }

            // Textura
            if (filtro.textura() != null) {
                predicados.add(cb.equal(root.get("textura"), filtro.textura()));
            }

            // Categoria (ex.: Almoço, Suco)
            if (temTexto(filtro.categoria())) {
                predicados.add(cb.equal(
                        cb.lower(root.<String>get("categoria")),
                        filtro.categoria().trim().toLowerCase()));
            }

            // RN-06: cada restrição vira uma condição, e todas são combinadas com AND
            if (filtro.restricoes() != null) {
                for (Restricao restricao : filtro.restricoes()) {
                    predicados.add(cb.isMember(restricao, root.<Set<Restricao>>get("restricoes")));
                }
            }

            return cb.and(predicados.toArray(new Predicate[0]));
        };
    }

    private static boolean temTexto(String valor) {
        return valor != null && !valor.isBlank();
    }
}
