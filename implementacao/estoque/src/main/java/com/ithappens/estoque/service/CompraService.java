package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Compra;
import com.ithappens.estoque.model.Estoque;
import com.ithappens.estoque.model.ItemPedido;
import com.ithappens.estoque.model.PedidoEstoque;
import com.ithappens.estoque.repository.CompraRepository;
import com.ithappens.estoque.repository.EstoqueRepository;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final PedidoEstoqueService pedidoEstoqueService;
    private final EstoqueRepository estoqueRepository;
    private final EstoqueService estoqueService;

    public Compra salvar(Compra compra){
        verificaSaida(compra.getPedidoEstoque().getId());
        processaPedidos(compra.getPedidoEstoque().getId());
        reduzQuantidadeEstoque(compra.getPedidoEstoque().getId());
        return compraRepository.save(compra);
    }

    public Compra alterar(Compra compra){
        if(compra.getId()==null) throw new ServiceException("Id da Compra não informada");

        return compraRepository.findById(compra.getId()).map(compraSalva->{
            if(!compra.getPedidoEstoque().equals(compra.getPedidoEstoque())){
                verificaSaida(compra.getPedidoEstoque().getId());
                processaPedidos(compra.getPedidoEstoque().getId());
            }
            return compraRepository.save(compra);
        }).orElseThrow(()->new ServiceException("Entrada com id " + compra.getId() + " inexistente"));
    }

    public void deleteCompra(Long compraId){
        Compra compra = buscaPorId(compraId);
        atualizaQuantidadeEstoque(compra.getPedidoEstoque().getId());
        compraRepository.delete(compra);
    }

    public Compra buscaPorId(Long compraId){
        return compraRepository.findById(compraId).orElseThrow(()->new ServiceException("Entrada com id " + compraId + " inexistente"));
    }

    private void reduzQuantidadeEstoque(Long pedidoEstoqueId){
        PedidoEstoque ped = pedidoEstoqueService.buscaPorId(pedidoEstoqueId);
        List<ItemPedido> itens = itemPedidoRepository.findAllByPedidoEstoqueWithStatusPedidoAtivo(pedidoEstoqueId);

        itens.forEach(itemPedido -> {
            Estoque etq  = estoqueRepository.findByFilialAndProduto(ped.getFilial().getId(),itemPedido.getProduto().getId()).get();
            etq.setQuantidade(etq.getQuantidade()-itemPedido.getQuantidade());
            estoqueService.alterar(etq);
        });
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

    private void processaPedidos(Long pedidoEstoqueId){
        itemPedidoRepository.findByPedidoEstoque(
                pedidoEstoqueId).forEach(itemPedidoService::processaItemPedido);
    }

    private void verificaSaida(Long pedidoEstoqueId){
        if(compraRepository.findByPedidoEstoque(pedidoEstoqueId).isPresent()){
            throw new ServiceException("Já existe uma saída cadastra para este pedido");
        }
    }

}
