package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ithappens.interview.enums.Tipo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private Tipo tipo;

    private String nota;

    @OneToMany(mappedBy = "pedido")
    @JsonIgnoreProperties("pedido")
    @EqualsAndHashCode.Exclude
    private Set<ItemPedido> itemsPedido = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Usuario usuario;

}
