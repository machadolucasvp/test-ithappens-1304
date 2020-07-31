package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Compra;
import com.ithappens.estoque.model.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque,Long> {

    EntradaEstoque findById(long id);

    @Query("select ent from EntradaEstoque ent where ent.pedidoEstoque.filial.id =: filialId")
    List<EntradaEstoque> findByFilial(Long filialId);

    @Query("select ent from EntradaEstoque ent where ent.pedidoEstoque.id =: pedidoEstoqueId")
    Optional<EntradaEstoque> findByPedidoEstoque(Long pedidoEstoqueId);

    @Query("select ent from EntradaEstoque  ent where  ent.usuario.cpf =: usuarioCpf")
    Optional<EntradaEstoque> findByUsuarioCpf(String usuarioCpf);

    @Query("select ent from EntradaEstoque ent where ent.usuario.id=:usuarioId")
    List<EntradaEstoque>findByUsuarioId(Long usuarioId);
}
