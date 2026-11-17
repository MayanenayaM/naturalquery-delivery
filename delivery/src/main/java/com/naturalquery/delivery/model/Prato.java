package com.naturalquery.delivery.model;

import com.naturalquery.delivery.model.enums.Restricao;
import com.naturalquery.delivery.model.enums.Textura;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pratos")
@Getter
@Setter
@NoArgsConstructor
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    // Texto livre para exibição e busca por palavra-chave (ex.: "Almoço", "Suco")
    @Column(nullable = false, length = 40)
    private String categoria;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Textura textura;

    // Restrições alimentares que o prato ATENDE
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "prato_restricoes", joinColumns = @JoinColumn(name = "prato_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "restricao", length = 20)
    private Set<Restricao> restricoes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;
}
