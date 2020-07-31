package com.ithappens.estoque.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ES01_ESTOQUE")
@SequenceGenerator(
        name = "ES01_ESTOQUE_SEQ",
        sequenceName = "ES01_ESTOQUE_SEQ",
        allocationSize = 1
)
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES01_ESTOQUE_SEQ")
    @Column(name="ES01_COD_ESTOQUE")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FKES01ES10_COD_FILIAL")
    private  Filial filial;

    @NotNull
    @OneToOne
    @JoinColumn(name="FKES01ES04_COD_PRODUTO")
    private Produto produto;

    @NotNull
    @Min(value = 0)
    @Column(name="ES01_QTD_TOTAL")
    private Integer quantidade;



}
