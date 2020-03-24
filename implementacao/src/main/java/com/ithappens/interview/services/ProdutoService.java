package com.ithappens.interview.services;

import com.ithappens.interview.dtos.ProdutoDTO;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.models.Produto;
import com.ithappens.interview.repositories.ProdutoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto findById(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

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
