package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.FormaPagamento;
import com.ithappens.estoque.model.StatusPedido;
import com.ithappens.estoque.repository.StatusPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusPedidoService {

    private final StatusPedidoRepository statusPedidoRepository;

    public StatusPedido salvar(StatusPedido statusPedido){
        verificaStatusPedidoValida(statusPedido.getDescricao());
        return statusPedidoRepository.save(statusPedido);
    }

    public StatusPedido alterar(StatusPedido statusPedido){
        if(statusPedido.getId()==null){
            throw new ServiceException("Id do Status não informado");
        }
        return statusPedidoRepository.findById(statusPedido.getId()).map(statusSalvo ->{
            if(!statusPedido.getDescricao().equals(statusSalvo.getDescricao())) verificaStatusPedidoValida(statusPedido.getDescricao());
            return statusPedidoRepository.save(statusPedido);

        }).orElseThrow(()->new ServiceException("Status com id " + statusPedido.getId() + " inexistente"));
    }

    public StatusPedido buscaPorId(Long id){
        return statusPedidoRepository.findById(id).orElseThrow(()-> new ServiceException("Status com id " + id + " inexistente"));
    }

    private void verificaStatusPedidoValida(String descricao){
        if(statusPedidoRepository.findByDescricao(descricao).isPresent()){
            throw new ServiceException("Status já cadastrada");
        }
    }
}
