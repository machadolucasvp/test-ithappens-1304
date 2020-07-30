package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FilialRepository extends JpaRepository<Filial,Long> {

    Filial findById(long id);

    @Query("select fil from Filial fil where trim(upper(fil.descricao)) =: descricao ")
    Optional<Filial> findByDescricao(String descricao);
}
