package com.ithappens.interview.services;

import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.ItemPedido;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.models.Usuario;
import com.ithappens.interview.repositories.ItemPedidoRepository;
import com.ithappens.interview.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FilialService filialService;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public void addPedidoSaida(Integer filialId, Pedido pedidoDTO){
        Filial filial = filialService.findById(filialId);
        Usuario usuario = usuarioService.findById(pedidoDTO.getUsuario().getId());
        Pedido pedido = Pedido.builder().nota(pedidoDTO.getNota())
                .usuario(usuario).tipo(Tipo.SAIDA)
                .build();

        Set<ItemPedido> itemPedidos = pedidoDTO.getItemsPedido().stream().map(
                item -> {
                    item.setPedido(pedido);
                    filialService.removeProduto(item.getProduto(), filial, item.getQuantidade());
                    return item;
                }
        ).collect(Collectors.toSet());
        pedido.setItemsPedido(itemPedidos);

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);

    }

    public void addPedidoEntrada(Integer filialId, Pedido pedidoDTO) {
        Filial filial = filialService.findById(filialId);
        Usuario usuario = usuarioService.findById(pedidoDTO.getUsuario().getId());
        Pedido pedido = Pedido.builder().nota(pedidoDTO.getNota())
                .usuario(usuario).tipo(Tipo.ENTRADA)
                .build();

        Set<ItemPedido> itemPedidos = pedidoDTO.getItemsPedido().stream().map(
                item -> {
                    item.setPedido(pedido);
                    filialService.addProduto(item.getProduto(), filial, item.getQuantidade());
                    return item;
                }
        ).collect(Collectors.toSet());
        pedido.setItemsPedido(itemPedidos);

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
    }

}
