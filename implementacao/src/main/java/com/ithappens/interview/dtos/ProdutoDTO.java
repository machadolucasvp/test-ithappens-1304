package com.ithappens.interview.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO  {

    private Integer id;

    private String nome;

    private String descricao;

    private String codigoDeBarras;

    private Integer sequencial;

    private Double custo;

}
