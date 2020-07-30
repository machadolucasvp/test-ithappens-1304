package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.ItemPedido;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {

    ItemPedido findById(long id);

    @Query("select itp from ItemPedido itp where itp.pedidoEstoque.id =: pedidoEstoqueId")
    List<ItemPedido> findByPedidoEstoque(Long pedidoEstoqueId);

    @Query("select itp from ItemPedido itp where itp.pedidoEstoque.id=:pedidoEstoqueId and itp.statusPedido.id =1")
    List<ItemPedido>findAllByPedidoEstoqueWithStatusPedidoAtivo(Long pedidoEstoqueId);

    @Query("select itp from ItemPedido itp where itp.pedidoEstoque.id=:pedidoEstoqueId and itp.statusPedido.id =2")
    List<ItemPedido>findAllByPedidoEstoqueWithStatusPedidoCancelado(Long pedidoEstoqueId);

    @Query("select itp from ItemPedido itp where itp.pedidoEstoque.id=:pedidoEstoqueId and itp.statusPedido.id =3")
    List<ItemPedido>findAllByPedidoEstoqueWithStatusPedidoProcessado(Long pedidoEstoqueId);
}
