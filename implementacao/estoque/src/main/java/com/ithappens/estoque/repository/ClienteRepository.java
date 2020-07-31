package com.ithappens.estoque.repository;


import com.ithappens.estoque.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Cliente findById(long id);

    @Query("select c from Cliente c where c.cpf =:cpf")
    Optional <Cliente> findByCpf(String cpf);

}
