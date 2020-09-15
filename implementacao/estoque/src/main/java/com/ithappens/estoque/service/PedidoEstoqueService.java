package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.ItemPedido;
import com.ithappens.estoque.model.PedidoEstoque;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import com.ithappens.estoque.repository.TipoPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoEstoqueService {

    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoEstoque salvar(PedidoEstoque pedidoEstoque){
        return pedidoEstoqueRepository.save(pedidoEstoque);
    }

    public PedidoEstoque alterar(PedidoEstoque pedidoEstoque){
        if(pedidoEstoque.getId()==null) throw new ServiceException("Id do Pedido de Estoque não informado");
        if(pedidoEstoqueRepository.findById(pedidoEstoque.getId()).isPresent()){
            return pedidoEstoqueRepository.save(pedidoEstoque);
        }else {
          throw new ServiceException("Pedido de Estoque com id "+ pedidoEstoque.getId()+ "inexistente");
        }
    }

    public void deletePedidoEstoque(Long pedidoEstoqueId){
        PedidoEstoque ped = buscaPorId(pedidoEstoqueId);
        if(Optional.ofNullable(ped).isPresent()){
            itemPedidoRepository.deletePorPedidoEstoque(pedidoEstoqueId);
            pedidoEstoqueRepository.delete(ped);
        }else throw new ServiceException("Pedido de Estoque com id "+ pedidoEstoqueId+ "inexistente");
    }

    public PedidoEstoque buscaPorId(Long id){
        return pedidoEstoqueRepository.findById(id).orElseThrow(()->new ServiceException("Pedido de Estoque com id"+ id+ "inexistente"));
    }

    public List<PedidoEstoque> buscaPorFilial(Long filialId){
        return new ArrayList<>(pedidoEstoqueRepository.findAllByFilial(filialId));
    }

    public List<PedidoEstoque> buscaPorTipoPedido(Long tipoPedidoId){
        return new ArrayList<>(pedidoEstoqueRepository.findAllByTipoPedido(tipoPedidoId));
    }

    public List<PedidoEstoque> buscaPedidosEtoqueTipoEntrada(){
        return new ArrayList<>(pedidoEstoqueRepository.findAllByTipoPedidoEntrada());
    }

    public List<PedidoEstoque> buscaPedidosEstoqueTipoSaida(){
        return new ArrayList<>(pedidoEstoqueRepository.findAllByTipoPedidoSaida());
    }

    public List<ItemPedido> buscaTodosItensDoPedidoEstoque(Long pedidoEstoqueId){
        return new ArrayList<>(itemPedidoRepository.findByPedidoEstoque(pedidoEstoqueId));
    }

    public List<ItemPedido> buscaItensDoPedidoEstoqueStatusAtivo(Long pedidoEstoqueId){
        return new ArrayList<>(itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoAtivo(pedidoEstoqueId));
    }

    public List<ItemPedido> buscaItensDoPedidoEstoqueStatusCancelado(Long pedidoEstoqueId){
        return new ArrayList<>(itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoCancelado(pedidoEstoqueId));
    }

    public List<ItemPedido> buscaItensDoPedidoEstoqueStatusProcessado(Long pedidoEstoqueId){
        return new ArrayList<>(itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoProcessado(pedidoEstoqueId));
    }


}
