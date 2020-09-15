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
@Table(name="ES07_FORMA_PAG")
@SequenceGenerator(
        name = "ES07_FORMA_PAG_SEQ",
        sequenceName = "ES07_FORMA_PAG_SEQ",
        allocationSize = 1
)
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES07_FORMA_PAG_SEQ")
    @Column(name = "ES07_COD_FORMA_PAG")
    private Long id;

    @NotBlank(message = "A descrição da forma de pagamento deve ser informada")
    @Column(name = "ES07_DESCRICAO")
    private String descricao;
}
