package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long> {

    FormaPagamento findById(long id);

    @Query("select fpg from FormaPagamento fpg where trim(upper(fpg.descricao)) =: descricao ")
    Optional<FormaPagamento> findByDescricao(String descricao);

}
