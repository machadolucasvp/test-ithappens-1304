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
@Table(name = "ES11_USUARIO")
@SequenceGenerator(
        name = "ES11_USUARIO_SEQ",
        sequenceName = "ES11_USUARIO_SEQ",
        allocationSize = 1
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ES11_USUARIO_SEQ")
    @Column(name = "ES11_COD_USUARIO")
    private Long id;

    @NotBlank(message = "O nome do usuário deve ser informado")
    @Column(name = "ES11_NOME")
    private String nome;

    @CPF(message = "Informe um CPF válido")
    @NotBlank(message = "O CPF do usuário deve ser informado")
    @Column(name="ES11_CPF")
    private String cpf;

    @NotBlank(message = "A senha do usuário deve ser informada")
    @Column(name = "ES11_SENHA")
    private String senha;


}
