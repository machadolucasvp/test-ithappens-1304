package com.ithappens.estoque.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Entity
@Table(name = "ES10_FILIAL")
@SequenceGenerator(
        name = "ES10_FILIAL_SEQ",
        sequenceName = "ES10_FILIAL_SEQ",
        allocationSize = 1
)
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES10_FILIAL_SEQ")
    @Column(name="ES10_COD_FILIAL")
    private Long id;

    @NotBlank(message = "A descrição da filial deve ser informada")
    @Column(name = "ES10_DESCRICAO")
    private String descricao;
}
