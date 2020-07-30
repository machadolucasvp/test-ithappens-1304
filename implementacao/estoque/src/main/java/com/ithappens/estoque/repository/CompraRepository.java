package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    Compra findById(long id);

    @Query("select cmp from Compra cmp where cmp.pedidoEstoque.filial.id =: filialId")
    List<Compra> findByFilial(Long filialId);

    @Query("select cmp from Compra cmp where cmp.pedidoEstoque.id =: pedidoEstoqueId")
    Optional<Compra> findByPedidoEstoque(Long pedidoEstoqueId);

    @Query("select cmp from Compra  cmp where cmp.usuario.cpf=: usuarioCpf")
    List<Compra> findByUsuarioCpf(String usuarioCpf);

    @Query("select cmp from Compra cmp where cmp.cliente.cpf=: clienteCpf")
    List<Compra> findByClienteCpf(String clienteCpf);

}
