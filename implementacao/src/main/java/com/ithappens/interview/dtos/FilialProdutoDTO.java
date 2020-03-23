package com.ithappens.interview.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilialProdutoDTO {

    private Integer id;

    private String nome;

    private String descricao;

    private String codigoDeBarras;

    private Long sequencial;

    private Double custo;

    private Integer quantidadeEstoque;

}
