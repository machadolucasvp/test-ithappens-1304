package com.ithappens.interview.services;

import com.ithappens.interview.dtos.*;
import com.ithappens.interview.enums.Status;
import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.*;
import com.ithappens.interview.repositories.ItemPedidoRepository;
import com.ithappens.interview.repositories.PedidoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
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

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Integer id) {
        Optional<Pedido> filial = pedidoRepository.findById(id);
        return filial.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
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

                        if (pedido.getItemsPedido().size() > 0 &&
                                containsProdutoAndPedidoOpen(pedido, item)) {
                            throw new DataIntegrityViolationException("Não foi possível realizar a entrada" +
                                    "do pedido, existem produtos duplicados em status ATIVO ou " +
                                    "PROCESSANDO");
                        }

                        filialService.addProduto(item.getProduto(), filial, item.getQuantidade());
                        pedido.getItemsPedido().add(item);
                        return item;

                    } else {
                        throw new IllegalArgumentException("Não foi possível identificar o tipo do pedido");
                    }
                }
        ).collect(Collectors.toSet());

        pedido.setCustoTotal(pedidoDTO.calculaCustoTotal());
        pedido.setItemsPedido(itemPedidos);
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
    }

    private boolean containsProdutoAndPedidoOpen(Pedido pedido, ItemPedido itemPedido) {
        Set<ItemPedido> itemPedidos = pedido.getItemsPedido().stream().filter(
                item -> item.getProduto().getId().equals(itemPedido.getProduto().getId()) &&
                        !item.getStatus().equals(Status.CANCELADO)
        ).collect(Collectors.toSet());

        return itemPedidos.size() > 0;
    }

    public boolean updateItemPedidoStatus(Integer pedidoId, Integer itemId, String status, Boolean isToProcess) {
        Pedido pedido = this.findById(pedidoId);
        if (!isToProcess) {
            return pedido.getItemsPedido().stream().anyMatch(
                    item -> {
                        if (item.getId().equals(itemId)) {
                            item.setStatus(Status.valueOf(status));
                            pedidoRepository.save(pedido);
                            return true;
                        }
                        return false;
                    }
            );
        } else {
            Set<ItemPedido> itemsPedidosProcessados = pedido.getItemsPedido().stream().map(
                    item -> {
                        item.setStatus(Status.PROCESSADO);
                        return item;
                    }
            ).collect(Collectors.toSet());
            pedido.setItemsPedido(itemsPedidosProcessados);
            pedidoRepository.save(pedido);
            return true;
        }
    }

    public PedidoDTO asDTO(Pedido pedido) {
        return PedidoDTO.builder().id(pedido.getId())
                .nota(pedido.getNota()).tipo(pedido.getTipo())
                .cliente(clienteAsDTO(pedido.getCliente()))
                .pagamento(pedido.getPagamento())
                .usuario(usuarioService.asDTO(pedido.getUsuario()))
                .filial(filialAsDTO(pedido.getFilial()))
                .itemsPedido(pedido.getItemsPedido().stream().map(
                        item -> this.itemPedidoAsDTO(item, this.produtoAsDTO(item.getProduto()))
                ).collect(Collectors.toSet()))
                .build();
    }

    public ItemPedidoDTO itemPedidoAsDTO(ItemPedido itemPedido, ProdutoDTO produtoDTO) {
        return ItemPedidoDTO.builder().id(itemPedido.getId())
                .custoUnitario(itemPedido.getCustoUnitario())
                .quantidade(itemPedido.getQuantidade())
                .status(itemPedido.getStatus())
                .produto(produtoDTO).build();
    }

    public ProdutoDTO produtoAsDTO(Produto produto) {
        return ProdutoDTO.builder().nome(produto.getNome())
                .codigoDeBarras(produto.getCodigoDeBarras())
                .custo(produto.getCusto())
                .descricao(produto.getDescricao())
                .id(produto.getId())
                .sequencial(produto.getSequencial())
                .build();

    }

    public FilialPedidoDTO filialAsDTO(Filial filial){
        return FilialPedidoDTO.builder().id(filial.getId())
                .nome(filial.getNome())
                .build();
    }

    ClienteDTO clienteAsDTO(Cliente cliente) {
        return ClienteDTO.builder().id(cliente.getId())
                .nome(cliente.getNome())
                .cnpj(cliente.getCpf())
                .cpf(cliente.getCnpj())
                .build();
    }

}
