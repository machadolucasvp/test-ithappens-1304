package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.StatusPedido;
import com.ithappens.estoque.model.TipoPedido;
import com.ithappens.estoque.repository.TipoPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoPedidoService {

    private final TipoPedidoRepository tipoPedidoRepository;

    public TipoPedido salvar(TipoPedido tipoPedido){
        verificaTipoPedidoValido(tipoPedido.getDescricao());
        return tipoPedidoRepository.save(tipoPedido);
    }

    public TipoPedido alterar(TipoPedido tipoPedido){
        if(tipoPedido.getId()==null){
            throw new ServiceException("Id do Tipo de Pedido não informado");
        }
        return tipoPedidoRepository.findById(tipoPedido.getId()).map(tipoPedidoSalvo -> {
            if(!tipoPedido.getDescricao().equals(tipoPedidoSalvo.getDescricao())) verificaTipoPedidoValido(tipoPedido.getDescricao());
            return  tipoPedidoRepository.save(tipoPedido);
        }).orElseThrow(()->new ServiceException("Tipo de Pedido com id " + tipoPedido.getId() + " inexistente"));
    }

    public TipoPedido buscaPorId(Long id){
        return tipoPedidoRepository.findById(id).orElseThrow(()-> new ServiceException("Tipo de Pedido com id " + id + " inexistente"));
    }

    private void verificaTipoPedidoValido(String descricao){
        if(tipoPedidoRepository.findByDescricao(descricao).isPresent()){
            throw new ServiceException("Tipo de Pedido já cadastrado");
        }
    }

}
