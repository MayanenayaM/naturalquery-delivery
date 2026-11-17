package com.naturalquery.delivery.service;

import com.naturalquery.delivery.dto.FiltroBuscaDTO;
import com.naturalquery.delivery.dto.PratoResultadoDTO;
import com.naturalquery.delivery.mapper.PratoMapper;
import com.naturalquery.delivery.model.Prato;
import com.naturalquery.delivery.model.enums.CategoriaAvaliacao;
import com.naturalquery.delivery.repository.AvaliacaoRepository;
import com.naturalquery.delivery.repository.AvaliacaoRepository.NotaMedia;
import com.naturalquery.delivery.repository.PratoRepository;
import com.naturalquery.delivery.repository.PratoSpecifications;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuscaPratoService {

    private static final List<CategoriaAvaliacao> CATEGORIAS_DE_CONTEXTO = List.of(
            CategoriaAvaliacao.ACESSIBILIDADE,
            CategoriaAvaliacao.ATENDIMENTO_ESPECIALIZADO);

    private final PratoRepository pratoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final PratoMapper pratoMapper;

    @Transactional(readOnly = true)
    public List<PratoResultadoDTO> buscar(FiltroBuscaDTO filtro) {
        // 1. Filtra no banco (cidade, textura, categoria, restrições com AND)
        List<Prato> pratos = pratoRepository.findAll(PratoSpecifications.comFiltro(filtro));

        if (pratos.isEmpty()) {
            return List.of();
        }

        // 2. Busca a nota de contexto dos estabelecimentos envolvidos (RN-07)
        Set<Long> idsEstabelecimentos = pratos.stream()
                .map(prato -> prato.getEstabelecimento().getId())
                .collect(Collectors.toSet());

        Map<Long, Double> notas = avaliacaoRepository
                .mediaPorEstabelecimento(CATEGORIAS_DE_CONTEXTO, idsEstabelecimentos)
                .stream()
                .collect(Collectors.toMap(NotaMedia::getEstabelecimentoId, NotaMedia::getMedia));

        // 3. Converte e ordena: maior nota de contexto primeiro, depois ordem alfabética
        return pratos.stream()
                .map(prato -> pratoMapper.toDto(
                        prato,
                        notas.getOrDefault(prato.getEstabelecimento().getId(), 0.0)))
                .sorted(Comparator.comparingDouble(PratoResultadoDTO::notaContexto).reversed()
                        .thenComparing(PratoResultadoDTO::nome))
                .toList();
    }
}