package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.model.Usuario;
import com.ithappens.estoque.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario){
        verificaSenhaValida(usuario.getSenha());
        verificaCPFValido(usuario.getCpf());
        return usuarioRepository.save(usuario);
    }

    public Usuario alterar(Usuario usuario){
        if(usuario.getId()==null) {
            throw new ServiceException("Id do Usuário não foi informado");
        }
        return usuarioRepository.findById(usuario.getId()).map(usuarioSalvo -> {
            if(!usuario.getCpf().equals(usuarioSalvo.getCpf())) verificaCPFValido(usuario.getCpf());

            return usuarioRepository.save(usuario);
        }).orElseThrow(()->new ServiceException("Usuário com id "+ usuario.getId() + " inexistente"));

    }

    public Usuario buscaPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()->new ServiceException("Usuário com id "+ id + " inexistente"));
    }

    public Usuario buscaPorCPF(String cpf){
        return usuarioRepository.findByCpf(cpf).orElseThrow(()-> new ServiceException("Usuário com cpf " + cpf + " inexistente"));
    }

    private void verificaCPFValido(String cpf){
        if(usuarioRepository.findByCpf(cpf).isPresent()){
            throw new ServiceException("CPF já cadastrado");
        }
    }

    private void verificaSenhaValida(String senha){
        if(senha.length()<8) throw new ServiceException("A senha deve conter mais de 8 dígitos");
    }



}
