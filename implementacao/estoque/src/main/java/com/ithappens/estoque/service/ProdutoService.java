package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Produto;
import com.ithappens.estoque.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto){
        verificaCdgBarrasValido(produto.getCdgBarras());
        return produtoRepository.save(produto);
    }

    public Produto alterar(Produto produto){
        if(produto.getId()==null){
            throw new ServiceException("Id do Produto não informado");
        }
        return produtoRepository.findById(produto.getId()).map(produtoSalvo->{
            if(!produto.getCdgBarras().equals(produtoSalvo.getCdgBarras())) verificaCdgBarrasValido(produto.getCdgBarras());
            return produtoRepository.save(produto);
        }).orElseThrow(()->new ServiceException("Produto com id " + produto.getId() + " inexistente"));
    }

    public Produto buscaPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(()-> new ServiceException("Produto com id " + id + " inexistente"));
    }

    public Produto buscaPorCdgBarras(String cdgBarras){
        return produtoRepository.findByCdgBarras(cdgBarras).orElseThrow(()-> new ServiceException("Produto com código de barras Nº " + cdgBarras + " inexistente"));
    }

    private void verificaCdgBarrasValido(String cdgBarras){
        if(produtoRepository.findByCdgBarras(cdgBarras).isPresent()){
            throw new ServiceException("Produto já cadastrado");
        }
    }


}
