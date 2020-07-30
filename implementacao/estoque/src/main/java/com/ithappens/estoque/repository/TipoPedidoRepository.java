package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.TipoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoPedidoRepository extends JpaRepository<TipoPedido,Long> {

    TipoPedido findById(long id);

    @Query("select tpd from TipoPedido tpd where trim(upper(tpd.descricao) )=:descricao ")
    Optional<TipoPedido>findByDescricao(String descricao);
}
