package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque,Long> {

    Estoque findById(long id);

    @Query("select etq from Estoque etq where etq.filial.id =: filialId")
    List<Estoque> findByFilial(Long filialId);

    @Query("select etq from Estoque etq where etq.produto.id =: produtoId")
    List<Estoque> findByProduto(Long produtoId);

    @Query("select etq from Estoque etq where etq.filial.id =: filialId and etq.produto.id =: produtoId")
    Optional<Estoque> findByFilialAndProduto(Long filialId, Long produtoId);
}
