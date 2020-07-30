package com.ithappens.estoque.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="ES03_ITEM_PEDIDO")
@SequenceGenerator(
        name="ES03_ITEM_PEDIDO_SEQ",
        sequenceName = "ES03_ITEM_PEDIDO_SEQ",
        allocationSize = 1
)
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES03_ITEM_PEDIDO_SEQ")
    @Column(name="ES03_COD_ITEM_PEDIDO")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "FKES03ES02_COD_PEDIDO_ESTOQUE")
    private  PedidoEstoque pedidoEstoque;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FKES03ES04_COD_PRODUTO")
    private Produto produto;

    @NotNull
    @ManyToOne
    @JoinColumn(name="FKES03ES06_COD_STATUS")
    private StatusPedido statusPedido;

    @NotNull
    @Min(value = 1, message = "A quantidade informada deve ser maior que zero")
    @Column(name = "ES03_QUANTIDADE")
    private Integer quantidade;

    @NotNull
    @Min(value = 1, message = "O valor unitário deve ser informado")
    @Column(name="ES03_VALOR_UNITARIO")
    private BigDecimal valorUnitario;


}
