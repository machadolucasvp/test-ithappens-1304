package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findById(long id);

    @Query("select usr from Usuario usr where usr.cpf=:cpf")
    Optional<Usuario>findByCpf(String cpf);


}
