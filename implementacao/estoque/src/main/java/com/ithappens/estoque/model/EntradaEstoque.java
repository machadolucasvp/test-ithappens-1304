package com.ithappens.estoque.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ES08_ENTRADA_ESTOQUE")
@SequenceGenerator(
        name="ES08_ENTRADA_ESTOQUE_SEQ",
        sequenceName = "ES08_ENTRADA_ESTOQUE_SEQ",
        allocationSize = 1
)
public class EntradaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES08_ENTRADA_ESTOQUE_SEQ")
    @Column(name="ES08_COD_ENTRADA_ESTOQUE")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "FKES08ES02_COD_PEDIDO_ESTOQUE")
    private PedidoEstoque pedidoEstoque;

    @NotNull
    @ManyToOne
    @JoinColumn(name="FKES08ES11_COD_USUARIO")
    private Usuario usuario;
}
