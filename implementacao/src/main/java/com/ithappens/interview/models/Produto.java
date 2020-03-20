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

    private Double custo;

    @OneToMany(mappedBy = "produto")
    @EqualsAndHashCode.Exclude
    private Set<ItemPedido> itemPedido = new HashSet<>();

    @JsonIgnoreProperties("produtos")
    @ManyToMany
    @EqualsAndHashCode.Exclude
    @JoinTable(name="produto_filial",
            joinColumns = {@JoinColumn(name="fk_produto")},
            inverseJoinColumns = {@JoinColumn(name="fk_filial") })
    private Set<Filial> filial = new HashSet<>(0);

    public boolean addFilial(Filial filial){
        return this.filial.add(filial);
    }

}
