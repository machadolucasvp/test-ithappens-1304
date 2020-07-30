package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ES02_PEDIDO_ESTOQUE")
@SequenceGenerator(
        name="ES02_PEDIDO_ESTOQUE_SEQ",
        sequenceName = "ES02_PEDIDO_ESTOQUE_SEQ",
        allocationSize = 1
)
public class PedidoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES02_PEDIDO_ESTOQUE_SEQ")
    @Column(name="ES02_COD_PEDIDO_ESTOQUE")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FKES02ES05_COD_TIPO_PEDIDO")
    private TipoPedido tipoPedido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FKES02ES10_COD_FILIAL")
    private Filial filial;

    @NotNull
    @Min(value = 1)
    @Column(name = "ES02_VALOR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name="ES02_OBS_ENTREGA")
    private String observacao;

}
