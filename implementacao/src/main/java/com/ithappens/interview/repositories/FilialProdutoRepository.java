package com.ithappens.interview.repositories;

import com.ithappens.interview.models.FilialProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialProdutoRepository extends JpaRepository<FilialProduto, Integer> {
}
