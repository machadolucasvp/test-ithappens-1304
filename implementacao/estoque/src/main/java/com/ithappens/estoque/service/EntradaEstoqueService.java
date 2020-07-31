package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.*;
import com.ithappens.estoque.repository.EntradaEstoqueRepository;
import com.ithappens.estoque.repository.EstoqueRepository;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EntradaEstoqueService {

    private final EntradaEstoqueRepository entradaEstoqueRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final EstoqueService estoqueService;
    private final PedidoEstoqueService pedidoEstoqueService;
    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final EstoqueRepository estoqueRepository;

    public EntradaEstoque salvar(EntradaEstoque entradaEstoque){
        verificaEntrada(entradaEstoque.getPedidoEstoque().getId());
        processaPedidos(entradaEstoque.getPedidoEstoque().getId());
        atualizaQuantidadeEstoque(entradaEstoque.getPedidoEstoque().getId());
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    public EntradaEstoque alterar(EntradaEstoque entradaEstoque){
        if(entradaEstoque.getId()==null) throw new ServiceException("Id da Entrada não informada");

        return entradaEstoqueRepository.findById(entradaEstoque.getId()).map(entradaSalva->{
            if(!entradaEstoque.getPedidoEstoque().equals(entradaSalva.getPedidoEstoque())){
                verificaEntrada(entradaEstoque.getPedidoEstoque().getId());
                processaPedidos(entradaEstoque.getPedidoEstoque().getId());
            }
            return entradaEstoqueRepository.save(entradaEstoque);
        }).orElseThrow(()->new ServiceException("Entrada com id " + entradaEstoque.getId() + " inexistente"));
    }

    public void deleteEntradaEstoque(Long entradaEstoqueId){
        EntradaEstoque etdEstoque = buscaPorId(entradaEstoqueId);
        reduzQuantidadeEstoque(etdEstoque.getPedidoEstoque().getId());
        entradaEstoqueRepository.delete(etdEstoque);
    }

    public EntradaEstoque buscaPorId(Long entradaEstoqueId){
        return entradaEstoqueRepository.findById(entradaEstoqueId).orElseThrow(()->new ServiceException("Entrada com id " + entradaEstoqueId + " inexistente"));
    }

    private void atualizaQuantidadeEstoque(Long pedidoEstoqueId){
        PedidoEstoque ped = pedidoEstoqueService.buscaPorId(pedidoEstoqueId);
        List <ItemPedido> itens = itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoAtivo(pedidoEstoqueId);

        itens.forEach(itemPedido -> {
           Estoque etq  = estoqueRepository.findByFilialAndProduto(ped.getFilial().getId(),itemPedido.getProduto().getId()).get();
           etq.setQuantidade(etq.getQuantidade()+itemPedido.getQuantidade());
           estoqueService.alterar(etq);
        });
    }

    private void reduzQuantidadeEstoque(Long pedidoEstoqueId){
        PedidoEstoque ped = pedidoEstoqueService.buscaPorId(pedidoEstoqueId);
        List <ItemPedido> itens = itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoAtivo(pedidoEstoqueId);

        itens.forEach(itemPedido -> {
            Estoque etq  = estoqueRepository.findByFilialAndProduto(ped.getFilial().getId(),itemPedido.getProduto().getId()).get();
            etq.setQuantidade(etq.getQuantidade()-itemPedido.getQuantidade());
            estoqueService.alterar(etq);
        });

    }

    private void processaPedidos(Long pedidoEstoqueId){
        itemPedidoRepository.findByPedidoEstoque(
                pedidoEstoqueId).forEach(itemPedidoService::processaItemPedido);
    }

    private void verificaEntrada(Long pedidoEstoqueId){
        if(entradaEstoqueRepository.findByPedidoEstoque(pedidoEstoqueId).isPresent()){
            throw new ServiceException("Já existe uma entrada cadastra para este pedido");
        }
    }

}
