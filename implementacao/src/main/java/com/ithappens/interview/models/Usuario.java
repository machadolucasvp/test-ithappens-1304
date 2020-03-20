package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 40, message = "Nome deve possuir no mínimo 4 e no máximo 40 caracteres")
    private String nome;

    @Email
    @Column(unique=true)
    @NotEmpty(message = "Email não deve ser vazio")
    private String email;

    @NotEmpty(message = "Senha não deve ser vazia")
    private String password;


    private boolean ativo;

    private String fotoUrl;

    @OneToMany(mappedBy = "usuario")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("usuario")
    private Set<Pedido> pedidos = new HashSet<>();

}
