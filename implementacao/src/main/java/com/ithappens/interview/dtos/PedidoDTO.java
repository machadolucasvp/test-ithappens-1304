package com.ithappens.interview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.ItemPedido;
import com.ithappens.interview.models.Pagamento;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Integer id;

    private Tipo tipo;

    private String observacao;

    @Builder.Default
    private Set<ItemPedidoDTO> itemsPedido = new HashSet<>();

    private UsuarioDTO usuario;

    private ClienteDTO cliente;

    private Pagamento pagamento;

    private FilialPedidoDTO filial;

}
