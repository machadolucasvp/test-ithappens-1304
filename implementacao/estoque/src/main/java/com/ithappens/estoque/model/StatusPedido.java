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
@Table(name = "ES06_STATUS_PEDIDO")
@SequenceGenerator(
        name = "ES06_STATUS_PEDIDO_SEQ",
        sequenceName = "ES06_STATUS_PEDIDO_SEQ",
        allocationSize = 1
)
public class StatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES06_STATUS_PEDIDO_SEQ")
    @Column(name="ES06_COD_STATUS_PEDIDO")
    private Long id;

    @NotBlank(message = "A descricao do status do pedido deve ser informada")
    @Column(name = "ES06_DESCRICAO")
    private String descricao;
}
