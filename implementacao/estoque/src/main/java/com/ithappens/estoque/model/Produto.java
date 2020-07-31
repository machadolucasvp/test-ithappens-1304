package com.ithappens.estoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ES04_PRODUTO")
@SequenceGenerator(
        name="ES04_PRODUTO_SEQ",
        sequenceName = "ES04_PRODUTO_SEQ",
        allocationSize = 1
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES04_PRODUTO_SEQ")
    @Column(name = "ES04_COD_PRODUTO")
    private Long id;

    @NotBlank(message = "O código de barras do produto deve ser informado")
    @Column(name = "ES04_CDG_BARRAS")
    private String cdgBarras;

    @NotBlank(message = "A descrição do produto deve ser informada")
    @Column(name="ES04_DESCRICAO")
    private String descricao;
}
