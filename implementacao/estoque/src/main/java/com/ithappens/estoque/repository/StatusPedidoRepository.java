package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Long> {

    StatusPedido findById(long id);

    @Query("select spd from StatusPedido spd where trim(upper(spd.descricao))=trim(upper(:descricao) ) ")
    Optional<StatusPedido>findByDescricao(String descricao);
}
