package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ithappens.interview.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private Double custoUnitario;

    private Integer quantidade;

    @ManyToOne
    private Pedido pedido;

}
