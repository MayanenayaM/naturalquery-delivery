package com.naturalquery.delivery.config;

import static com.naturalquery.delivery.model.enums.CategoriaAvaliacao.ACESSIBILIDADE;
import static com.naturalquery.delivery.model.enums.CategoriaAvaliacao.ATENDIMENTO_ESPECIALIZADO;
import static com.naturalquery.delivery.model.enums.CategoriaAvaliacao.GERAL;
import static com.naturalquery.delivery.model.enums.Restricao.LOW_CARB;
import static com.naturalquery.delivery.model.enums.Restricao.SEM_ACUCAR;
import static com.naturalquery.delivery.model.enums.Restricao.SEM_GLUTEN;
import static com.naturalquery.delivery.model.enums.Restricao.SEM_LACTOSE;
import static com.naturalquery.delivery.model.enums.Restricao.VEGETARIANO;
import static com.naturalquery.delivery.model.enums.Textura.LIQUIDA;
import static com.naturalquery.delivery.model.enums.Textura.MACIA;
import static com.naturalquery.delivery.model.enums.Textura.NORMAL;
import static com.naturalquery.delivery.model.enums.Textura.PASTOSA;

import com.naturalquery.delivery.model.Avaliacao;
import com.naturalquery.delivery.model.Estabelecimento;
import com.naturalquery.delivery.model.Prato;
import com.naturalquery.delivery.model.enums.CategoriaAvaliacao;
import com.naturalquery.delivery.model.enums.Restricao;
import com.naturalquery.delivery.model.enums.Textura;
import com.naturalquery.delivery.repository.EstabelecimentoRepository;
import com.naturalquery.delivery.repository.PratoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PratoRepository pratoRepository;

    @Override
    public void run(String... args) {
      
        if (estabelecimentoRepository.count() > 0) {
            log.info("Seed ignorado: o banco já possui dados.");
            return;
        }

        // ---------- Estabelecimentos (com avaliações) ----------
        Estabelecimento saborDeCasa = salvarEstabelecimento("Sabor de Casa Recife", "Recife", "PE",
                av(ACESSIBILIDADE, 3, "Degraus na entrada"),
                av(ATENDIMENTO_ESPECIALIZADO, 3, "Atendimento cordial, mas sem preparo específico"),
                av(GERAL, 4, "Comida caseira"));

        Estabelecimento cantinhoDaVovo = salvarEstabelecimento("Cantinho da Vovó", "Recife", "PE",
                av(ACESSIBILIDADE, 5, "Rampa de acesso e embalagens fáceis de abrir"),
                av(ATENDIMENTO_ESPECIALIZADO, 5, "Equipe orienta sobre texturas e restrições"),
                av(GERAL, 4, "Ótimo para idosos"));

        Estabelecimento verdeVida = salvarEstabelecimento("Verde Vida Natural", "Recife", "PE",
                av(ACESSIBILIDADE, 4, "Ambiente amplo"),
                av(ATENDIMENTO_ESPECIALIZADO, 4, "Conhece bem opções vegetarianas"),
                av(GERAL, 5, "Cardápio saudável"));

        Estabelecimento sucosECia = salvarEstabelecimento("Sucos & Cia", "Recife", "PE",
                av(ACESSIBILIDADE, 4, "Balcão baixo e cardápio em letra grande"),
                av(GERAL, 4, "Sucos frescos"));

        Estabelecimento casaPaulista = salvarEstabelecimento("Casa Paulista", "São Paulo", "SP",
                av(ACESSIBILIDADE, 5, "Totalmente acessível"),
                av(GERAL, 5, "Excelente"));

        Estabelecimento saboresDeOlinda = salvarEstabelecimento("Sabores de Olinda", "Olinda", "PE",
                av(ACESSIBILIDADE, 4, "Entrada plana"),
                av(ATENDIMENTO_ESPECIALIZADO, 3, "Atendimento simpático"));

        // ---------- Pratos ----------
        List<Prato> pratos = new ArrayList<>();

        // Sabor de Casa Recife
        pratos.add(prato(saborDeCasa, "Frango desfiado com purê de batata", "Almoço",
                "Frango cozido e desfiado com purê cremoso", "28.90", MACIA, SEM_GLUTEN));
        pratos.add(prato(saborDeCasa, "Carne moída com legumes macios", "Almoço",
                "Carne moída refogada com cenoura e abobrinha bem cozidas", "26.50", MACIA, SEM_LACTOSE));
        pratos.add(prato(saborDeCasa, "Bife grelhado com arroz e feijão", "Almoço",
                "Prato tradicional do dia a dia", "32.00", NORMAL, SEM_LACTOSE));

        // Cantinho da Vovó (note o "empate" com o frango do Sabor de Casa: RN-07)
        pratos.add(prato(cantinhoDaVovo, "Frango desfiado ao molho com purê", "Almoço",
                "Frango desfiado ao molho suave com purê de batata", "29.90", MACIA, SEM_GLUTEN));
        pratos.add(prato(cantinhoDaVovo, "Peixe cozido com purê de mandioquinha", "Almoço",
                "Filé de peixe cozido, muito macio, sem açúcar adicionado", "34.00", MACIA, SEM_GLUTEN, SEM_ACUCAR));
        pratos.add(prato(cantinhoDaVovo, "Sopa cremosa de legumes", "Sopa",
                "Legumes batidos, textura de creme", "18.00", PASTOSA, VEGETARIANO, SEM_LACTOSE, SEM_GLUTEN));
        pratos.add(prato(cantinhoDaVovo, "Mingau de aveia sem açúcar", "Lanche",
                "Mingau cremoso adoçado apenas com canela", "12.00", PASTOSA, SEM_ACUCAR, VEGETARIANO));

        // Verde Vida Natural
        pratos.add(prato(verdeVida, "Risoto cremoso de abóbora", "Almoço",
                "Arroz cremoso com abóbora, bem macio", "31.00", MACIA, VEGETARIANO, SEM_GLUTEN));
        pratos.add(prato(verdeVida, "Omelete de espinafre", "Almoço",
                "Omelete macia recheada com espinafre", "24.00", MACIA, LOW_CARB, VEGETARIANO, SEM_GLUTEN));
        pratos.add(prato(verdeVida, "Panqueca de banana e aveia", "Lanche",
                "Panqueca simples, sem farinha refinada", "16.00", NORMAL, VEGETARIANO));

        // Sucos & Cia (RN-06: combinação SEM_ACUCAR + SEM_GLUTEN)
        pratos.add(prato(sucosECia, "Suco verde sem açúcar", "Suco",
                "Couve, limão e gengibre", "12.00", LIQUIDA,
                SEM_ACUCAR, SEM_GLUTEN, SEM_LACTOSE, VEGETARIANO, LOW_CARB));
        pratos.add(prato(sucosECia, "Suco de limão com hortelã sem açúcar", "Suco",
                "Limão e hortelã, sem adição de açúcar", "10.00", LIQUIDA,
                SEM_ACUCAR, SEM_GLUTEN, SEM_LACTOSE, VEGETARIANO));
        pratos.add(prato(sucosECia, "Suco de laranja adoçado", "Suco",
                "Laranja com açúcar", "11.00", LIQUIDA, SEM_GLUTEN, SEM_LACTOSE, VEGETARIANO));
        pratos.add(prato(sucosECia, "Vitamina de banana com leite", "Suco",
                "Banana batida com leite", "14.00", LIQUIDA, VEGETARIANO, SEM_GLUTEN));

        // Casa Paulista (São Paulo: serve para testar o filtro de cidade)
        pratos.add(prato(casaPaulista, "Frango desfiado com purê de batata", "Almoço",
                "Frango desfiado com purê", "35.00", MACIA, SEM_GLUTEN));
        pratos.add(prato(casaPaulista, "Suco verde sem açúcar", "Suco",
                "Couve, maçã verde e limão", "15.00", LIQUIDA, SEM_ACUCAR, SEM_GLUTEN, VEGETARIANO));

        // Sabores de Olinda (mesmo estado, outra cidade)
        pratos.add(prato(saboresDeOlinda, "Cuscuz macio com ovo", "Almoço",
                "Cuscuz bem hidratado com ovo mexido", "15.00", MACIA, SEM_LACTOSE, VEGETARIANO));
        pratos.add(prato(saboresDeOlinda, "Caldo de feijão batido", "Sopa",
                "Feijão batido, textura cremosa", "14.00", PASTOSA, SEM_GLUTEN, SEM_LACTOSE));

        pratoRepository.saveAll(pratos);

        log.info("Seed concluído: {} estabelecimentos e {} pratos.",
                estabelecimentoRepository.count(), pratoRepository.count());
    }

    // ---------- Métodos auxiliares ----------

    private Estabelecimento salvarEstabelecimento(String nome, String cidade, String uf, Avaliacao... avaliacoes) {
        Estabelecimento estabelecimento = new Estabelecimento(nome, cidade, uf);
        Arrays.stream(avaliacoes).forEach(estabelecimento::adicionarAvaliacao);
        return estabelecimentoRepository.save(estabelecimento);
    }

    private Avaliacao av(CategoriaAvaliacao categoria, int nota, String comentario) {
        return new Avaliacao(categoria, nota, comentario);
    }

    private Prato prato(Estabelecimento estabelecimento, String nome, String categoria, String descricao,
                        String preco, Textura textura, Restricao... restricoes) {
        Prato prato = new Prato();
        prato.setEstabelecimento(estabelecimento);
        prato.setNome(nome);
        prato.setCategoria(categoria);
        prato.setDescricao(descricao);
        prato.setPreco(new BigDecimal(preco));
        prato.setTextura(textura);
        prato.setRestricoes(new HashSet<>(Arrays.asList(restricoes)));
        return prato;
    }
}
