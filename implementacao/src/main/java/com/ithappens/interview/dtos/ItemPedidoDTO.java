package com.ithappens.interview.dtos;


import com.ithappens.interview.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Integer id;

    private Status status;

    private Double subTotal;

    private Double custoUnitario;

    private Integer quantidade;

    private ProdutoDTO produto;

}
