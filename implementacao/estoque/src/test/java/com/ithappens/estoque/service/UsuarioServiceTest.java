package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Usuario;
import com.ithappens.estoque.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    void init(){usuario = Usuario.builder().id(1L).cpf("80361755090").nome("Harry").senha("12345678").build(); }

    @Test
    void aoSalvar_RetornarUsuarioSalvo(){
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario novoUsuario = usuarioService.salvar(Usuario.builder().cpf("803.617.550-90").nome("Harry").senha("12345678").build());

        assertEquals(usuario.getId(),novoUsuario.getId());
    }

    @Test
    void aoAlterarUsuario_RetornarDadosAtualizados(){
        Usuario usuarioNovosDados = Usuario.builder().id(1L).cpf("80361755090").nome("Harry Potter").senha("12345678").build();

        when(usuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioNovosDados);

       Usuario usuarioAtualizado = usuarioService.alterar(usuarioNovosDados);

       assertEquals(usuarioAtualizado.getId(), usuarioNovosDados.getId());
       assertEquals(usuarioAtualizado.getNome(), usuarioNovosDados.getNome());
    }

    @Test
    void aoVerificarSenhaInvalida_LancarExcecao(){
        Usuario usuario = Usuario.builder().cpf("35084501043").nome("Ronald Weasley").senha("12345").build();

        assertThrows(ServiceException.class, ()->usuarioService.salvar(usuario));
    }

    @Test
    void aoVerificarCPFCadastrado_LancarExcecao(){
        when(usuarioRepository.findByCpf(any(String.class))).thenReturn(Optional.of(usuario));

        assertThrows(ServiceException.class, ()->usuarioService.salvar(usuario));
    }
}
