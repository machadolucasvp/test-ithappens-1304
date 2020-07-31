package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.PedidoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoEstoqueRepository extends JpaRepository<PedidoEstoque, Long> {

    PedidoEstoque findById(long id);

    @Query("select ped from PedidoEstoque ped where ped.tipoPedido.id =:tipoPedidoId")
    List<PedidoEstoque>findAllByTipoPedido(Long tipoPedidoId);

    @Query("select ped from PedidoEstoque ped where ped.filial.id =:filialId")
    List<PedidoEstoque>findAllByFilial(Long filialId);

    @Query("select ped from PedidoEstoque ped where ped.tipoPedido.id = 1")
    List<PedidoEstoque>findAllByTipoPedidoEntrada();

    @Query("select ped from PedidoEstoque ped where ped.tipoPedido.id = 2")
    List<PedidoEstoque>findAllByTipoPedidoSaida();

}
