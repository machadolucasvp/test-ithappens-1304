package com.ithappens.interview.services;

import com.ithappens.interview.dtos.FilialDTO;
import com.ithappens.interview.dtos.FilialPedidoDTO;
import com.ithappens.interview.dtos.FilialProdutoDTO;
import com.ithappens.interview.dtos.ProdutoDTO;
import com.ithappens.interview.models.*;
import com.ithappens.interview.repositories.FilialProdutoRepository;
import com.ithappens.interview.repositories.FilialRepository;
import com.ithappens.interview.repositories.ProdutoRepository;
import jdk.jfr.StackTrace;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilialService {

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    FilialProdutoRepository filialProdutoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    public Filial findById(Integer id) {
        Optional<Filial> filial = filialRepository.findById(id);
        return filial.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

    public FilialDTO asDTO(Filial filial) {
        Integer filialId = filial.getId();
        Set<FilialProdutoDTO> produtos = filial.getProdutos().stream().map(
                oldProd -> {
                    Optional<FilialProduto> filialProduto =
                            filialProdutoRepository.findByFilialIdAndProdutoId(filialId, oldProd.getProduto().getId());

                    return new FilialProdutoDTO(oldProd.getProduto().getId(), oldProd.getProduto().getNome(),
                            oldProd.getProduto().getDescricao(), oldProd.getProduto().getCodigoDeBarras(),
                            oldProd.getProduto().getSequencial(), oldProd.getProduto().getCusto(), filialProduto.get()
                            .getQuantidadeEstoque());
                }
        ).collect(Collectors.toSet());

        return FilialDTO.builder().id(filial.getId()).nome(filial.getNome()).produtos(produtos).build();

    }

    public void removeProduto(Produto produto, Filial filial, Integer quantidadeProdutos) {
        if (quantidadeProdutos <= 0) throw new DataIntegrityViolationException("Não foi possível " +
                "realizar a operação de saída de produtos do estoque, quantidade invalída ou" + "estoque vazio");
        try {
            Optional<FilialProduto> filialProduto =
                    filialProdutoRepository.findByFilialIdAndProdutoId(filial.getId(), produto.getId());

            if (filialProduto.isPresent()) {
                int variacaoEstoque = filialProduto.get().getQuantidadeEstoque() - quantidadeProdutos;

                if (variacaoEstoque < 0) throw new DataIntegrityViolationException("Não foi possível realizar" +
                        "a operação de saída de produtos do estoque, quantidade desejada é menor que a disponível");

                filialProduto.get().removeEstoque(quantidadeProdutos);
                filialProdutoRepository.save(filialProduto.get());
            } else {
                throw new ObjectNotFoundException(produto.getId(), FilialProduto.class.getName());
            }

        } catch (DataAccessException exception) {
            throw new DataIntegrityViolationException("Não foi possível realizar a retirada do produto no estoque");
        }
    }

    public void addProduto(Produto produto, Filial filial, Integer quantidadeProdutos) {
        if (quantidadeProdutos <= 0) return;
        try {
            if (produto.getId() != null) {
                Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());

                if (produtoExistente.isPresent()) {
                    produto = produtoExistente.get();
                    Optional<FilialProduto> filialProduto =
                            filialProdutoRepository.findByFilialIdAndProdutoId(filial.getId(), produto.getId());
                    if (filialProduto.isPresent()) {
                        filialProduto.get().addEstoque(quantidadeProdutos);
                        filialProdutoRepository.save(filialProduto.get());
                        return;
                    } else {
                        throw new ObjectNotFoundException(produto.getId(), FilialProduto.class.getName());
                    }
                }

            }
            produto.setId(null);
            FilialProduto filialProduto = FilialProduto.builder()
                    .produto(produto).filial(filial).quantidadeEstoque(quantidadeProdutos).build();

            filialRepository.save(filial);
            produtoRepository.save(produto);
            filialProdutoRepository.save(filialProduto);

        } catch (DataAccessException exception) {
            throw new DataIntegrityViolationException("Não foi possível persistir o objeto para o estoque");
        }
    }

    public FilialPedidoDTO filialPedidoAsDTO(Filial filial){
        return FilialPedidoDTO.builder().id(filial.getId())
                .nome(filial.getNome())
                .build();
    }

}
