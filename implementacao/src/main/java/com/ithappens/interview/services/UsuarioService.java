package com.ithappens.interview.services;

import com.ithappens.interview.dtos.UsuarioDTO;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.models.Usuario;
import com.ithappens.interview.repositories.PedidoRepository;
import com.ithappens.interview.repositories.UsuarioRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

    public UsuarioDTO asDTO(Usuario usuario){
        return UsuarioDTO.builder().ativo(usuario.isAtivo())
                .id(usuario.getId()).email(usuario.getEmail()).build();
    }

}
