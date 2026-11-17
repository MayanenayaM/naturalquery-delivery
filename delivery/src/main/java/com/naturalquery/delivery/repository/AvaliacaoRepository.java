package com.naturalquery.delivery.repository;

import com.naturalquery.delivery.model.Avaliacao;
import com.naturalquery.delivery.model.enums.CategoriaAvaliacao;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // Projeção: o Spring preenche essa interface com os aliases da consulta
    interface NotaMedia {
        Long getEstabelecimentoId();

        Double getMedia();
    }

    @Query("""
            SELECT a.estabelecimento.id AS estabelecimentoId, AVG(a.nota) AS media
            FROM Avaliacao a
            WHERE a.categoria IN :categorias
              AND a.estabelecimento.id IN :ids
            GROUP BY a.estabelecimento.id
            """)
    List<NotaMedia> mediaPorEstabelecimento(
            @Param("categorias") Collection<CategoriaAvaliacao> categorias,
            @Param("ids") Collection<Long> ids);
}
