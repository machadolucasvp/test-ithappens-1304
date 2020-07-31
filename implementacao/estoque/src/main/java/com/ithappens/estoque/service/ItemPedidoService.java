package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.ItemPedido;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final StatusPedidoService statusPedidoService;

    public ItemPedido salvar(ItemPedido itemPedido){
        verificaProduto(itemPedido.getPedidoEstoque().getId(), itemPedido.getProduto().getId());
        return itemPedidoRepository.save(itemPedido);
    }

    public ItemPedido alterar(ItemPedido itemPedido){
        if(itemPedido.getId()==null) throw new ServiceException("Id do Item não informado");

        return itemPedidoRepository.findById(itemPedido.getId()).map(itemPedidoSalvo ->{
            if(!itemPedido.getProduto().equals(itemPedidoSalvo.getProduto())) verificaProduto(itemPedido.getPedidoEstoque().getId(),itemPedido.getProduto().getId());
            return itemPedidoRepository.save(itemPedido);
        }).orElseThrow(()->new ServiceException("Item com id " + itemPedido.getId() + " inexistente"));
    }

    public ItemPedido buscaPorId(Long id){
        return itemPedidoRepository.findById(id).orElseThrow(()->new ServiceException("Item com id " + id + " inexistente"));
    }

    public void retiraItemPedido(Long itemPedidoId){
        ItemPedido itp = buscaPorId(itemPedidoId);
        if(Optional.ofNullable(itp).isPresent()){
            itp.setStatusPedido(statusPedidoService.buscaPorId(2L));
        }
    }

    public void processaItemPedido(ItemPedido itemPedido){
        itemPedido.setStatusPedido(statusPedidoService.buscaPorId(3L));
    }
    private void verificaProduto(Long pedidoEstoqueId, Long produtoId){
        if(itemPedidoRepository.findByPedidoEstoqueAndProduto(pedidoEstoqueId,produtoId).isPresent()){
            throw new ServiceException("Esse produto já foi adicionado ao pedido");
        }
    }



}
