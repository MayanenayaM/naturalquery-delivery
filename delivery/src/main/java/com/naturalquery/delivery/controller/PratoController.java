package com.naturalquery.delivery.controller;

import com.naturalquery.delivery.dto.FiltroBuscaDTO;
import com.naturalquery.delivery.dto.PratoResultadoDTO;
import com.naturalquery.delivery.model.enums.Restricao;
import com.naturalquery.delivery.model.enums.Textura;
import com.naturalquery.delivery.service.BuscaPratoService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pratos")
@RequiredArgsConstructor
public class PratoController {

    private final BuscaPratoService buscaPratoService;

    @GetMapping
    public List<PratoResultadoDTO> buscar(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) Textura textura,
            @RequestParam(required = false) Set<Restricao> restricoes,
            @RequestParam(required = false) String categoria) {

        return buscaPratoService.buscar(new FiltroBuscaDTO(cidade, textura, restricoes, categoria));
    }
}