package com.ithappens.interview.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 40, message = "Nome deve possuir no mínimo 4 e no máximo 40 caracteres")
    private String nome;

    @ManyToMany(mappedBy = "filial")
    @EqualsAndHashCode.Exclude
    private Set<Produto> produtos = new HashSet<>();

    public boolean addProdutos(Produto produto){
        return this.produtos.add(produto);
    }

}
