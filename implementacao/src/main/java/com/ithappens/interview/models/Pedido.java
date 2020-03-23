package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ithappens.interview.enums.Status;
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

    private Double custoTotal;

    @OneToMany(mappedBy = "pedido")
    @JsonIgnoreProperties(value = "pedido", allowSetters = true)
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<ItemPedido> itemsPedido = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("pedidoCliente")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties("produtos")
    private Filial filial;

    public double calculaCustoTotal() {
        return this.getItemsPedido().stream()
                .filter(item -> !item.getStatus().equals(Status.CANCELADO))
                .mapToDouble(item -> item.getSubTotal())
                .sum();
    }


}
