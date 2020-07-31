package com.ithappens.estoque.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ES12_CLIENTE")
@SequenceGenerator(
        name = "ES12_CLIENTE_SEQ",
        sequenceName = "ES12_CLIENTE_SEQ",
        allocationSize = 1
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES12_CLIENTE_SEQ")
    @Column(name="ES12_COD_CLIENTE")
    private Long id;

    @NotBlank(message = "O nome do cliente deve ser informado")
    @Column(name="ES12_NOME")
    private  String nome;

    @CPF(message = "Informe um CPF válido")
    @NotBlank(message = "O CPF do cliente deve ser informado")
    @Column(name="ES12_CPF")
    private String cpf;
}
