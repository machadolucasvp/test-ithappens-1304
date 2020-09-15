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
@Table(name="ES05_TIPO_PEDIDO")
@SequenceGenerator(
        name = "ES05_TIPO_PEDIDO_SEQ",
        sequenceName = "ES05_TIPO_PEDIDO_SEQ",
        allocationSize = 1
)
public class TipoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES05_TIPO_PEDIDO_SEQ")
    @Column(name = "ES05_COD_TIPO_PEDIDO")
    private Long id;

    @NotBlank(message = "A descrição do produto deve ser informada")
    @Column(name = "ES05_DESCRICAO")
    private String descricao;
}
