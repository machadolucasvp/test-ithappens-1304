package com.ithappens.interview.services;

import com.ithappens.interview.dtos.*;
import com.ithappens.interview.models.*;
import com.ithappens.interview.repositories.FilialProdutoRepository;
import com.ithappens.interview.repositories.FilialRepository;
import com.ithappens.interview.repositories.ProdutoRepository;
import jdk.jfr.StackTrace;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilialService {

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    FilialProdutoRepository filialProdutoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    public Optional<Filial> findByIdAsOptional(Integer id) {
        return filialRepository.findById(id);
    }

    public Filial findById(Integer id) {
        Optional<Filial> filial = filialRepository.findById(id);
        return filial.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

    public Filial post(Filial filial){
        return filialRepository.save(filial);
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

    public FilialPedidoDTO filialPedidoAsDTO(Filial filial) {
        return FilialPedidoDTO.builder().id(filial.getId())
                .nome(filial.getNome())
                .build();
    }

    public Page<FilialProdutoDTO> findFilialProdutosPage(Integer filialId, Integer page, Integer size, String orderBy, String direction) {
        return filialProdutoRepository.findByFilialId(filialId,
                PageRequest.of(page, size, Sort.Direction.fromString(orderBy), direction))
                .map(this::filialProdutoAsDTO);
    }

    public FilialProdutoDTO filialProdutoAsDTO(FilialProduto filialProduto) {
        return FilialProdutoDTO.builder().codigoDeBarras(filialProduto.getProduto().getCodigoDeBarras())
                .custo(filialProduto.getProduto().getCusto())
                .descricao(filialProduto.getProduto().getDescricao())
                .id(filialProduto.getProduto().getId())
                .nome(filialProduto.getProduto().getNome())
                .sequencial(filialProduto.getQuantidadeEstoque())
                .quantidadeEstoque(filialProduto.getQuantidadeEstoque())
                .build();
    }

    public FilialDTO asDTO(Filial filial) {
        return FilialDTO.builder().nome(filial.getNome())
                .id(filial.getId()).build();
    }


}
