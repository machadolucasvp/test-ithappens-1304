package com.ithappens.interview.services;

import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.ItemPedido;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.models.Usuario;
import com.ithappens.interview.repositories.ItemPedidoRepository;
import com.ithappens.interview.repositories.PedidoRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

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

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public void addPedido(Integer filialId, Pedido pedidoDTO, Tipo tipo) {
        Filial filial = filialService.findById(filialId);
        Usuario usuario = usuarioService.findById(pedidoDTO.getUsuario().getId());
        Pedido pedido = Pedido.builder().nota(pedidoDTO.getNota())
                .usuario(usuario).tipo(tipo)
                .build();

        Set<ItemPedido> itemPedidos = pedidoDTO.getItemsPedido().stream().map(
                item -> {
                    item.setSubTotal(item.calculaSubTotal());
                    item.setPedido(pedido);
                    if (tipo.equals(Tipo.SAIDA)) {
                        filialService.removeProduto(item.getProduto(), filial, item.getQuantidade());
                        return item;
                    } else if (tipo.equals(Tipo.ENTRADA)) {
                        filialService.removeProduto(item.getProduto(), filial, item.getQuantidade());
                        return item;

                    } else {
                        throw new IllegalArgumentException("Não foi possível identificar o tipo do pedido");
                    }
                }
        ).collect(Collectors.toSet());

        pedido.setCustoTotal(pedido.calculaCustoTotal());
        pedido.setItemsPedido(itemPedidos);
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
    }

}
