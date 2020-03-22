package com.ithappens.interview.repositories;

import com.ithappens.interview.models.FilialProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilialProdutoRepository extends JpaRepository<FilialProduto, Integer> {
    Optional<FilialProduto> findByFilialIdAndProdutoId(Integer filialId, Integer produtoId);
}
