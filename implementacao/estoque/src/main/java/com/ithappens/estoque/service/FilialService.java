package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.repository.FilialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FilialService {

    private final FilialRepository filialRepository;

    public Filial salvar(Filial filial){
        verificaFilialValida(filial.getDescricao());
        return filialRepository.save(filial);
    }

    public Filial alterar(Filial filial){
        if(filial.getId() == null){
            throw new ServiceException("Id da Filial não informado");
        }
        return filialRepository.findById(filial.getId()).map(filialSalva -> {
            if(!filial.getDescricao().equals(filialSalva)) verificaFilialValida(filial.getDescricao());

            return filialRepository.save(filial);
        }).orElseThrow(()->new ServiceException("Filial com o id " + filial.getId() + " inexistente"));
    }

    public Filial buscaPorId(Long id){
        return filialRepository.findById(id).orElseThrow(()->new ServiceException("Filial com o id " + id + " inexistente"));
    }


    private void verificaFilialValida(String descricao){
        if(filialRepository.findByDescricao(descricao).isPresent()){
            throw new ServiceException("Filial com essa descrição já cadastrada");
        }
    }


}
