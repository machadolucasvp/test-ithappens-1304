package com.ithappens.interview.repositories;

import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Page<Pedido> findByFilial(Filial filial, Pageable pageRequest);

}
