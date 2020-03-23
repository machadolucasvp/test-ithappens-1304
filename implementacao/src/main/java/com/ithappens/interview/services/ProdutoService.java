package com.ithappens.interview.services;

import com.ithappens.interview.dtos.ProdutoDTO;
import com.ithappens.interview.models.Produto;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    public ProdutoDTO asDTO(Produto produto) {
        return ProdutoDTO.builder().nome(produto.getNome())
                .codigoDeBarras(produto.getCodigoDeBarras())
                .custo(produto.getCusto())
                .descricao(produto.getDescricao())
                .id(produto.getId())
                .sequencial(produto.getSequencial())
                .build();

    }

}
