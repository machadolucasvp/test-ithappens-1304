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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido findById(Integer id) {
        Optional<Pedido> filial = pedidoRepository.findById(id);
        return filial.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

    public PedidoDTO addPedido(Integer filialId, Pedido pedidoDTO, Tipo tipo) {
        Filial filial = filialService.findById(filialId);
        Usuario usuario = usuarioService.findById(pedidoDTO.getUsuario().getId());
        Cliente cliente = clienteService.findById(pedidoDTO.getCliente().getId());
        Pedido pedido = Pedido.builder().tipo(tipo).filial(filial)
                .usuario(usuario)
                .observacao(pedidoDTO.getObservacao())
                .pagamento(pedidoDTO.getPagamento())
                .cliente(cliente)
                .build();

        Set<ItemPedido> itemPedidos = pedidoDTO.getItemsPedido().stream().map(
                item -> {
                    item.setId(null);
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
        pedido.getPagamento().setCusto(pedido.getCustoTotal());
        pedido.setItemsPedido(itemPedidos);
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);

        return this.asDTO(pedido);
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
                .observacao(pedido.getObservacao()).tipo(pedido.getTipo())
                .custoTotal(pedido.getCustoTotal())
                .pagamento(pedido.getPagamento())
                .cliente(clienteService.asDTO(pedido.getCliente()))
                .usuario(usuarioService.asDTO(pedido.getUsuario()))
                .filial(filialService.filialPedidoAsDTO(pedido.getFilial()))
                .itemsPedido(pedido.getItemsPedido().stream().map(
                        item -> this.itemPedidoAsDTO(item, produtoService.asDTO(item.getProduto()))
                ).collect(Collectors.toSet()))
                .build();
    }

    public ItemPedidoDTO itemPedidoAsDTO(ItemPedido itemPedido, ProdutoDTO produtoDTO) {
        return ItemPedidoDTO.builder().id(itemPedido.getId())
                .custoUnitario(itemPedido.getCustoUnitario())
                .quantidade(itemPedido.getQuantidade())
                .subTotal(itemPedido.getSubTotal())
                .status(itemPedido.getStatus())
                .produto(produtoDTO).build();
    }

    public Page<PedidoDTO> findPedidosPageable(Integer page, Integer size,
                                               String direction, String orderBy, Integer filialId) {
        Page<Pedido> pedido;
        if (filialId > 0) {
            Filial filial = filialService.findById(filialId);

            pedido = pedidoRepository.findByFilial(filial, PageRequest.of(page, size,
                    Sort.Direction.valueOf(direction), orderBy));
        } else {
            pedido = pedidoRepository.findAll(PageRequest.of(page, size,
                    Sort.Direction.valueOf(direction), orderBy));
        }
        return pedido.map(this::asDTO);
    }

}
