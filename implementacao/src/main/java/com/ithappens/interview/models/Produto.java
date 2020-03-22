package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private String codigoDeBarras;

    private Long sequencial;

    private Double custo;

    @OneToMany(mappedBy = "produto")
    @EqualsAndHashCode.Exclude
    private Set<ItemPedido> itemPedido = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"produto", "filial", "produto_id"})
    private Set<FilialProduto> filiais = new HashSet<>(0);

}
