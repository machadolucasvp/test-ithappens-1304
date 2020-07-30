package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Estoque;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.model.Produto;
import com.ithappens.estoque.repository.EstoqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public Estoque salvar(Estoque estoque){
        verificaEstoqueExistente(estoque.getFilial().getId(), estoque.getProduto().getId());
        return estoqueRepository.save(estoque);
    }

    public Estoque alterar(Estoque estoque){
        if(estoque.getId()==null) throw new ServiceException("Id do Estoque não informado");

        return estoqueRepository.findById(estoque.getId()).map(estoqueSalvo ->{
            if(!estoque.getFilial().equals(estoqueSalvo.getFilial()) && !estoque.getProduto().equals(estoqueSalvo.getProduto())){
                verificaEstoqueExistente(estoque.getFilial().getId(),estoque.getProduto().getId());
            }
            return estoqueRepository.save(estoque);
        }).orElseThrow(()->new ServiceException("Estoque com id " + estoque.getId() + " inexistente"));
    }

    public Estoque buscaPorId(Long id){
        return estoqueRepository.findById(id).orElseThrow(()->new ServiceException("Estoque com id " + id + " inexistente"));
    }

    public List <Estoque> buscaPorFilial(Long filialId){
        return new ArrayList<>(estoqueRepository.findByFilial(filialId));
    }

    public List <Estoque> buscaPorProduto(Long produtoId){
        return new ArrayList<>(estoqueRepository.findByProduto(produtoId));
    }

    private void verificaEstoqueExistente(Long filialId, Long produtoId){
        if (estoqueRepository.findByFilialAndProduto(filialId,produtoId).isPresent()){
            throw new ServiceException("Já existe um estoque cadastrado para esse produto nessa filial");
        }
    }
}
