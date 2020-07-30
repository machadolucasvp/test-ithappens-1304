package com.ithappens.estoque.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ES09_COMPRA")
@SequenceGenerator(
        name = "ES09_COMPRA_SEQ",
        sequenceName = "ES09_COMPRA_SEQ",
        allocationSize = 1
)
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES09_COMPRA_SEQ")
    @Column(name="ES09_COD_COMPRA")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "FKES09ES02_COD_PEDIDO_ESTOQUE")
    private PedidoEstoque pedidoEstoque;

    @NotNull
    @OneToOne
    @JoinColumn(name = "FKES09ES07_COD_FORMA_PAG")
    private FormaPagamento formaPagamento;

    @NotNull
    @ManyToOne
    @JoinColumn(name="FKES09ES11_COD_USUARIO")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FKES09ES12_COD_CLIENTE")
    private Cliente cliente;
}
